package ru.zelenev.LearningManageSystem.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.LearningManageSystem.model.dto.GroupCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupPatchDto;
import ru.zelenev.LearningManageSystem.model.dto.GroupResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Group;


@org.mapstruct.Mapper(componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupCreateDto GroupCreateDto);

    GroupResponseDto toResponseDto(Group Group);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGroupFromPatchDto(GroupPatchDto dto, @MappingTarget Group Group);

}
