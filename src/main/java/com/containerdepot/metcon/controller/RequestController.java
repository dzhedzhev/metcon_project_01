package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.model.enums.RequestEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/containers")
public class RequestController {
    @ModelAttribute("allContainerTypes")
    public ContainerIsoType[] allContainerTypes() {
        return ContainerIsoType.values();
    }
    @ModelAttribute("allRequestTypes")
    public RequestEnum[] allRequestTypes() {
        return RequestEnum.values();
    }
    @GetMapping("/requests/add")
    public String viewRequestAdd () {
        return "request-add";
    }
}
