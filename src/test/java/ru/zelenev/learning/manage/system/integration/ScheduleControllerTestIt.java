package ru.zelenev.learning.manage.system.integration;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.liquibase.change-log=classpath:db/test-changelog/db.test-changelog-master.yaml",
        "spring.liquibase.contexts=schedule_test"
})
public class ScheduleControllerTestIt extends AbstractIt {


    @Autowired
    private SpringLiquibase liquibase;

    @Autowired
    private JdbcClient jdbcClient;

    @Nested
    class GetSchedule {

        @Test
        void shouldReturnScheduleByTeacherId() {
            UUID teacherId = jdbcClient.sql("SELECT id FROM lms.teacher WHERE first_name = :name")
                    .param("name", "Pavel")
                    .query(UUID.class)
                    .single();

            restTestClient.get()
                    .uri(
                            uriBuilder -> uriBuilder.path("/api/v1/schedules")
                                    .queryParam("teacherId", teacherId)
                                    .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.pageNumber").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(5)
                    .jsonPath("$.content[*].teacher.id").value(ids -> {
                        List<String> idList = (List<String>) ids;
                        assertThat(idList)
                                .allMatch(id -> id.equals(teacherId.toString()));
                    })
                    .jsonPath("$.content[0].group.name").isEqualTo("DPO-1")
                    .jsonPath("$.content[0].course.title").isEqualTo("Базы данных")
                    .jsonPath("$.content[0].teacher.firstName").isEqualTo("Pavel");
        }

        @Test
        void shouldReturnScheduleByGroupId() {
            UUID groupId = jdbcClient.sql("SELECT id FROM lms.groups WHERE name = :name")
                    .param("name", "DPO-1")
                    .query(UUID.class)
                    .single();

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules")
                            .queryParam("groupId", groupId)
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.pageNumber").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(5)
                    .jsonPath("$.content[*].group.id").value(ids -> {
                        List<String> idList = (List<String>) ids;
                        assertThat(idList).allMatch(id -> id.equals(groupId.toString()));
                    })
                    .jsonPath("$.content[0].group.name").isEqualTo("DPO-1")
                    .jsonPath("$.content[0].course.title").isEqualTo("JAVA")
                    .jsonPath("$.content[0].teacher.firstName").isEqualTo("Pavel");
        }

        @Test
        void shouldReturnScheduleByTeacherIdAndGroupId() {
            UUID teacherId = jdbcClient.sql("SELECT id FROM lms.teacher WHERE first_name = :name")
                    .param("name", "Pavel")
                    .query(UUID.class)
                    .single();

            UUID groupId = jdbcClient.sql("SELECT id FROM lms.groups WHERE name = :name")
                    .param("name", "DPO-1")
                    .query(UUID.class)
                    .single();

            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules")
                            .queryParam("teacherId", teacherId)
                            .queryParam("groupId", groupId)
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.pageNumber").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(3)
                    .jsonPath("$.content[*].teacher.id").value(ids -> {
                        List<String> idList = (List<String>) ids;
                        assertThat(idList).allMatch(id -> id.equals(teacherId.toString()));
                    })
                    .jsonPath("$.content[*].group.id").value(ids -> {
                        List<String> idList = (List<String>) ids;
                        assertThat(idList).allMatch(id -> id.equals(groupId.toString()));
                    })
                    .jsonPath("$.content[0].group.name").isEqualTo("DPO-1")
                    .jsonPath("$.content[0].course.title").isEqualTo("Базы данных")
                    .jsonPath("$.content[0].teacher.firstName").isEqualTo("Pavel");
        }

        @Test
        void shouldReturnAllSchedules_WhenNoParamsProvided() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules").build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.pageNumber").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(8)
                    .jsonPath("$.content").isArray()
                    .jsonPath("$.content[0].group.name").isEqualTo("DPO-1")
                    .jsonPath("$.content[0].course.title").isEqualTo("Базы данных");
        }

    }
}
