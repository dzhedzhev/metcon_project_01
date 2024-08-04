package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import com.containerdepot.metcon.service.RequestService;
import com.containerdepot.metcon.service.dtos.imports.RequestAddDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/containers")
public class RequestController { /*TODO transform to REST controller*/
    private final RequestService requestService;
    private final ModelMapper modelMapper;

    public RequestController(RequestService requestService, ModelMapper modelMapper) {
        this.requestService = requestService;
        this.modelMapper = modelMapper;
    }
    @ModelAttribute("allRequests")
    public List<Request> getAllRequests() { return this.requestService.findAllContainersByIdDesc();}
    @ModelAttribute("allContainerTypes")
    public ContainerIsoType[] allContainerTypes() {
        return ContainerIsoType.values();
    }
    @ModelAttribute("requestAddData")
    public RequestAddDto requestAddDto() {return new RequestAddDto();}
    @ModelAttribute("allRequestTypes")
    public RequestEnum[] allRequestTypes() {
        return RequestEnum.values();
    }
    @GetMapping("/requests/add")
    public String viewRequestAdd () {
        return "request-add";
    }
    @PostMapping("/requests/add")
    public String doRequestAdd (@Valid RequestAddDto data,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("requestAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.requestAddData",
                    bindingResult);
            return "redirect:/containers/requests/add";
        }
        this.requestService.add(data);
        return "redirect:/containers/requests/all";
    }
    @GetMapping("/requests/all")
    public String viewRequestsAll() {return "requests-all";}
    @DeleteMapping("/requests/delete/{id}")
    public String deleteRequest(@PathVariable("id") Long id) {
        this.requestService.delete(id);
        return "redirect:/containers/requests/all";
    }
    @GetMapping("/requests/edit/{id}")
    public String viewEditRequest(@PathVariable("id") Long id, Model model) {
        Optional<Request> optionalRequest = this.requestService.findRequestById(id);
        if (optionalRequest.isEmpty()) {
            return "redirect:/containers/requests/all";/*TODO exception handling*/
        }
        model.addAttribute("requestId", optionalRequest.get().getId());
        RequestAddDto requestAddDto = this.modelMapper.map(optionalRequest.get(), RequestAddDto.class);
        model.addAttribute("requestAddData", requestAddDto);

        return "request-edit";
    }
    @PostMapping("/requests/edit/{id}")
    public String doEditRequest(@PathVariable("id") Long id, @Valid RequestAddDto data,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("requestAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.requestAddData",
                    bindingResult);
            return "redirect:/containers/requests/edit";
        }
        boolean success = this.requestService.edit(id, data);
        if (!success) {
            return "redirect:/containers/requests/all"; /*TODO error handling*/
        }
        return "redirect:/containers/requests/all";
    }
}
