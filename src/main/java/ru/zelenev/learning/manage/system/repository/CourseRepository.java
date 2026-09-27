package ru.zelenev.learning.manage.system.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.learning.manage.system.model.entity.Course;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {

    @Override
    @EntityGraph(attributePaths = "teacher")
    Optional<Course> findById(UUID id);

    @Override
    @EntityGraph(attributePaths = "teacher")
    Page<Course> findAll(Pageable pageable);

}