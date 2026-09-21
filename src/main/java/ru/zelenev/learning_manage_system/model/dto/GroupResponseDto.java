package ru.zelenev.learning_manage_system.model.dto;

import java.util.UUID;

public record GroupResponseDto(
        UUID id,
        String name
) {
}
