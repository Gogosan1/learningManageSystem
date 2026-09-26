package ru.zelenev.learning.manage.system.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SchedulePatchDto(
        UUID groupId,
        UUID courseId,
        UUID teacherId,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
