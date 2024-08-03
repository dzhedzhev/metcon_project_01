package com.containerdepot.metcon.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCompanyPhoneValidator.class)
public @interface UniqueCompanyPhone {
    String message() default "There is registered company with this phone number!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
