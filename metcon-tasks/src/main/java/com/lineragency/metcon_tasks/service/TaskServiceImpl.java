package com.lineragency.metcon_tasks.service;


import com.lineragency.metcon_tasks.data.TaskRepository;
import com.lineragency.metcon_tasks.model.dto.AddTaskDTO;
import com.lineragency.metcon_tasks.model.dto.TaskDTO;
import com.lineragency.metcon_tasks.model.entity.Task;
import com.lineragency.metcon_tasks.service.exception.ApiTaskNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean add(AddTaskDTO data) {
        boolean existingTask = this.taskRepository.existsByRequestId(data.requestId());
        if (existingTask) {
            return false;
        }
        Task task = new Task();
        task.setType(data.type());
        task.setCompany(data.company());
        task.setContainerNumber(data.containerNumber());
        task.setContainerType(data.containerType());
        task.setTruck(data.truck());
        task.setDateTime(data.dateTime());
        task.setRequestId(data.requestId());
        this.taskRepository.save(task);
        return true;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return this.taskRepository.findAll().stream().map(TaskServiceImpl::mapTaskToDTO).collect(Collectors.toList());
    }

    @Override
    public boolean delete(long id) {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            return false;
        }
        this.taskRepository.deleteById(id);
        return true;
    }

    @Override
    public TaskDTO getTaskById(long id) {
        return mapTaskToDTO(this.taskRepository.findById(id).orElseThrow(() ->
                new ApiTaskNotFoundException("Oops! There is no task with id " + id + "!", id)));
    }

    @Override
    public boolean edit(TaskDTO data) {
        Optional<Task> optionalTask = this.taskRepository.findById(data.id());
        if (optionalTask.isEmpty()) {
            return false;
        }
        Task task = optionalTask.get();
        task.setTruck(data.truck());
        task.setDateTime(data.dateTime());
        this.taskRepository.save(task);
        return true;
    }

    private static TaskDTO mapTaskToDTO(Task t) {
        return new TaskDTO(t.getId(), t.getType(), t.getCompany(), t.getContainerNumber(),
                t.getContainerType(), t.getTruck(), t.getDateTime(), t.getRequestId());
    }
}
