package ru.zelenev.LearningManageSystem.model.dto;

import java.util.UUID;

public record CourseResponseDto(
        UUID id,
        String title,
        String description,
        TeacherResponseDto teacher
) {
}
