package ru.zelenev.learning.manage.system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.learning.manage.system.model.dto.CourseCreateDto;
import ru.zelenev.learning.manage.system.model.dto.CoursePatchDto;
import ru.zelenev.learning.manage.system.model.dto.CourseResponseDto;
import ru.zelenev.learning.manage.system.service.CourseService;
import ru.zelenev.learning.manage.system.util.entity.PagedResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable("id") UUID id) {
        CourseResponseDto courseResponseDto = courseService.getCourse(id);
        return ResponseEntity.ok(courseResponseDto);
    }

    @GetMapping
    public PagedResponse<CourseResponseDto> getCourses(
            @PageableDefault(size = 20) Pageable pageable) {

        return courseService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseCreateDto courseCreateDto) {
        CourseResponseDto courseResponseDto = courseService.createCourse(courseCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourseResponseDto> patchCourse(@PathVariable("id") UUID id,
                                                         @RequestBody CoursePatchDto coursePatchDto) {
        CourseResponseDto courseResponseDto = courseService.patchCourse(id, coursePatchDto);
        return ResponseEntity.ok(courseResponseDto);
    }

}
