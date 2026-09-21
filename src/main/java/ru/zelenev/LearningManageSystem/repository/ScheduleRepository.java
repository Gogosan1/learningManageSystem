package ru.zelenev.LearningManageSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zelenev.LearningManageSystem.model.entity.Schedule;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {


    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByGroupId(UUID groupId, Pageable pageable);

    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAllByTeacherId(UUID teacherId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.startTime < :targetDate")
    void deleteOlderThan(@Param("targetDate") OffsetDateTime oneYearAgo);
}