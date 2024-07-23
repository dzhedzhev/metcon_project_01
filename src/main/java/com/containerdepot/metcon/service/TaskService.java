package com.containerdepot.metcon.service;

import com.containerdepot.metcon.model.entities.Task;
import com.containerdepot.metcon.service.dtos.TaskAddDto;

import java.util.List;

public interface TaskService {
    boolean add(TaskAddDto data);
    List<TaskAddDto> getAllTasks();
}
