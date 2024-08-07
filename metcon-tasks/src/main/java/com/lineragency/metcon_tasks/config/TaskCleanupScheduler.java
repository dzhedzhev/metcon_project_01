package com.lineragency.metcon_tasks.config;

import com.lineragency.metcon_tasks.data.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class TaskCleanupScheduler {

    private final TaskRepository taskRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(TaskCleanupScheduler.class);


    public TaskCleanupScheduler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")  // Every day at midnight
    public void deleteOldEntities() {
        LocalDateTime cutoffDateTime = LocalDateTime.now().minusMonths(6);
        int tasksDeleted = taskRepository.deleteByDateTimeBefore(cutoffDateTime);

        LOGGER.info("Deleted all tasks before: " + cutoffDateTime + ". Affected records: " + tasksDeleted);
    }
}
