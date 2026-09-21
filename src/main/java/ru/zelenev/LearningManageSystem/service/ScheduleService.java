package ru.zelenev.LearningManageSystem.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.SchedulePatchDto;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Course;
import ru.zelenev.LearningManageSystem.model.entity.Group;
import ru.zelenev.LearningManageSystem.model.entity.Schedule;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;
import ru.zelenev.LearningManageSystem.model.mapper.ScheduleMapper;
import ru.zelenev.LearningManageSystem.repository.ScheduleRepository;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.util.exceptions.EndTimeBeforeStartTimeException;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;
import ru.zelenev.LearningManageSystem.util.exceptions.TimeBeforeNowMomentException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CourseService courseService;
    private final GroupService groupService;
    private final TeacherService teacherService;
    private final ScheduleMapper scheduleMapper;

    @Transactional(readOnly = true)
    public ScheduleResponseDto getSchedule(UUID id) {
        Schedule schedule = getScheduleByIdOrThrow(id);
        return scheduleMapper.toResponseDto(schedule);
    }

    // TODO: по хорошему в качестве базового поведения можно привязать поиск к текущей дате и на ближайшую неделю
    @Transactional(readOnly = true)
    public PagedResponse<ScheduleResponseDto> findAllByGroupId(UUID groupId, Pageable pageable) {
        Page<Schedule> schedulePages = scheduleRepository.findAllByGroupId(groupId, pageable);
        List<ScheduleResponseDto> content = schedulePages.
                stream().
                map(scheduleMapper::toResponseDto).
                toList();

        return new PagedResponse<>(
                content,
                schedulePages.getNumber(),
                schedulePages.getSize(),
                schedulePages.getTotalElements(),
                schedulePages.getTotalPages(),
                schedulePages.isLast()
        );
    }

    // TODO: по хорошему в качестве базового поведения можно привязать поиск к текущей дате и на ближайшую неделю
    @Transactional(readOnly = true)
    public PagedResponse<ScheduleResponseDto> findAllByTeacherId(UUID teacherId, Pageable pageable) {
        Page<Schedule> schedulePages = scheduleRepository.findAllByTeacherId(teacherId, pageable);
        List<ScheduleResponseDto> content = schedulePages.
                stream().
                map(scheduleMapper::toResponseDto).
                toList();

        return new PagedResponse<>(
                content,
                schedulePages.getNumber(),
                schedulePages.getSize(),
                schedulePages.getTotalElements(),
                schedulePages.getTotalPages(),
                schedulePages.isLast()
        );
    }

    @Transactional
    public ScheduleResponseDto createScheduleNote(@Valid ScheduleCreateDto scheduleCreateDto) {

        validateStartTimeAndEndTimeOrThrow(scheduleCreateDto.startTime(), scheduleCreateDto.endTime());
        Schedule schedule = scheduleMapper.toEntity(scheduleCreateDto);

        Group group = groupService.getGroupByIdOrThrow(scheduleCreateDto.groupId());
        Course course = courseService.getCourseByIdOrThrow(scheduleCreateDto.courseId());
        Teacher teacher = teacherService.getTeacherByIdOrThrow(scheduleCreateDto.teacherId());

        schedule.setGroup(group);
        schedule.setCourse(course);
        schedule.setTeacher(teacher);

        Schedule createdSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDto(createdSchedule);
    }

    @Transactional
    public void deleteSchedule(UUID id) {
        Schedule schedule = getScheduleByIdOrThrow(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public ScheduleResponseDto patchSchedule(UUID id, SchedulePatchDto schedulePatchDto) {
        Schedule schedule = getScheduleByIdOrThrow(id);

        scheduleMapper.updateScheduleFromPatchDto(schedulePatchDto, schedule);

        if (schedulePatchDto.courseId() != null) {
            Course course = courseService.getCourseByIdOrThrow(schedulePatchDto.courseId());
            schedule.setCourse(course);
        }

        if (schedulePatchDto.groupId() != null) {
            Group group = groupService.getGroupByIdOrThrow(schedulePatchDto.groupId());
            schedule.setGroup(group);
        }

        if (schedulePatchDto.teacherId() != null) {
            Teacher teacher = teacherService.getTeacherByIdOrThrow(schedulePatchDto.teacherId());
            schedule.setTeacher(teacher);
        }

        validateStartTimeAndEndTimeOrThrow(schedule.getStartTime(), schedule.getEndTime());
        // TODO: создать проверку конфликтов наложения расписания, если такие возникнут на будущее

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.toResponseDto(updatedSchedule);
    }

    private Schedule getScheduleByIdOrThrow(UUID id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + id + " does not exists!"));
    }

    private void validateStartTimeAndEndTimeOrThrow(OffsetDateTime startTime, OffsetDateTime endTime) {
        if (endTime.isBefore(startTime))
            throw new EndTimeBeforeStartTimeException("End time can not be before start time!");

        if (endTime.isBefore(OffsetDateTime.now()) || startTime.isBefore(OffsetDateTime.now()))
            throw new TimeBeforeNowMomentException("Time moment can not be before now time!");
    }

}
