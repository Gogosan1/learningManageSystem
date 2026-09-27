package ru.zelenev.learning.manage.system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.learning.manage.system.model.dto.CourseCreateDto;
import ru.zelenev.learning.manage.system.model.dto.CoursePatchDto;
import ru.zelenev.learning.manage.system.model.dto.CourseResponseDto;
import ru.zelenev.learning.manage.system.model.entity.Course;
import ru.zelenev.learning.manage.system.model.entity.Teacher;
import ru.zelenev.learning.manage.system.model.mapper.CourseMapper;
import ru.zelenev.learning.manage.system.repository.CourseRepository;
import ru.zelenev.learning.manage.system.util.entity.PagedResponse;
import ru.zelenev.learning.manage.system.util.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public CourseResponseDto getCourse(UUID id) {
        Course course = getCourseById(id);
        return courseMapper.toResponseDto(course);
    }

    @Transactional(readOnly = true)
    public PagedResponse<CourseResponseDto> findAll(Pageable pageable) {
        Page<Course> page = courseRepository.findAll(pageable);
        List<CourseResponseDto> content = page.getContent().stream()
                .map(courseMapper::toResponseDto)
                .toList();

        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Transactional
    public CourseResponseDto createCourse(CourseCreateDto dto) {

        Course course = courseMapper.toEntity(dto);

        Teacher teacher = teacherService.getTeacherById(dto.teacherId());

        course.setTeacher(teacher);

        Course createdCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(createdCourse);
    }

    @Transactional
    public void deleteCourse(UUID id) {
        Course course = getCourseById(id);
        courseRepository.delete(course);
    }

    @Transactional
    public CourseResponseDto patchCourse(UUID id, CoursePatchDto dto) {
        Course course = getCourseById(id);

        courseMapper.updateCourseFromPatchDto(dto, course);

        if (dto.teacherId() != null) {
            course.setTeacher(teacherService.getTeacherById(dto.teacherId()));
        }

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toResponseDto(updatedCourse);
    }

    Course getCourseById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + id + " does not exist!"));
    }

}
