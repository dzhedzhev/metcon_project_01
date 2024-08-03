package com.containerdepot.metcon.service.impl;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.data.TaskRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.entities.Task;
import com.containerdepot.metcon.service.TaskService;
import com.containerdepot.metcon.service.dtos.imports.TaskAddDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final RequestRepository requestRepository;
    private final RestClient taskRestClient;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, CompanyRepository companyRepository, RequestRepository requestRepository, RestClient taskRestClient) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
        this.requestRepository = requestRepository;
        this.taskRestClient = taskRestClient;
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

    @Override
    public void addTask(TaskAddDto data) {
        LOGGER.debug("Adding task...");
        this.taskRestClient
                .post()
                .uri("http://localhost:8081/tasks")
                .body(data)
                .retrieve();
    }

    @Override
    public List<TaskAddDto> getAllTasks() {
//        return this.taskRepository.findAll().stream().map(t -> {
//            TaskAddDto taskAddDto = this.modelMapper.map(t, TaskAddDto.class);
//            taskAddDto.setCompany(t.getCompany().getNameEn());
//            taskAddDto.setType(t.getType().name());
//            taskAddDto.setContainerType(t.getContainerType().name());
//            taskAddDto.setRequestId(t.getRequest().getId());
//            return taskAddDto;
//        }).collect(Collectors.toList());
        return this.taskRestClient
                .get()
                .uri("http://localhost:8081/tasks/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public void delete(Long id) {
         this.taskRestClient
                .delete()
                .uri("http://localhost:8081/tasks/delete/{id}", id)
                .retrieve();
    }

    @Override
    public TaskAddDto getTaskById(Long id) {
        return this.taskRestClient
                .get()
                .uri("http://localhost:8081/tasks/{id}", id)
                .retrieve()
                .body(TaskAddDto.class);
    }

    @Override
    public void editTask(TaskAddDto data) {
        this.taskRestClient
                .put()
                .uri("http://localhost:8081/tasks/edit")
                .body(data)
                .retrieve();
    }
}
