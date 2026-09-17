package ru.zelenev.LearningManageSystem.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.LearningManageSystem.model.dto.StudentCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Student;


@Mapper(componentModel = "string")
public interface StudentMapper {
    Student toEntity(StudentCreateDto studentCreateDto);

    StudentResponseDto toResponseDto(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeacherFromPatchDto(StudentPatchDto dto, @MappingTarget Student student);

}
