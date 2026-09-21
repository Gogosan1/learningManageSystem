package ru.zelenev.learning_manage_system.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ScheduleResponseDto(
        UUID id,
        UUID groupId,
        UUID courseId,
        UUID teacherId,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
