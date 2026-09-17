package ru.zelenev.LearningManageSystem.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.model.dto.StudentCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.StudentResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Student;
import ru.zelenev.LearningManageSystem.model.mapper.StudentMapper;
import ru.zelenev.LearningManageSystem.repository.StudentRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public StudentResponseDto getStudent(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " does not exists!"));
        return studentMapper.toResponseDto(student);
    }

    @Transactional(readOnly = true)
    public PagedResponse<StudentResponseDto> findAll(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);

        List<StudentResponseDto> studentsResponseDtoList = studentPage.stream()
                .map(studentMapper::toResponseDto)
                .toList();

        return new PagedResponse<>(
                studentsResponseDtoList,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.isLast()
        );
    }


    @Transactional
    public StudentResponseDto createStudent(@Valid StudentCreateDto studentCreateDto) {
        Student student = studentMapper.toEntity(studentCreateDto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }


    @Transactional
    public void deleteStudent(UUID id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id "+ id + " does not exists!"));
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponseDto patchStudent(UUID id, StudentPatchDto studentPatchDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id "+ id + " does not exists!"));

        studentMapper.updateTeacherFromPatchDto(studentPatchDto,student);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponseDto(savedStudent);
    }
}
