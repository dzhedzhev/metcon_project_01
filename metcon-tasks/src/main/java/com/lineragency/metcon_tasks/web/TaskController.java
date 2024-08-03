package com.lineragency.metcon_tasks.web;

import com.lineragency.metcon_tasks.data.TaskRepository;
import com.lineragency.metcon_tasks.model.dto.AddTaskDTO;
import com.lineragency.metcon_tasks.model.dto.TaskDTO;
import com.lineragency.metcon_tasks.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        boolean success = this.taskService.add(data);
        if (!success) {
            throw new IllegalArgumentException("Cannot create task! There is a task associated with this request!");
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/edit")
    public ResponseEntity<TaskDTO> editTask(@RequestBody TaskDTO data) {
        boolean success = this.taskService.edit(data);
        if (!success) {
            throw new IllegalArgumentException("Cannot edit task! There is no task associated with id " + data.id() + "!");
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TaskDTO> deleteTask(@PathVariable("id") long id) {
        this.taskService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.taskService.getTaskById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(this.taskService.getAllTasks());
    }

}
