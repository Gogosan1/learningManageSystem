package ru.zelenev.learning.manage.system.util.entity;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime time,
        HttpStatus status,
        String error,
        String message) {
}
