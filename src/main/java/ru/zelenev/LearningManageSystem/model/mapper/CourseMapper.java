package ru.zelenev.LearningManageSystem.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.LearningManageSystem.model.dto.CourseCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.CoursePatchDto;
import ru.zelenev.LearningManageSystem.model.dto.CourseResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Course;


@org.mapstruct.Mapper(componentModel = "spring")
public interface CourseMapper {
    Course toEntity(CourseCreateDto CourseCreateDto);

    CourseResponseDto toResponseDto(Course Course);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCourseFromPatchDto(CoursePatchDto dto, @MappingTarget Course Course);

}
