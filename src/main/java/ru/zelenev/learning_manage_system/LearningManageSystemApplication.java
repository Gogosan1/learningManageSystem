package ru.zelenev.learning_manage_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LearningManageSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningManageSystemApplication.class, args);
    }

}
