package ru.zelenev.LearningManageSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.LearningManageSystem.model.entity.Teacher;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}