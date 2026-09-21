package ru.zelenev.learning_manage_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GroupStudentRepository extends JpaRepository<GroupStudent, GroupStudentId> {

    @Query("SELECT COUNT(gs) FROM GroupStudent gs " +
            "WHERE gs.id.groupId = :groupId " +
            "AND (SELECT COUNT(sub) FROM GroupStudent sub WHERE sub.id.studentId = gs.id.studentId) = 1")
    long countStudentsWithOnlyThisGroup(@Param("groupId") UUID groupId);

    @Modifying
    @Query("DELETE FROM GroupStudent gs WHERE gs.id.groupId = :groupId")
    void deleteByGroupId(@Param("groupId") UUID groupId);
}