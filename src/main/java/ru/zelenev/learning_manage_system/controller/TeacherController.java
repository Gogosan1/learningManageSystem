package ru.zelenev.learning_manage_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.learning_manage_system.model.dto.TeacherCreateDto;
import ru.zelenev.learning_manage_system.model.dto.TeacherPatchDto;
import ru.zelenev.learning_manage_system.model.dto.TeacherResponseDto;
import ru.zelenev.learning_manage_system.service.TeacherService;
import ru.zelenev.learning_manage_system.util.entity.PagedResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacher(@PathVariable("id") UUID id) {
        TeacherResponseDto teacher = teacherService.getTeacher(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    public PagedResponse<TeacherResponseDto> getTeachers(@PageableDefault(size = 20)
                                                         Pageable pageable) {
        return teacherService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody
                                                            TeacherCreateDto teacherCreateDto) {
        TeacherResponseDto teacherResponseDto = teacherService.createTeacher(teacherCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") UUID id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> patchTeacher(@PathVariable("id") UUID id, @RequestBody TeacherPatchDto teacherPatchDto) {
        TeacherResponseDto teacherResponseDto = teacherService.patchTeacher(id, teacherPatchDto);
        return ResponseEntity.ok(teacherResponseDto);
    }

}
