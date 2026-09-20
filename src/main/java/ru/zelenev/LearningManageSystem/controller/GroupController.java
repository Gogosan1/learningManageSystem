package ru.zelenev.LearningManageSystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zelenev.LearningManageSystem.model.dto.AddStudentsRequestDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupResponseDto;
import ru.zelenev.LearningManageSystem.service.GroupService;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
public class GroupController {

    public final GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroup(@PathVariable("id")UUID id){
        GroupResponseDto groupResponseDto = groupService.getGroup(id);
        return ResponseEntity.ok(groupResponseDto);
    }

    @GetMapping
    public PagedResponse<GroupResponseDto> getGroups(@PageableDefault(page = 0, size = 20, sort = "id")
                                                                     Pageable pageable){
        return groupService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<GroupResponseDto> postGroup(@Valid @RequestBody GroupCreateDto groupRequestDto){
        GroupResponseDto groupResponseDto = groupService.createGroup(groupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(groupResponseDto);
    }

    @PostMapping("/{groupId}/students")
    public ResponseEntity<Void> addStudentsToGroup(@PathVariable("groupId") UUID groupId,
                                                   @Valid @RequestBody AddStudentsRequestDto dto){
        groupService.addStudentsToGroup(groupId, dto.studentsIds());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") UUID id){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupResponseDto> patchGroup(@PathVariable("id") UUID id, @RequestBody GroupPatchDto groupPatchDto){
        GroupResponseDto groupResponseDto = groupService.patchGroup(id, groupPatchDto);
        return ResponseEntity.ok(groupResponseDto);
    }
}
