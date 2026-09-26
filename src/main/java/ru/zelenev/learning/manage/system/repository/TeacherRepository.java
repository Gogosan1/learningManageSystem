package ru.zelenev.learning.manage.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.learning.manage.system.model.entity.Teacher;

import java.util.UUID;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
}