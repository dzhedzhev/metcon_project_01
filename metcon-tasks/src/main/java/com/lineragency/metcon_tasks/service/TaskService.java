package com.lineragency.metcon_tasks.service;

import com.lineragency.metcon_tasks.model.dto.AddTaskDTO;
import com.lineragency.metcon_tasks.model.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    boolean add(AddTaskDTO data);
    List<TaskDTO> getAllTasks();

    void delete(long id);

    TaskDTO getTaskById(long id);

    boolean edit(TaskDTO data);
}
