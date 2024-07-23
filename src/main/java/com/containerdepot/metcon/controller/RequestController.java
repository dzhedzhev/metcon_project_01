package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Request;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import com.containerdepot.metcon.service.RequestService;
import com.containerdepot.metcon.service.dtos.RequestAddDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/containers")
public class RequestController { /*TODO transform to REST controller*/
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
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
        boolean success = this.requestService.add(data);
        if (!success) {
            return "redirect:/containers/requests/add";
        }
        return "redirect:/home";
    }
    @GetMapping("/requests/all")
    public String viewRequestsAll() {return "requests-all";}
}
