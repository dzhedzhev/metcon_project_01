package com.containerdepot.metcon.service.validation;

import com.containerdepot.metcon.data.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueCompanyPhoneValidator implements ConstraintValidator<UniqueCompanyPhone, String> {
    private final CompanyRepository companyRepository;

    public UniqueCompanyPhoneValidator(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void initialize(UniqueCompanyPhone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null || phone.isBlank()) {
            return true;
        }
        return !this.companyRepository.existsByPhoneNumber(phone);
    }
}
