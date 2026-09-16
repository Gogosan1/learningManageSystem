package ru.zelenev.LearningManageSystem.model.dto;

import java.util.UUID;

public record TeacherResponseDto(
        UUID id,
        String firstName,
        String lastName
) {
}
