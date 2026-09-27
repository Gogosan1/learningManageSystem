package ru.zelenev.learning.manage.system.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.learning.manage.system.model.dto.ScheduleCreateDto;
import ru.zelenev.learning.manage.system.model.dto.ScheduleFilterDto;
import ru.zelenev.learning.manage.system.model.dto.SchedulePatchDto;
import ru.zelenev.learning.manage.system.model.dto.ScheduleResponseDto;
import ru.zelenev.learning.manage.system.model.entity.Course;
import ru.zelenev.learning.manage.system.model.entity.Group;
import ru.zelenev.learning.manage.system.model.entity.Schedule;
import ru.zelenev.learning.manage.system.model.entity.Teacher;
import ru.zelenev.learning.manage.system.model.mapper.ScheduleMapper;
import ru.zelenev.learning.manage.system.repository.ScheduleRepository;
import ru.zelenev.learning.manage.system.util.ScheduleSpecifications;
import ru.zelenev.learning.manage.system.util.entity.PagedResponse;
import ru.zelenev.learning.manage.system.util.exceptions.EndTimeBeforeStartTimeException;
import ru.zelenev.learning.manage.system.util.exceptions.ResourceNotFoundException;
import ru.zelenev.learning.manage.system.util.exceptions.TimeBeforeNowMomentException;

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
        Schedule schedule = getScheduleById(id);
        return scheduleMapper.toResponseDto(schedule);
    }

    @Transactional(readOnly = true)
    public PagedResponse<ScheduleResponseDto> findAllByFilter(Pageable pageable, ScheduleFilterDto dto) {
        Page<Schedule> schedulePages = scheduleRepository.findAll(ScheduleSpecifications.byFilter(dto), pageable);
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

        validateTime(scheduleCreateDto.startTime(), scheduleCreateDto.endTime());
        Schedule schedule = scheduleMapper.toEntity(scheduleCreateDto);

        Group group = groupService.getGroupById(scheduleCreateDto.groupId());
        Course course = courseService.getCourseById(scheduleCreateDto.courseId());
        Teacher teacher = teacherService.getTeacherById(scheduleCreateDto.teacherId());

        schedule.setGroup(group);
        schedule.setCourse(course);
        schedule.setTeacher(teacher);

        Schedule createdSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDto(createdSchedule);
    }

    @Transactional
    public void deleteSchedule(UUID id) {
        Schedule schedule = getScheduleById(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public ScheduleResponseDto patchSchedule(UUID id, SchedulePatchDto schedulePatchDto) {
        Schedule schedule = getScheduleById(id);

        scheduleMapper.updateScheduleFromPatchDto(schedulePatchDto, schedule);

        if (schedulePatchDto.courseId() != null) {
            Course course = courseService.getCourseById(schedulePatchDto.courseId());
            schedule.setCourse(course);
        }

        if (schedulePatchDto.groupId() != null) {
            Group group = groupService.getGroupById(schedulePatchDto.groupId());
            schedule.setGroup(group);
        }

        if (schedulePatchDto.teacherId() != null) {
            Teacher teacher = teacherService.getTeacherById(schedulePatchDto.teacherId());
            schedule.setTeacher(teacher);
        }

        validateTime(schedule.getStartTime(), schedule.getEndTime());
        // TODO: создать проверку конфликтов наложения расписания, если такие возникнут на будущее

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return scheduleMapper.toResponseDto(updatedSchedule);
    }

    private Schedule getScheduleById(UUID id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule with id " + id + " does not exists!"));
    }

    private void validateTime(OffsetDateTime startTime, OffsetDateTime endTime) {
        if (endTime.isBefore(startTime))
            throw new EndTimeBeforeStartTimeException("End time can not be before start time!");

        if (endTime.isBefore(OffsetDateTime.now()) || startTime.isBefore(OffsetDateTime.now()))
            throw new TimeBeforeNowMomentException("Time moment can not be before now time!");
    }

}
