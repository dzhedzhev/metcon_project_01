package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.service.dtos.CompanyAddDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companies")
public class CompanyController {
    @ModelAttribute("companyAddData")
    public CompanyAddDto companyAddDto() {return new CompanyAddDto();}
    @GetMapping("/add")
    private String viewCompanyAdd() {return "company-add";}
}
