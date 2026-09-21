package ru.zelenev.learning_manage_system.model.dto;

import java.util.UUID;

public record CourseResponseDto(
        UUID id,
        String title,
        String description,
        TeacherResponseDto teacher
) {
}
