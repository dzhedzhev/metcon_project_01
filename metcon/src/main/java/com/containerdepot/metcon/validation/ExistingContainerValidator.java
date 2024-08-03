package com.containerdepot.metcon.validation;

import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.model.entities.Container;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistingContainerValidator implements ConstraintValidator<ExistingContainer, String> {
    private final ContainerRepository containerRepository;

    public ExistingContainerValidator(ContainerRepository containerRepository) {
        this.containerRepository = containerRepository;
    }

    @Override
    public void initialize(ExistingContainer constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String container, ConstraintValidatorContext context) {
        if (container == null || container.isBlank()) {
            return false;
        }
        Optional<Container> optionalContainer = this.containerRepository.findByNumber(container);
        if (optionalContainer.isPresent() && optionalContainer.get().getReleased() == null) {
            return false;
        }
        if (optionalContainer.isPresent() && optionalContainer.get().getReleased() != null) {
            return true;
        }
        return optionalContainer.isEmpty();
    }
}
