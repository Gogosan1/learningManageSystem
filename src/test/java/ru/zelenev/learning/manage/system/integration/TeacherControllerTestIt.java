package ru.zelenev.learning.manage.system.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import ru.zelenev.learning.manage.system.model.dto.TeacherCreateDto;
import ru.zelenev.learning.manage.system.model.dto.TeacherPatchDto;
import ru.zelenev.learning.manage.system.model.dto.TeacherResponseDto;
import ru.zelenev.learning.manage.system.model.entity.Teacher;
import ru.zelenev.learning.manage.system.repository.TeacherRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TeacherControllerTestIt extends AbstractIt {

    @Autowired
    private TeacherRepository teacherRepository;

    private UUID teacherId;

    @BeforeEach
    void setUp() {

        Teacher teacher1 = new Teacher();
        teacher1.setFirstName("Pavel");
        teacher1.setLastName("String");
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Elena");
        teacher2.setLastName("Polnochnaya");

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherId = teacher1.getId();
    }

    @AfterEach
    void cleanUp() {
        teacherRepository.deleteAll();
    }

    @Nested
    class GetTeacher {

        @Test
        void shouldGetAndReturnTeacherById() {

            restTestClient.get()
                    .uri("/api/v1/teachers/{id}", teacherId)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.firstName").isEqualTo("Pavel")
                    .jsonPath("$.lastName").isEqualTo("String");
        }

        @Test
        void shouldReturnNotFoundStatus() {
            UUID id = UUID.randomUUID();
            restTestClient.get()
                    .uri("/api/v1/teachers/{id}", id)
                    .exchange()
                    .expectStatus().isNotFound()
                    .expectBody()
                    .jsonPath("$.error").isEqualTo("Resource not found")
                    .jsonPath("$.message").isEqualTo("Teacher with id " + id + " does not exist!");
        }

        @Test
        void shouldGetAndReturnTeachersPages() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v1/teachers")
                            .queryParam("page", "0")
                            .queryParam("size", "20")
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.content").isArray()
                    .jsonPath("$.content[0].id").isEqualTo(teacherId.toString())
                    .jsonPath("$.content.length()").isEqualTo(2);
        }
    }

    @Nested
    class PostTeacher {
        @Test
        void shouldCreateNewTeacher() {
            TeacherCreateDto createDto = new TeacherCreateDto("Петр", "Петров");

            TeacherResponseDto responseBody = restTestClient.post()
                    .uri("/api/v1/teachers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(createDto)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(TeacherResponseDto.class)
                    .returnResult()
                    .getResponseBody();

            assertThat(responseBody).isNotNull();
            assertThat(responseBody.id()).isNotNull();

            boolean existsInDb = teacherRepository.existsById(responseBody.id());
            assertThat(existsInDb).isTrue();
        }

    }

    @Nested
    class PatchTeacher {
        @Test
        void shouldPatchExistingTeacher() {
            TeacherPatchDto patchDto = new TeacherPatchDto(null, "Сидоров");

            restTestClient.patch()
                    .uri("/api/v1/teachers/{id}", teacherId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(patchDto)
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.id").isEqualTo(teacherId.toString())
                    .jsonPath("$.firstName").isEqualTo("Pavel")
                    .jsonPath("$.lastName").isEqualTo("Сидоров");
        }

    }

    @Nested
    class DeleteTeacher {
        @Test
        void shouldDeleteTeacherById() {
            restTestClient.delete()
                    .uri("/api/v1/teachers/{id}", teacherId)
                    .exchange()
                    .expectStatus().isNoContent();

            boolean existsInDb = teacherRepository.existsById(teacherId);
            assertThat(existsInDb).isFalse();
        }
    }

}
