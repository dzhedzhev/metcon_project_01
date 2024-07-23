package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.data.TaskRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.entities.Task;
import com.containerdepot.metcon.service.TaskService;
import com.containerdepot.metcon.service.dtos.TaskAddDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final RequestRepository requestRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, CompanyRepository companyRepository, RequestRepository requestRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public boolean add(TaskAddDto data) {
        boolean existingTask = this.taskRepository.existsByRequestId(data.getRequestId());
        if (existingTask) {
            return false;
        }
        Optional<Company> optionalCompany = this.companyRepository.findByNameEn(data.getCompany());
        if (optionalCompany.isEmpty()) {
            return false;
        }
        Optional<Request> optionalRequest = this.requestRepository.findById(data.getRequestId());
        if (optionalRequest.isEmpty()) {
            return false;
        }
        Task task = this.modelMapper.map(data, Task.class);
        task.setCompany(optionalCompany.get());
        task.setRequest(optionalRequest.get());
        this.taskRepository.save(task);
        return true;
    }
}
