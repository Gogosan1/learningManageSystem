package ru.zelenev.LearningManageSystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.LearningManageSystem.repository.ScheduleRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private ScheduleRepository scheduleRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteOldScheduleNotes() {
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);

        scheduleRepository.deleteOlderThan(oneYearAgo);
    }
}
