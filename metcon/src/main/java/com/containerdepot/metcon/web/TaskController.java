package com.containerdepot.metcon.web;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.RequestService;
import com.containerdepot.metcon.service.TaskService;
import com.containerdepot.metcon.service.dtos.imports.TaskAddDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/containers/tasks")
public class TaskController {
    private final TaskService taskService;
    private final RequestService requestService;

    public TaskController(TaskService taskService, RequestService requestService) {
        this.taskService = taskService;
        this.requestService = requestService;
    }

    @ModelAttribute("taskAddData")
    public TaskAddDto taskAddDto() {
        return new TaskAddDto();
    }

    @ModelAttribute("allTasks")
    public List<TaskAddDto> getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @GetMapping("/add")
    public String viewTaskAdd(
            Model model,
            @RequestParam long id
    ) {
        Optional<Request> byId = this.requestService.findRequestById(id);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("Can not create task! There is no request such request!");
        }
        Request request = byId.get();
        TaskAddDto taskAddDto = new TaskAddDto();
        taskAddDto.setType(request.getType().name());
        taskAddDto.setCompany(request.getCompany().getNameEn());
        taskAddDto.setContainerNumber(request.getContainerNumber());
        taskAddDto.setContainerType(request.getContainerType().name());
        taskAddDto.setTruck(request.getTruck());
        taskAddDto.setRequestId(request.getId());
        model.addAttribute("taskAddDto", taskAddDto);
        return "task-add";
    }

    @PostMapping("/add")
    public String doTaskAdd(@Valid TaskAddDto data,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskAddData",
                    bindingResult);
            return "redirect:/containers/tasks/add";
        }
        taskService.addTask(data);
        return "redirect:/containers/tasks/all";
    }
    @GetMapping("/all")
    public String viewAllTasks() {
        return "tasks-all";
    }
    @GetMapping("/edit/{id}")
    public String viewEditTask(@PathVariable("id") Long id, Model model) {
        TaskAddDto taskById = this.taskService.getTaskById(id);
        model.addAttribute("taskDto", taskById);
        return "task-edit";
    }
    @PostMapping("/edit/{id}")
    public String doEditTask(@PathVariable("id") Long id, @Valid TaskAddDto data,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskEditData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskEditData",
                    bindingResult);
            return "redirect:/containers/tasks/edit/{id}";
        }
        taskService.editTask(data);
        return "redirect:/containers/tasks/all";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        this.taskService.delete(id);
        return "redirect:/containers/tasks/all";
    }
}
