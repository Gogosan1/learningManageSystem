package ru.zelenev.LearningManageSystem.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;

/**
 * DTO for {@link Teacher}
 */
public record TeacherCreateDto(
        @NotNull(message = "teacher first name can not be null")
        @NotBlank(message = "teacher first name can not be blank")
        String firstName,
        @NotNull(message = "teacher last name can not be null")
        @NotBlank(message = "teacher last name can not be blank")
        String lastName) {
}