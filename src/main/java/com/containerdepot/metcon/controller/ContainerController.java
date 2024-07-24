package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.ContainerAddDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ContainerController {
    private final ContainerService containerService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    public ContainerController(ContainerService containerService, CompanyService companyService, CompanyRepository companyRepository) {
        this.containerService = containerService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
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
    @GetMapping("/containers/company")
    public String viewContainersCompany(
            Model model,
            @RequestParam long id
    ) {
        Optional<Company> optionalCompany = this.companyService.findCompanyById(id);
        if (optionalCompany.isEmpty()) {
            return "redirect:/companies/all";
        }
        List<Container> containersByCompanyId = this.containerService.findAllByCompanyId(optionalCompany.get().getId());
        model.addAttribute("companyContainers", containersByCompanyId);
        return "containers-company";
    }
}
