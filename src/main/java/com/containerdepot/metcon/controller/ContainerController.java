package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.ContainerAddDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ContainerController {
    private final ContainerService containerService;
    private final CompanyService companyService;

    public ContainerController(ContainerService containerService, CompanyService companyService) {
        this.containerService = containerService;
        this.companyService = companyService;
    }

    @ModelAttribute("containerAddData")
    public ContainerAddDto containerAddDto() {
        return new ContainerAddDto();
    }

    @ModelAttribute("allCompaniesNames")
    public List<String> allCompaniesNames() {
        return this.companyService.allCompaniesNames();
    }

    @ModelAttribute("allContainerTypes")
    public ContainerIsoType[] allContainerTypes() {
        return ContainerIsoType.values();
    }

    @ModelAttribute("allContainers")
    public List<Container> getAllContainers() {
        return this.containerService.getAllOrderedByReceivedDesc();
    }

    @GetMapping("/containers/all")
    public String containersAllView() {
        return "/containers-all";
    }

    @GetMapping("/containers/add")
    public String containersAddView() {
        return "/containers-add";
    }

    @PostMapping("/containers/add")
    public String doAddContainer(@Valid ContainerAddDto data,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("containerAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.containerAddData",
                    bindingResult);

            return "redirect:/containers/add";
        }

        boolean success = this.containerService.add(data);
        if (!success) {
            return "redirect:/containers/add";
        }

        return "redirect:/containers/all";
    }
}
