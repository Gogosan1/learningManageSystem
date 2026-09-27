package ru.zelenev.learning.manage.system.model.dto;

import java.util.UUID;

public record ScheduleFilterDto(
        UUID groupId,
        UUID teacherId
) {
}
