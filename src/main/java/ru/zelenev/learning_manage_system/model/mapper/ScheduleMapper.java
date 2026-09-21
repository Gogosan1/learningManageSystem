package ru.zelenev.learning_manage_system.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.learning_manage_system.model.dto.ScheduleCreateDto;
import ru.zelenev.learning_manage_system.model.dto.SchedulePatchDto;
import ru.zelenev.learning_manage_system.model.dto.ScheduleResponseDto;
import ru.zelenev.learning_manage_system.model.entity.Schedule;


@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "course", ignore = true)
    Schedule toEntity(ScheduleCreateDto ScheduleCreateDto);

    ScheduleResponseDto toResponseDto(Schedule Schedule);

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "course", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateScheduleFromPatchDto(SchedulePatchDto dto, @MappingTarget Schedule Schedule);

}
