package ru.zelenev.LearningManageSystem.model.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(
        name = "student_groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_group_student",
                        columnNames = {"group_id", "student_id"}
                )
        }
)
@Setter
public class GroupStudent {
    @EmbeddedId
    private GroupStudentId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;
}
