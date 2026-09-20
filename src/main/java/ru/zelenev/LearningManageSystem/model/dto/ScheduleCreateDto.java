package ru.zelenev.LearningManageSystem.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ScheduleCreateDto(
        @NotNull UUID groupId,
        @NotNull UUID courseId,
        @NotNull UUID teacherId,
        @NotNull OffsetDateTime startTime,
        @NotNull OffsetDateTime endTime
) {
}
