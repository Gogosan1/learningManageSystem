package ru.zelenev.learning.manage.system.model.dto;

import java.util.UUID;

public record StudentResponseDto(
        UUID id,
        String firstName,
        String lastName
) {
}
