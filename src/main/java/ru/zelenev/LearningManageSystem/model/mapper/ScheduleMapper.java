package ru.zelenev.LearningManageSystem.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleCreateDto;
import ru.zelenev.LearningManageSystem.model.dto.SchedulePatchDto;
import ru.zelenev.LearningManageSystem.model.dto.ScheduleResponseDto;
import ru.zelenev.LearningManageSystem.model.entity.Schedule;


@org.mapstruct.Mapper(componentModel = "spring")
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
