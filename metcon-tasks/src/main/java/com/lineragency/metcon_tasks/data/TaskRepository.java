package com.lineragency.metcon_tasks.data;

import com.lineragency.metcon_tasks.model.entity.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByRequestId(long request);
    @Transactional
    int deleteByDateTimeBefore(LocalDateTime dateTime);
}
