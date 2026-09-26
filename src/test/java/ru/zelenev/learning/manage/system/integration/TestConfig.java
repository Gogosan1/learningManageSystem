package ru.zelenev.learning.manage.system.integration;

import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;


@TestConfiguration
public class TestConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer postgreSQLContainer() {
        return new PostgreSQLContainer("postgres:16-alpine");
    }

}
