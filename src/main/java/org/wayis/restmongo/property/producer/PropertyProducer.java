package org.wayis.restmongo.property.producer;


import org.wayis.restmongo.property.annotation.Property;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class PropertyProducer {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("database");
    private static final String MANDATORY_MSG = "No value found for the mandatory property '{0}'";

    @Produces
    @Property
    public String getStringProperty(InjectionPoint p) {
        return getPropertyValue(p);
    }

    @Produces
    @Property
    public Integer getIntegerProperty(InjectionPoint p) {
        final String value = getPropertyValue(p);
        if (value == null) {
            return null;
        }
        return Integer.parseInt(value);
    }

    private String getPropertyValue(InjectionPoint p) {
        final Property property = p.getAnnotated().getAnnotation(Property.class);
        final String propertyKey = property.key();
        // TODO: replace by StringUtils
        if (propertyKey == null || propertyKey.length() == 0) {
            return property.defaultValue();
        }
        final String value = BUNDLE.getString(propertyKey);
        // TODO: replace by StringUtils
        if (value == null || value.length() == 0) {
            if (property.mandatory()) {
                throw new IllegalStateException(MessageFormat.format(MANDATORY_MSG, propertyKey));
            } else {
                return property.defaultValue();
            }
        }
        return value;
    }
}
