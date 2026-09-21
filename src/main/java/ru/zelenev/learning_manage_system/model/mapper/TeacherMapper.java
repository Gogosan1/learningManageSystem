package ru.zelenev.learning_manage_system.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.learning_manage_system.model.dto.TeacherCreateDto;
import ru.zelenev.learning_manage_system.model.dto.TeacherPatchDto;
import ru.zelenev.learning_manage_system.model.dto.TeacherResponseDto;
import ru.zelenev.learning_manage_system.model.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toEntity(TeacherCreateDto teacherCreateDto);

    TeacherResponseDto toResponseDto(Teacher teacher);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromPatchDto(TeacherPatchDto dto, @MappingTarget Teacher teacher);


}
