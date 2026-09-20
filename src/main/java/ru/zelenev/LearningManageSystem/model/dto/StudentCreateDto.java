package ru.zelenev.LearningManageSystem.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record StudentCreateDto(
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,
        @NotNull @NotEmpty List<UUID> groupIds
) {
}
