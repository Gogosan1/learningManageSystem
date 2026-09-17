package ru.zelenev.LearningManageSystem.model.dto;

import java.util.UUID;

public record StudentCreateDto(
        UUID id,
        String firstName,
        String lastName
) {
}
