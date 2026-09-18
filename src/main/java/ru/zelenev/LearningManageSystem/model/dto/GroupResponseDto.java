package ru.zelenev.LearningManageSystem.model.dto;

import java.util.UUID;

public record GroupResponseDto(
        UUID id,
        String name
) {
}
