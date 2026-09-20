package ru.zelenev.LearningManageSystem.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Setter
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Setter
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

}
