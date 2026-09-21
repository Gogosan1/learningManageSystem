package ru.zelenev.learning_manage_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.learning_manage_system.model.entity.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
