package com.containerdepot.metcon.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistingContainerValidator.class)
public @interface ExistingContainer {
    String message() default "This container is available at depot!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
