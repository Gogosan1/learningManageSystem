package ru.zelenev.learning.manage.system.model.dto;

import java.util.UUID;

public record GroupResponseDto(
        UUID id,
        String name
) {
}
