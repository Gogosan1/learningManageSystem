package ru.zelenev.learning.manage.system.job;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zelenev.learning.manage.system.repository.ScheduleRepository;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${scheduling.cron-expression}")
    @Transactional
    public void deleteOldScheduleNotes() {
        OffsetDateTime oneYearAgo = OffsetDateTime.now().minusYears(1);

        scheduleRepository.deleteByStartTimeBefore(oneYearAgo);
    }
}
