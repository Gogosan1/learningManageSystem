package ru.zelenev.LearningManageSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zelenev.LearningManageSystem.model.entity.Group;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}