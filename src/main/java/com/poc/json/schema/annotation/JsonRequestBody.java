package com.poc.json.schema.annotation;

import org.springframework.validation.Errors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
    Denotes a Spring MVC Controller handler method parameter
    that will be validated against JSON schema.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JsonRequestBody {
    /**
     * Immediately throw an exception when JSON schema validation
     * fails. Otherwise, be lax and instead of exception, just
     * contribute to the corresponding validation {@link Errors}
     * object.
     *
     */
    boolean strict() default true;

    
}