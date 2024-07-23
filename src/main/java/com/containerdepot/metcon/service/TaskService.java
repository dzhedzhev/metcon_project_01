package com.containerdepot.metcon.service;

import com.containerdepot.metcon.service.dtos.TaskAddDto;

public interface TaskService {
    boolean add(TaskAddDto data);
}
