package ru.zelenev.learning_manage_system.model.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ScheduleCreateDto(
        @NotNull(message = "group id can not be null")
        UUID groupId,
        @NotNull(message = "course id can not be null")
        UUID courseId,
        @NotNull(message = "teacher id can not be null")
        UUID teacherId,
        @NotNull(message = "start time can not be null")
        OffsetDateTime startTime,
        @NotNull(message = "end time can not be null")
        OffsetDateTime endTime
) {
}
