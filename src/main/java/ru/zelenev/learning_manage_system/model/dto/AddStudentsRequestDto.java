package ru.zelenev.learning_manage_system.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record AddStudentsRequestDto(
        @NotNull(message = "students id list can not be null")
        @NotEmpty(message = "students is list can not be empty")
        List<UUID> studentsIds
) {
}
