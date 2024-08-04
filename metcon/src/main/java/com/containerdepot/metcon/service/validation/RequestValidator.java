package com.containerdepot.metcon.service.validation;

import com.containerdepot.metcon.data.ContainerRepository;
import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestValidator implements ConstraintValidator<ValidRequest, RequestAddDto> {
    private String message;
    private final ContainerRepository containerRepository;
    private final RequestRepository requestRepository;

    public RequestValidator(ContainerRepository containerRepository, RequestRepository requestRepository) {
        this.containerRepository = containerRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void initialize(ValidRequest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(RequestAddDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        boolean isValid = true;
        Optional<Container> optionalContainer = this.containerRepository.findByNumber(dto.getContainerNumber());
        if (optionalContainer.isEmpty() && isRepairOrRelease(dto)) {
            isValid = false;
            this.message = message + "This container is not present!";
        }
        if (optionalContainer.isPresent() && optionalContainer.get().getReleased() != null && isRepairOrRelease(dto)) {
            isValid = false;
            this.message = message + "This container is released!";
        }

        if (!isValid) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("containerNumber")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        this.message = null;

        return isValid;
    }

    private static boolean isRepairOrRelease(RequestAddDto dto) {
        return "REPAIR".equals(dto.getType().toString()) || "RELEASE".equals(dto.getType().toString());
    }
}
