package ru.zelenev.LearningManageSystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.LearningManageSystem.model.dto.StudentCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentResponseDto;
import ru.zelenev.LearningManageSystem.service.StudentService;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudent(@PathVariable("id") UUID id) {
        StudentResponseDto student = studentService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public PagedResponse<StudentResponseDto> getStudent(@PageableDefault(size = 20)
                                                        Pageable pageable) {
        return studentService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody
                                                            StudentCreateDto studentCreateDto) {
        StudentResponseDto studentResponseDto = studentService.createStudent(studentCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDto> patchStudent(@PathVariable("id") UUID id, @RequestBody StudentPatchDto studentPatchDto) {
        StudentResponseDto studentResponseDto = studentService.patchStudent(id, studentPatchDto);
        return ResponseEntity.ok(studentResponseDto);
    }

}

