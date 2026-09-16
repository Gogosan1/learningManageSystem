package ru.zelenev.LearningManageSystem.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;

/**
 * DTO for {@link Teacher}
 */
public record TeacherCreateDto(
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName) {
}