package ru.zelenev.learning.manage.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.zelenev.learning.manage.system.model.entity.Schedule;
import ru.zelenev.learning.manage.system.util.ScheduleSpecifications;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule> {

    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Page<Schedule> findAll(Specification<Schedule> scheduleSpecification, Pageable pageable);

    @EntityGraph(attributePaths = {"group", "teacher", "course"})
    Optional<Schedule> findById(UUID uuid);

    void deleteByStartTimeBefore(OffsetDateTime oneYearAgo);
}