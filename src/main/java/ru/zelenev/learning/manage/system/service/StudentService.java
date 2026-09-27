package ru.zelenev.learning.manage.system.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.learning.manage.system.model.dto.StudentCreateDto;
import ru.zelenev.learning.manage.system.model.dto.StudentPatchDto;
import ru.zelenev.learning.manage.system.model.dto.StudentResponseDto;
import ru.zelenev.learning.manage.system.model.entity.Student;
import ru.zelenev.learning.manage.system.model.mapper.StudentMapper;
import ru.zelenev.learning.manage.system.repository.StudentRepository;
import ru.zelenev.learning.manage.system.util.entity.PagedResponse;
import ru.zelenev.learning.manage.system.util.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupStudentService groupStudentService;

    private final StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public StudentResponseDto getStudent(UUID id) {
        Student student = getStudentById(id);
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

        groupStudentService.addNewStudentToGroups(savedStudent, studentCreateDto.groupIds());

        return studentMapper.toResponseDto(savedStudent);
    }


    @Transactional
    public void deleteStudent(UUID id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponseDto patchStudent(UUID id, StudentPatchDto studentPatchDto) {
        Student student = getStudentById(id);

        studentMapper.updateStudentFromPatchDto(studentPatchDto, student);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toResponseDto(savedStudent);
    }

    Student getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " does not exists!"));
    }
}
