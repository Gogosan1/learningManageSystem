package ru.zelenev.LearningManageSystem.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.model.dto.GroupCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Group;
import ru.zelenev.LearningManageSystem.model.mapper.GroupMapper;
import ru.zelenev.LearningManageSystem.repository.GroupRepository;
import ru.zelenev.LearningManageSystem.util.entity.PagedResponse;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final GroupStudentService groupStudentService;

    @Transactional(readOnly = true)
    public GroupResponseDto getGroup(UUID id) {
        Group group = getGroupByIdOrThrow(id);
        return groupMapper.toResponseDto(group);
    }

    @Transactional(readOnly = true)
    public PagedResponse<GroupResponseDto> findAll(Pageable pageable) {
        Page<Group> page = groupRepository.findAll(pageable);
        List<GroupResponseDto> content = page.getContent().stream()
                .map(groupMapper::toResponseDto)
                .toList();

        return new PagedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Transactional
    public GroupResponseDto createGroup(GroupCreateDto dto) {
        Group group = groupMapper.toEntity(dto);
        Group savedGroup = groupRepository.save(group);
        return groupMapper.toResponseDto(savedGroup);
    }

    @Transactional
    public void deleteGroup(UUID id) {
        Group group = getGroupByIdOrThrow(id);

        if (groupStudentService.hasStudentsWithNoAlternativeGroup(id)) {
            throw new IllegalStateException(
                    "Нельзя удалить группу. Некоторые студенты не имеют альтернативной группы."
            );
        }
        groupStudentService.deleteRelationsByGroupId(id);
        groupRepository.delete(group);
    }

    @Transactional
    public GroupResponseDto patchGroup(UUID id, GroupPatchDto dto) {
        Group group = getGroupByIdOrThrow(id);

        groupMapper.updateGroupFromPatchDto(dto, group);

        Group updatedGroup = groupRepository.save(group);
        return groupMapper.toResponseDto(updatedGroup);
    }

    @Transactional
    public void addStudentsToGroup(UUID groupId, @NotNull @NotEmpty List<UUID> studentsId) {
        Group group = getGroupByIdOrThrow(groupId);

        groupStudentService.addStudentsToGroup(group, studentsId);
    }


    Group getGroupByIdOrThrow(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group with id " + id + " does not exists"));
    }

}

