package ru.zelenev.learning_manage_system.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.learning_manage_system.model.dto.CourseCreateDto;
import ru.zelenev.learning_manage_system.model.dto.CoursePatchDto;
import ru.zelenev.learning_manage_system.model.dto.CourseResponseDto;
import ru.zelenev.learning_manage_system.model.entity.Course;


@Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseCreateDto CourseCreateDto);

    CourseResponseDto toResponseDto(Course Course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromPatchDto(CoursePatchDto dto, @MappingTarget Course Course);

}
