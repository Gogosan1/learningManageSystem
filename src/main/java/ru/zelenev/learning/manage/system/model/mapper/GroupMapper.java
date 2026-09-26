package ru.zelenev.learning.manage.system.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.learning.manage.system.model.dto.GroupCreateDto;
import ru.zelenev.learning.manage.system.model.dto.GroupPatchDto;
import ru.zelenev.learning.manage.system.model.dto.GroupResponseDto;
import ru.zelenev.learning.manage.system.model.entity.Group;


@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group toEntity(GroupCreateDto GroupCreateDto);

    GroupResponseDto toResponseDto(Group Group);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGroupFromPatchDto(GroupPatchDto dto, @MappingTarget Group Group);

}
