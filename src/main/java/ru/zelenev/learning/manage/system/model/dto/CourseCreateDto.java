package ru.zelenev.learning.manage.system.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseCreateDto(
        @NotBlank(message = "course title can not be blank")
        String title,
        String description,
        @NotNull(message = "teacher id can not be null")
        UUID teacherId
) {
}
