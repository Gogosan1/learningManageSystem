package ru.zelenev.LearningManageSystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.SchedulePatchDto;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleResponseDto;
import ru.zelenev.LearningManageSystem.service.ScheduleService;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable("id") UUID id) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.getSchedule(id);
        return ResponseEntity.ok(scheduleResponseDto);
    }


    // TODO : переписать на specification
    @GetMapping("/{groupId}")
    public PagedResponse<ScheduleResponseDto> getSchedulesByGroupId(
            @PageableDefault(page = 0, size = 20) Pageable pageable,
            @PathVariable("groupId") UUID groupId) {

        return scheduleService.findAllByGroupId(groupId, pageable);
    }

    @GetMapping("/{teacherId}")
    public PagedResponse<ScheduleResponseDto> getSchedulesByTeacherId(
            @PageableDefault(page = 0, size = 20) Pageable pageable,
            @PathVariable("teacherId") UUID teacherId) {

        return scheduleService.findAllByTeacherId(teacherId, pageable);
    }


    @PostMapping
    public ResponseEntity<ScheduleResponseDto> postGroup(@Valid @RequestBody ScheduleCreateDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.createScheduleNote(scheduleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") UUID id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> patchGroup(@PathVariable("id") UUID id, @RequestBody SchedulePatchDto schedulePatchDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.patchSchedule(id, schedulePatchDto);
        return ResponseEntity.ok(scheduleResponseDto);
    }

}
