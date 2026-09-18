package ru.zelenev.LearningManageSystem.model.dto;

import java.util.UUID;

public record CoursePatchDto(
        String title,
        String description,
        UUID teacherId
) {
}
