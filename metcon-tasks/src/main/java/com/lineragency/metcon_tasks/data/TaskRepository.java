package com.lineragency.metcon_tasks.data;

import com.lineragency.metcon_tasks.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByRequestId(long request);
}
