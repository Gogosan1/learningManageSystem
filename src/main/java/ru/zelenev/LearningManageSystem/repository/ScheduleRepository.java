package ru.zelenev.LearningManageSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.LearningManageSystem.model.entity.Schedule;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID>{


    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByGroupId(UUID groupId, Pageable pageable);

    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByTeacherId(UUID teacherId, Pageable pageable);
}