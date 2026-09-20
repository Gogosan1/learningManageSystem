package ru.zelenev.LearningManageSystem.model.dto;

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
