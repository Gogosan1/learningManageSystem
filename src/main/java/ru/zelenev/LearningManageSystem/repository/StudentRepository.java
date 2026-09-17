package ru.zelenev.LearningManageSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.LearningManageSystem.model.entity.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
