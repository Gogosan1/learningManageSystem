package ru.zelenev.LearningManageSystem.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GroupStudentId implements Serializable {

    @Column(name = "student_id")
    private UUID studentId;

    @Column(name = "group_id")
    private UUID groupId;
}
