package com.containerdepot.metcon.data;

import com.containerdepot.metcon.model.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByRequestId(long requestId);
}
