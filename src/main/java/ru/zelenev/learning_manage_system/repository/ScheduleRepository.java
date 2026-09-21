package ru.zelenev.learning_manage_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.learning_manage_system.model.entity.Schedule;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {


    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByGroupId(UUID groupId, Pageable pageable);

    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByTeacherId(UUID teacherId, Pageable pageable);

    void deleteByStartTimeBefore(OffsetDateTime oneYearAgo);
}