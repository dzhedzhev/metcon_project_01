package com.lineragency.metcon_tasks.model.dto;

import com.lineragency.metcon_tasks.model.enums.ContainerIsoType;
import com.lineragency.metcon_tasks.model.enums.RequestEnum;

import java.time.LocalDateTime;

public record TaskDTO(
        long id,
        RequestEnum type,
        String company,
        String containerNumber,
        ContainerIsoType containerType,
        String truck,
        LocalDateTime dateTime,
        long requestId
) {
}
