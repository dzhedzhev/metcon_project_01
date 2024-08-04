package com.containerdepot.metcon.service.validation;

import com.containerdepot.metcon.data.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueVatNumberValidator implements ConstraintValidator<UniqueVatNumber, String> {
    private final CompanyRepository companyRepository;

    public UniqueVatNumberValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void initialize(UniqueVatNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String vatNumber, ConstraintValidatorContext context) {
        if (vatNumber == null || vatNumber.isBlank()) {
            return true;
        }
        return !this.companyRepository.existsByVatNumber(vatNumber);
    }
}
