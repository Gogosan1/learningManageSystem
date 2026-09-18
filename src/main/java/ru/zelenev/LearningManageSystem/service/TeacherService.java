package ru.zelenev.LearningManageSystem.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.model.dto.TeacherPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;
import ru.zelenev.LearningManageSystem.model.mapper.TeacherMapper;
import ru.zelenev.LearningManageSystem.repository.TeacherRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherMapper teacherMapper;
    private final TeacherRepository teacherRepository;


    @Transactional(readOnly = true)
    public TeacherResponseDto getTeacher(UUID id) {
        Teacher teacher = getTeacherByIdOrThrow(id);

        return teacherMapper.toResponseDto(teacher);
    }

    @Transactional(readOnly = true)
    public PagedResponse<TeacherResponseDto> findAll(Pageable pageable) {
        Page<Teacher> teacherPage = teacherRepository.findAll(pageable);

        List<TeacherResponseDto> teacherResponseDtoList = teacherPage.getContent()
                .stream()
                .map(teacherMapper::toResponseDto)
                .toList();
        return new PagedResponse<>(
                teacherResponseDtoList,
                teacherPage.getNumber(),
                teacherPage.getSize(),
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages(),
                teacherPage.isLast()
        );
    }

    @Transactional
    public TeacherResponseDto createTeacher(TeacherCreateDto teacherCreateDto) {
        Teacher teacher = teacherMapper.toEntity(teacherCreateDto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDto(savedTeacher);
    }

    @Transactional
    public void deleteTeacher(UUID id) {
       Teacher teacher = getTeacherByIdOrThrow(id);
        teacherRepository.delete(teacher);
    }

    @Transactional
    public TeacherResponseDto patchTeacher(UUID id, TeacherPatchDto dto) {
        Teacher teacher = getTeacherByIdOrThrow(id);

        teacherMapper.updateTeacherFromPatchDto(dto,teacher);

        Teacher updatedTeacher = teacherRepository.save(teacher);

        return teacherMapper.toResponseDto(updatedTeacher);
    }

    Teacher getTeacherByIdOrThrow(UUID id){
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher with id "+ id +" does not exist!"));
    }
}
