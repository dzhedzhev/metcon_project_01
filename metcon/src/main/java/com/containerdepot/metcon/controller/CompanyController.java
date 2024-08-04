package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.dtos.exports.CompanyDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyAddDto;
import com.containerdepot.metcon.service.dtos.imports.CompanyEditDto;
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
    @ModelAttribute("companyEditData")
    public CompanyEditDto companyEditDto() {return new CompanyEditDto();}
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

        this.companyService.add(data);
        return "redirect:/companies/all";
    }
    @GetMapping("/all")
    public String viewCompaniesAll() {return "companies-all";}
    @GetMapping("/edit/{id}")
    public String viewEditCompany(@PathVariable("id") Long id, Model model) {
        Optional<Company> companyById = this.companyService.findCompanyById(id);
        if (companyById.isEmpty()) {
            throw new IllegalArgumentException("There is no company with specified id!");
        }
        if (model.containsAttribute("org.springframework.validation.BindingResult.companyEditData")) {
            return "company-edit";
        }
        CompanyEditDto mappedCompanyEditDto = this.modelMapper.map(companyById.get(), CompanyEditDto.class);
        model.addAttribute("companyEditData", mappedCompanyEditDto);
        return "company-edit";
    }
    @GetMapping("/edit")
    public String viewErrEditCompany() {return "company-edit";}
    @PostMapping("/edit/{id}")
    public String doEditCompany(@Valid @ModelAttribute("companyEditData") CompanyEditDto data,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyEditData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyEditData",
                    bindingResult);
            return "redirect:/companies/edit/{id}";/*TODO try to fix error handling*/
        }
        this.companyService.edit(data);
        return "redirect:/companies/all";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRequest(@PathVariable("id") Long id) {
        this.companyService.delete(id);
        return "redirect:/companies/all";
    }
}
