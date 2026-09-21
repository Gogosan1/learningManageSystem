package ru.zelenev.learning_manage_system.model.dto;

import java.util.UUID;

public record CoursePatchDto(
        String title,
        String description,
        UUID teacherId
) {
}
