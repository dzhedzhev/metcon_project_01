package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;
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
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }
    @ModelAttribute("allCompanies")
    public List<CompanyDto> getAllCompanies() { return this.companyService.allCompanies();}
    @ModelAttribute("companyAddData")
    public CompanyAddDto companyAddDto() {return new CompanyAddDto();}
    @GetMapping("/add")
    private String viewCompanyAdd() {return "company-add";}
    @PostMapping("/add")
    public String doAddCompany(@Valid CompanyAddDto data,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddData",
                    bindingResult);

            return "redirect:/companies/add";
        }

        boolean success = this.companyService.add(data);
        if (!success) {
            return "redirect:/companies/add";
        }

        return "redirect:/home";
    }
    @GetMapping("/all")
    public String viewCompaniesAll() {return "companies-all";}
    @GetMapping("/edit/{id}")
    public String viewEditCompany(@PathVariable("id") Long id, Model model) {
        Optional<Company> companyById = this.companyService.findCompanyById(id);
        if (companyById.isEmpty()) {
            return "redirect:/companies/all";
        }
        CompanyAddDto mappedCompanyAddDto = this.modelMapper.map(companyById.get(), CompanyAddDto.class);
        mappedCompanyAddDto.setId(id);
        model.addAttribute("companyAddDto", mappedCompanyAddDto);
        return "company-edit";
    }
    @GetMapping("/edit")
    public String viewErrEditCompany() {return "company-edit";}
    @PostMapping("/edit/{id}")
    public String doEditCompany(@Valid @ModelAttribute("companyAddData") CompanyAddDto data,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyAddData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddData",
                    bindingResult);
            return "redirect:/companies/edit/{id}";/*TODO try to fix error handling*/
        }
        boolean success = this.companyService.edit(data);
        if (!success) {
            return "redirect:/companies/edit/{id}"; /*TODO error handling*/
        }
        return "redirect:/companies/all";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRequest(@PathVariable("id") Long id) {
        try {
            this.companyService.delete(id);
        } catch (Exception exception) {
            throw new IllegalStateException("Cannot delete company!");/*TODO exception handling*/
        }

        return "redirect:/companies/all";
    }
}
