package ru.zelenev.learning.manage.system.model.dto;

import java.util.UUID;

public record CoursePatchDto(
        String title,
        String description,
        UUID teacherId
) {
}
