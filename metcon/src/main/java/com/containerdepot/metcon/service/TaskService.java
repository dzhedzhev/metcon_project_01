package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.imports.TaskAddDto;

import java.util.List;

public interface TaskService {

    void addTask(TaskAddDto data);
    List<TaskAddDto> getAllTasks();

    void delete(Long id);
    TaskAddDto getTaskById(Long id);

    void editTask(TaskAddDto data);
}
