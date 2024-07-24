package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.entities.Company;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.dtos.CompanyAddDto;
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
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @ModelAttribute("allCompanies")
    public List<Company> getAllCompanies() { return this.companyService.allCompanies();}
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
}
