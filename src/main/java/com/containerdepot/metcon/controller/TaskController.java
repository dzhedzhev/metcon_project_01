package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.data.RequestRepository;
import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.service.TaskService;
import com.containerdepot.metcon.service.dtos.RequestAddDto;
import com.containerdepot.metcon.service.dtos.TaskAddDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/containers/tasks")
public class TaskController {
    private final RequestRepository requestRepository;
    private final TaskService taskService;

    public TaskController(RequestRepository requestRepository, TaskService taskService) {
        this.requestRepository = requestRepository;
        this.taskService = taskService;
    }
    @ModelAttribute("taskAddData")
    public TaskAddDto taskAddDto() {return new TaskAddDto();}

    @GetMapping("/add")
    public String viewTaskAdd(
            Model model,
            @RequestParam long id
    ) {
        Optional<Request> byId = this.requestRepository.findById(id);
        if (byId.isEmpty()) {
            return "redirect:/containers/requests/all";
        }
        Request request = byId.get();
        TaskAddDto taskAddDto = new TaskAddDto();
        taskAddDto.setType(request.getType().name());/*TODO refactor to String?*/
        taskAddDto.setCompany(request.getCompany().getNameEn());
        taskAddDto.setContainerNumber(request.getContainerNumber());
        taskAddDto.setContainerType(request.getContainerType().name());
        taskAddDto.setTruck(request.getTruck());
        taskAddDto.setRequestId(request.getId());
        model.addAttribute("taskAddDto", taskAddDto);
        return "task-add";
    }
    @PostMapping("/add")
    public String doTaskAdd (@Valid TaskAddDto data,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskAddData",
                    bindingResult);
            return "redirect:/containers/tasks/add";
        }
        boolean success = this.taskService.add(data);
        if (!success) {
            return "redirect:/containers/tasks/add";
        }
        return "redirect:/home";
    }
}