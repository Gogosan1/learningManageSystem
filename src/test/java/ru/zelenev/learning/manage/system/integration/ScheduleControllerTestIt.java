package ru.zelenev.learning.manage.system.integration;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.TestPropertySource;
import ru.zelenev.learning.manage.system.model.dto.ScheduleResponseDto;
import ru.zelenev.learning.manage.system.util.entity.PagedResponse;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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

            PagedResponse<ScheduleResponseDto> response =
                    restTestClient.get()
                            .uri(
                                    uriBuilder -> uriBuilder.path("/api/v1/schedules")
                                            .queryParam("teacherId", teacherId)
                                            .build())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(new ParameterizedTypeReference<
                                    PagedResponse<ScheduleResponseDto>>() {
                            })
                            .returnResult()
                            .getResponseBody();

            assertThat(response.getTotalElements()).isEqualTo(5);
            assertThat(response.getContent())
                    .allMatch(schedule ->
                            schedule.teacher().id().equals(teacherId));
        }

        @Test
        void shouldReturnScheduleByGroupId() {
            UUID groupId = jdbcClient.sql("SELECT id FROM lms.groups WHERE name = :name")
                    .param("name", "DPO-1")
                    .query(UUID.class)
                    .single();

            PagedResponse<ScheduleResponseDto> response =
                    restTestClient.get()
                            .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules")
                                    .queryParam("groupId", groupId)
                                    .build())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(new ParameterizedTypeReference<
                                    PagedResponse<ScheduleResponseDto>>() {
                            })
                            .returnResult()
                            .getResponseBody();

            assertThat(response.getTotalElements()).isEqualTo(3);

            assertThat(response.getContent())
                    .allMatch(schedule ->
                            schedule.group().id().equals(groupId));
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

            PagedResponse<ScheduleResponseDto> response =
                    restTestClient.get()
                            .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules")
                                    .queryParam("teacherId", teacherId)
                                    .queryParam("groupId", groupId)
                                    .build())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(new ParameterizedTypeReference<
                                    PagedResponse<ScheduleResponseDto>>() {
                            })
                            .returnResult()
                            .getResponseBody();

            assertThat(response.getTotalElements()).isEqualTo(3);

            assertThat(response.getContent())
                    .allMatch(schedule ->
                            schedule.group().id().equals(groupId) &&
                                    schedule.teacher().id().equals(teacherId));
        }

        @Test
        void shouldReturnAllSchedules_WhenNoParamsProvided() {
            restTestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v1/schedules").build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.pageNumber").isEqualTo(0)
                    .jsonPath("$.totalElements").isEqualTo(8);
        }

    }
}
