package ru.zelenev.LearningManageSystem.model.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupCreateDto(
        @NotBlank
        String name
) {
}
