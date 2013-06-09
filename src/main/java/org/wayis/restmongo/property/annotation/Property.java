package org.wayis.restmongo.property.annotation;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Property {

    @Nonbinding
    public String key() default "";

    @Nonbinding
    public boolean mandatory() default false;

    @Nonbinding
    public String defaultValue() default "";

}
