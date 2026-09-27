package ru.zelenev.learning.manage.system.util;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.zelenev.learning.manage.system.model.dto.ScheduleFilterDto;
import ru.zelenev.learning.manage.system.model.entity.Schedule;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScheduleSpecifications {

    public static Specification<Schedule> byFilter(ScheduleFilterDto scheduleFilterDto){
        return (root, query, cb) ->  {
            List<Predicate> predicates = new ArrayList<>();

            if(scheduleFilterDto.groupId() != null){
                predicates.add(cb.equal(root.get("group").get("id"), scheduleFilterDto.groupId()));
            }

            if(scheduleFilterDto.teacherId() != null){
                predicates.add(cb.equal(root.get("teacher").get("id"), scheduleFilterDto.teacherId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
