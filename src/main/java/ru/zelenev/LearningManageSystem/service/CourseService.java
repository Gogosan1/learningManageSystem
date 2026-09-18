package ru.zelenev.LearningManageSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.model.dto.CourseCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.CoursePatchDto;
import ru.zelenev.LearningManageSystem.model.dto.CourseResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Course;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;
import ru.zelenev.LearningManageSystem.model.mapper.CourseMapper;
import ru.zelenev.LearningManageSystem.repository.CourseRepository;
import ru.zelenev.LearningManageSystem.repository.TeacherRepository;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    @Transactional(readOnly = true)
    public CourseResponseDto getCourse(UUID id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + id + " does not exist!"));
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

        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Teacher with id " + dto.teacherId() + " does not exist!"));

        course.setTeacher(teacher);

        Course createdCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(createdCourse);
    }

    @Transactional
    public void deleteCourse(UUID id) {
        Course course =  courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + id + " does not exist!"));
        courseRepository.delete(course);
    }

    @Transactional
    public CourseResponseDto patchCourse(UUID id, CoursePatchDto dto) {
        Course course =  courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + id + " does not exist!"));

        courseMapper.updateCourseFromPatchDto(dto, course);

        if (dto.teacherId() != null) {
            course.setTeacher(teacherRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Teacher with id " + dto.teacherId() + " does not exist!")));
        }

        Course updatedCourse = courseRepository.save(course);

        return courseMapper.toResponseDto(updatedCourse);
    }

}
