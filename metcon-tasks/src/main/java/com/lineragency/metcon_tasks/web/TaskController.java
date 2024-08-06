package com.lineragency.metcon_tasks.web;

import com.lineragency.metcon_tasks.model.dto.AddTaskDTO;
import com.lineragency.metcon_tasks.model.dto.TaskDTO;
import com.lineragency.metcon_tasks.model.entity.Task;
import com.lineragency.metcon_tasks.service.TaskService;
import com.lineragency.metcon_tasks.service.exception.ApiTaskNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody AddTaskDTO data) {
        TaskDTO taskDTO = this.taskService.add(data);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(taskDTO.id())
                        .toUri()
                ).body(taskDTO);
    }
    @PutMapping("/edit")
    public ResponseEntity<TaskDTO> editTask(@RequestBody TaskDTO data) {
        boolean success = this.taskService.edit(data);
        if (!success) {
            throw new ApiTaskNotFoundException("Cannot edit task! There is no task associated with id " + data.id() + "!", data.id());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") long id) {
        boolean success = this.taskService.delete(id);
        if (!success) {
            throw new ApiTaskNotFoundException("Cannot delete task! There is no task associated with id " + id + "!", id);
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.taskService.getTaskById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(this.taskService.getAllTasks());
    }

}
