package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.imports.TaskAddDto;

import java.util.List;

public interface TaskService {
    boolean add(TaskAddDto data);

    void addTask(TaskAddDto data);
    List<TaskAddDto> getAllTasks();
}
