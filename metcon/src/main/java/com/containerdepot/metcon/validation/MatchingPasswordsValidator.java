package com.containerdepot.metcon.validation;

import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class MatchingPasswordsValidator implements ConstraintValidator<MatchingPasswords, SignUpDto> {
    private String message;
    @Override
    public void initialize(MatchingPasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(SignUpDto dto, ConstraintValidatorContext context) {
        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return true;
        }

        boolean isValid = dto.getPassword().equals(dto.getConfirmPassword());

        if (!isValid) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;

    }


}
