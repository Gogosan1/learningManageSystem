import org.springframework.boot.SpringApplication;
import ru.zelenev.learning_manage_system.LearningManageSystemApplication;
import ru.zelenev.learning_manage_system.integration.TestConfig;

public class TestApplication {
    public static void main(String[] args) {
        SpringApplication
                .from(LearningManageSystemApplication::main)
                .with(TestConfig.class)
                .run(args);
    }
}
