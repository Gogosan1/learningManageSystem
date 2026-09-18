package ru.zelenev.LearningManageSystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseCreateDto(
        @NotBlank
        String title,
        String description,
        @NotNull
        UUID teacherId
) {
}
