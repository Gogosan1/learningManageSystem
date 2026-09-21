package ru.zelenev.learning_manage_system.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.learning_manage_system.model.dto.StudentCreateDto;
import ru.zelenev.learning_manage_system.model.dto.StudentPatchDto;
import ru.zelenev.learning_manage_system.model.dto.StudentResponseDto;
import ru.zelenev.learning_manage_system.model.entity.Student;


@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toEntity(StudentCreateDto studentCreateDto);

    StudentResponseDto toResponseDto(Student student);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateStudentFromPatchDto(StudentPatchDto dto, @MappingTarget Student student);

}
