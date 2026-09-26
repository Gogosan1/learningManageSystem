package ru.zelenev.learning.manage.system.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.learning.manage.system.model.entity.Group;
import ru.zelenev.learning.manage.system.model.entity.Student;
import ru.zelenev.learning.manage.system.repository.GroupRepository;
import ru.zelenev.learning.manage.system.repository.StudentRepository;
import ru.zelenev.learning.manage.system.util.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupStudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void addNewStudentToGroups(Student savedStudent, @NotNull @NotEmpty List<UUID> groupsId) {
        List<Group> groups = groupRepository.findAllById(groupsId);
        if (groups.size() != groupsId.size()) {
            throw new ResourceNotFoundException("Одна или несколько указанных групп не найдены");
        }

        for (Group group : groups) {
            group.getStudents().add(savedStudent);
        }
        groupRepository.saveAll(groups);
    }

    @Transactional
    public void addStudentsToGroup(Group group, @NotNull @NotEmpty List<UUID> studentsId) {
        List<Student> students = studentRepository.findAllById(studentsId);
        if (studentsId.size() != students.size()) {
            throw new ResourceNotFoundException("Один или несколько указанных студентов не найдены");
        }

        group.getStudents().addAll(students);
        groupRepository.save(group);
    }

    @Transactional
    public boolean hasStudentsWithNoAlternativeGroup(UUID groupId) {
        long count = groupRepository.countStudentsWithOnlyThisGroup(groupId);
        return count > 0;
    }
}
