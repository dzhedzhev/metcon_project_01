package com.containerdepot.metcon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCompanyEmailValidator.class)
public @interface UniqueCompanyEmail {
    String message() default "There is user registered with this email!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
