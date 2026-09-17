package ru.zelenev.LearningManageSystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.model.dto.TeacherCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.TeacherResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;
import ru.zelenev.LearningManageSystem.model.mapper.TeacherMapper;
import ru.zelenev.LearningManageSystem.repository.TeacherRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    private TeacherMapper teacherMapper;
    private Teacher teacher;
    private UUID teacherId;

    @BeforeEach
    void setUp(){
        teacherId = UUID.randomUUID();
        teacher = new Teacher(teacherId,"Pavel", "Zelenev");
        teacherMapper = Mappers.getMapper(TeacherMapper.class);
        teacherService = new TeacherService(teacherMapper,teacherRepository);
    }

    @Nested
    class GetTeacher {

        @Test
        void ShouldReturnTeacherResponseDto() {
            //Arrange
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

            //Act
            TeacherResponseDto teacherResponseDto = teacherService.getTeacher(teacherId);

            //Assert
            assertEquals(teacher.getId(), teacherResponseDto.id());
            assertEquals(teacher.getFirstName(), teacherResponseDto.firstName());
            assertEquals(teacher.getLastName(), teacherResponseDto.lastName());
        }

        @Test
        void ShouldThrowResourceNotFoundException() {
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

            ResourceNotFoundException resourceNotFoundException =
                    assertThrows(ResourceNotFoundException.class, () -> teacherService.getTeacher(teacherId));

            assertEquals("Teacher with id " + teacherId + " does not exist!", resourceNotFoundException.getMessage());
        }

    }

    @Nested
    class FindAll {

        @Test
        void ShouldReturnPagedResponseOfTeacherResponseDto(){
            Teacher teacher1 = new Teacher(UUID.randomUUID(), "Nikolay", "Petrov");
            Teacher teacher2 = new Teacher(UUID.randomUUID(), "Alex", "Li");
            List<Teacher> teacherList = List.of(teacher,teacher1, teacher2);
            Pageable pageable = PageRequest.of(0,2);
            Page<Teacher> mockPageFromDb = new PageImpl<>(teacherList, pageable, 3);
            when(teacherRepository.findAll(pageable)).thenReturn(mockPageFromDb);

            PagedResponse<TeacherResponseDto> result = teacherService.findAll(pageable);

            assertNotNull(result);
            assertEquals(0, result.getPageNumber());
            assertEquals(2, result.getPageSize());
            assertEquals(3, result.getTotalElements());
            assertEquals(2, result.getTotalPages());
            assertFalse(result.isLast());

            assertEquals(3, result.getContent().size());

            TeacherResponseDto firstDto = result.getContent().get(0);
            assertEquals(teacher.getId(), firstDto.id());
            assertEquals("Pavel", firstDto.firstName());

            verify(teacherRepository).findAll(pageable);


        }
    }

    @Nested
    class CreateTeacher {

        @Test
        void ShouldReturnTeacherResponseDto(){
            TeacherCreateDto teacherCreateDto = new TeacherCreateDto(teacher.getFirstName(), teacher.getLastName());
            when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

            TeacherResponseDto teacherResponseDto = teacherService.createTeacher(teacherCreateDto);

            assertEquals("Pavel", teacherResponseDto.firstName());
            assertEquals("Zelenev", teacherResponseDto.lastName());
            verify(teacherRepository, times(1)).save(any(Teacher.class));
        }


    }

    @Nested
    class DeleteTeacher {

        @Test
        void ShouldSuccessfullyDelete(){
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

            teacherService.deleteTeacher(teacherId);

            verify(teacherRepository, times(1)).delete(teacher);
        }

        @Test
        void ShouldThrowResourceNotFoundException() {
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(
                    ResourceNotFoundException.class,
                    () -> teacherService.deleteTeacher(teacherId)
            );


            assertEquals("Teacher with id " + teacherId + " does not exist!", exception.getMessage());
            verify(teacherRepository, never()).delete(any(Teacher.class));
        }
    }

    @Nested
    class PatchTeacher {

        @Test
        void FirstName_ShouldReturnResponseTeacherDto() {
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
            when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
            TeacherPatchDto teacherPatchDto = new TeacherPatchDto("Ivan", null);

            TeacherResponseDto teacherResponseDto = teacherService.patchTeacher(teacherId, teacherPatchDto);

            assertEquals(teacherId, teacherResponseDto.id());
            assertEquals("Ivan", teacherResponseDto.firstName());
            assertEquals("Zelenev", teacherResponseDto.lastName());
        }

        @Test
        void ShouldReturnResponseTeacherDto() {
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
            when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
            TeacherPatchDto teacherPatchDto = new TeacherPatchDto("Ivan", "Ivanov");

            TeacherResponseDto teacherResponseDto = teacherService.patchTeacher(teacherId, teacherPatchDto);

            assertEquals(teacherId, teacherResponseDto.id());
            assertEquals("Ivan", teacherResponseDto.firstName());
            assertEquals("Ivanov", teacherResponseDto.lastName());
        }

        @Test
        void ShouldThrowResourceNotFoundException() {
            when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());
            TeacherPatchDto teacherPatchDto = new TeacherPatchDto("Ivan", null);

            ResourceNotFoundException resourceNotFoundException =
                    assertThrows(ResourceNotFoundException.class, () -> teacherService.patchTeacher(teacherId, teacherPatchDto));

            assertEquals("Teacher with id " + teacherId + " does not exist!", resourceNotFoundException.getMessage());
        }
    }
}
