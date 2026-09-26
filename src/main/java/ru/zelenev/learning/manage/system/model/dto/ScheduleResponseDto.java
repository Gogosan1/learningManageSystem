package ru.zelenev.learning.manage.system.model.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ScheduleResponseDto(
        UUID id,
        GroupResponseDto group,
        CourseResponseDto course,
        TeacherResponseDto teacher,
        OffsetDateTime startTime,
        OffsetDateTime endTime
) {
}
