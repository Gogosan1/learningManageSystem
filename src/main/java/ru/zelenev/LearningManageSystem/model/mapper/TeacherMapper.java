package ru.zelenev.LearningManageSystem.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.LearningManageSystem.model.dto.TeacherCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;

@Mapper(componentModel = "string")
public interface TeacherMapper {
    Teacher toEntity(TeacherCreateDto teacherCreateDto);

    TeacherResponseDto toResponseDto(Teacher teacher);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromPatchDto(TeacherPatchDto dto, @MappingTarget Teacher teacher);


}
