package com.containerdepot.metcon.validation;

import com.containerdepot.metcon.data.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueCompanyEmailValidator implements ConstraintValidator<UniqueCompanyEmail, String> {
    private final CompanyRepository companyRepository;

    public UniqueCompanyEmailValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void initialize(UniqueCompanyEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.isBlank()) {
            return true;
        }
        return !this.companyRepository.existsByEmail(email);
    }
}
