package com.lineragency.metcon_tasks.service;

import com.lineragency.metcon_tasks.model.dto.AddTaskDTO;
import com.lineragency.metcon_tasks.model.dto.TaskDTO;
import com.lineragency.metcon_tasks.model.entity.Task;

import java.util.List;

public interface TaskService {
    TaskDTO add(AddTaskDTO data);
    List<TaskDTO> getAllTasks();

    boolean delete(long id);

    TaskDTO getTaskById(long id);

    boolean edit(TaskDTO data);
}
