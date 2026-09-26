package ru.zelenev.learning.manage.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.learning.manage.system.model.entity.Student;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
