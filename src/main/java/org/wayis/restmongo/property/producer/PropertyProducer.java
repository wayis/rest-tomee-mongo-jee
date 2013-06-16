package org.wayis.restmongo.property.producer;


import org.apache.commons.lang3.StringUtils;
import org.wayis.restmongo.property.annotation.Property;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.text.MessageFormat;
import java.util.MissingResourceException;
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
        if (StringUtils.isEmpty(propertyKey)) {
            if (property.mandatory()) {
                throw new IllegalStateException(MessageFormat.format(MANDATORY_MSG, propertyKey));
            } else {
                return property.defaultValue();
            }
        }
        String value = null;
        try {
            value = BUNDLE.getString(propertyKey);
            if (StringUtils.isEmpty(value)) {
                if (property.mandatory()) {
                    throw new IllegalStateException(MessageFormat.format(MANDATORY_MSG, propertyKey));
                } else {
                    return property.defaultValue();
                }
            }
        } catch (MissingResourceException e) {
            if (property.mandatory()) {
                throw new IllegalStateException(MessageFormat.format(MANDATORY_MSG, propertyKey));
            } else {
                return property.defaultValue();
            }
        }

        return value;
    }

}
