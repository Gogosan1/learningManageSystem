package ru.zelenev.LearningManageSystem.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.model.entity.Group;
import ru.zelenev.LearningManageSystem.model.entity.GroupStudent;
import ru.zelenev.LearningManageSystem.model.entity.GroupStudentId;
import ru.zelenev.LearningManageSystem.model.entity.Student;
import ru.zelenev.LearningManageSystem.repository.GroupRepository;
import ru.zelenev.LearningManageSystem.repository.GroupStudentRepository;
import ru.zelenev.LearningManageSystem.repository.StudentRepository;
import ru.zelenev.LearningManageSystem.util.exceptions.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupStudentService {

    private final GroupStudentRepository groupStudentRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public void addNewStudentToGroups(Student savedStudent, @NotNull @NotEmpty List<UUID> groupsId) {
        List<Group> groups = groupRepository.findAllById(groupsId);
        if (groups.size() != groupsId.size()) {
            throw new ResourceNotFoundException("Одна или несколько указанных групп не найдены");
        }

        List<GroupStudent> inscriptions = new ArrayList<>();

        for (Group group : groups) {
            GroupStudentId id = new GroupStudentId(savedStudent.getId(), group.getId());

            GroupStudent inscription = new GroupStudent();
            inscription.setId(id);
            inscription.setStudent(savedStudent);
            inscription.setGroup(group);

            inscriptions.add(inscription);

        }

        if (!inscriptions.isEmpty()) {
            groupStudentRepository.saveAll(inscriptions);
        }
    }

    @Transactional
    public void addStudentsToGroup(Group group, @NotNull @NotEmpty List<UUID> studentsId) {
        List<Student> students = studentRepository.findAllById(studentsId);
        if (studentsId.size() != students.size()) {
            throw new ResourceNotFoundException("Один или несколько указанных студентов не найдены");
        }

        List<GroupStudent> inscriptions = new ArrayList<>();

        for (Student student : students) {
            GroupStudentId id = new GroupStudentId(student.getId(), group.getId());

            GroupStudent inscription = new GroupStudent();
            inscription.setId(id);
            inscription.setStudent(student);
            inscription.setGroup(group);

            inscriptions.add(inscription);

        }

        if (!inscriptions.isEmpty()) {
            groupStudentRepository.saveAll(inscriptions);
        }

    }

    @Transactional
    public boolean hasStudentsWithNoAlternativeGroup(UUID groupId) {
        long count = groupStudentRepository.countStudentsWithOnlyThisGroup(groupId);
        return count > 0;
    }

    @Transactional
    public void deleteRelationsByGroupId(UUID groupId) {
        groupStudentRepository.deleteByGroupId(groupId);
    }
}
