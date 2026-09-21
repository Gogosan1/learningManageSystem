package ru.zelenev.LearningManageSystem.model.dto;

import jakarta.validation.constraints.NotBlank;

public record GroupCreateDto(
        @NotBlank(message = "group name can not be blank")
        String name
) {
}
