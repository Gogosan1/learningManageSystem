package ru.zelenev.LearningManageSystem.util.entity;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime time, int status, String error, String message) {
}
