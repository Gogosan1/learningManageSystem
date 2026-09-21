package ru.zelenev.learning_manage_system.model.dto;

import java.util.UUID;

public record StudentResponseDto(
        UUID id,
        String firstName,
        String lastName
) {
}
