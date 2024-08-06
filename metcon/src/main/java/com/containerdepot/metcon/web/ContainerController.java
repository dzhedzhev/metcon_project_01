package com.containerdepot.metcon.web;

import com.containerdepot.metcon.data.CompanyRepository;
import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.model.entities.Container;
import com.containerdepot.metcon.model.enums.ContainerIsoType;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.ContainerService;
import com.containerdepot.metcon.service.dtos.imports.ContainerAddDto;
import com.containerdepot.metcon.service.dtos.imports.ContainerEditDto;
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
public class ContainerController {
    private final ContainerService containerService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public ContainerController(ContainerService containerService, CompanyService companyService, CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.containerService = containerService;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("containerAddData")
    public ContainerAddDto containerAddDto() {
        return new ContainerAddDto();
    }
    @ModelAttribute("containerEditData")
    public ContainerEditDto containerEditDto() {
        return new ContainerEditDto();
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
    public List<ContainerAddDto> getAllContainers() {
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

        this.containerService.add(data);

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
        List<ContainerAddDto> containersByCompanyId = this.containerService.findAllByCompanyId(optionalCompany.get().getId());
        model.addAttribute("companyContainers", containersByCompanyId);
        return "containers-company";
    }
    @GetMapping("/containers/edit/{id}")
    public String viewEditContainer(@PathVariable("id") Long id, Model model) {
        Optional<Container> optionalContainer = this.containerService.findContainerById(id);
        if (optionalContainer.isEmpty()) {
            throw new IllegalArgumentException("No such container present!");/*TODO exception handling*/
        }
        if (model.containsAttribute("org.springframework.validation.BindingResult.containerAddData")) {
            return "containers-edit";
        }
        ContainerAddDto containerAddDto = this.modelMapper.map(optionalContainer.get(), ContainerAddDto.class);
        containerAddDto.setId(id);
        containerAddDto.setOwner(optionalContainer.get().getOwner().getNameEn());
        model.addAttribute("containerAddData", containerAddDto);
        return "containers-edit";
    }
    @PostMapping("/containers/edit/{id}")
    public String doEditContainer(@Valid ContainerEditDto data,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("containerEditData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.containerEditData",
                    bindingResult);

            return "redirect:/containers/edit/{id}";
        }

        this.containerService.edit(data);

        return "redirect:/containers/all";
    }
    @DeleteMapping("/containers/delete/{id}")
    public String deleteContainer(@PathVariable("id") Long id) {
        this.containerService.delete(id);
        return "redirect:/containers/all";
    }
}
