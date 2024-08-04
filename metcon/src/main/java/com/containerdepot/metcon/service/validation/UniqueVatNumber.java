package com.containerdepot.metcon.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueVatNumberValidator.class)
public @interface UniqueVatNumber {
    String message() default "There is company registered with this VAT number!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
