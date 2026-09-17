package ru.zelenev.LearningManageSystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.model.dto.TeacherPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherResponseDto;
import ru.zelenev.LearningManageSystem.service.TeacherService;


import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacher(@PathVariable("id") UUID id){
        TeacherResponseDto teacher = teacherService.getTeacher(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping
    public PagedResponse<TeacherResponseDto> getTeachers(@PageableDefault(page = 0, size = 20, sort = "id")
                                                             Pageable pageable){
        return teacherService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@Valid @RequestBody
                                                            TeacherCreateDto teacherCreateDto){
        TeacherResponseDto teacherResponseDto = teacherService.createTeacher(teacherCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") UUID id){
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> patchTeacher(@PathVariable("id") UUID id, @RequestBody TeacherPatchDto teacherPatchDto){
        TeacherResponseDto teacherResponseDto = teacherService.patchTeacher(id, teacherPatchDto);
        return ResponseEntity.ok(teacherResponseDto);
    }

}
