package com.lineragency.metcon_tasks.model.dto;

import com.lineragency.metcon_tasks.model.enums.ContainerIsoType;
import com.lineragency.metcon_tasks.model.enums.RequestEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record AddTaskDTO(
        RequestEnum type,
        String company,
        String containerNumber,
        ContainerIsoType containerType,
        String truck,
        LocalDateTime dateTime,
        long requestId
) {
}
