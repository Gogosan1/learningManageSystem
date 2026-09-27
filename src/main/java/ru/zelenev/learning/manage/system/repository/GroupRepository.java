package ru.zelenev.learning.manage.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zelenev.learning.manage.system.model.entity.Group;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {

    @Query("SELECT COUNT(s) FROM Group g JOIN g.students s " +
            "WHERE g.id = :groupId " +
            "AND (SELECT COUNT(otherG) FROM Group otherG WHERE s MEMBER OF otherG.students) = 1")
    long countStudentsWithOnlyThisGroup(@Param("groupId") UUID groupId);

}