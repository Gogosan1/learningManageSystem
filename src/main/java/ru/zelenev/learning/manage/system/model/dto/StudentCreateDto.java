package ru.zelenev.learning.manage.system.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record StudentCreateDto(
        @NotNull(message = "student first name can not be null")
        @NotBlank(message = " student first name can not be blank")
        String firstName,
        @NotNull(message = "student last name can not be null")
        @NotBlank(message = "student last name can not be blank")
        String lastName,
        @NotNull(message = "group id list can not be null")
        @NotEmpty(message = "group id list can not be empty")
        List<UUID> groupIds
) {
}
