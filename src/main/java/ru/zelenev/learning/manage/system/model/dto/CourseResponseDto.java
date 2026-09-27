package ru.zelenev.learning.manage.system.model.dto;

import java.util.UUID;

public record CourseResponseDto(
        UUID id,
        String title,
        String description,
        TeacherResponseDto teacher
) {
}
