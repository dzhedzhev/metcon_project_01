package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.enums.UserRole;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.UserService;
import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, CompanyService companyService, ModelMapper modelMapper) {
        this.userService = userService;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("allCompaniesNames")
    public List<String> allCompaniesNames() {
        return this.companyService.allCompaniesNames();
    }
    @ModelAttribute("signUpData")
    public SignUpDto signUpDto() {return new SignUpDto();}
    @ModelAttribute("allRoles")
    public UserRole[] allRoles() {
        return UserRole.values();
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "/login";
    }
    @GetMapping("/sign-up")
    public String viewSignUp() {
        return "/sign-up";
    }
    @PostMapping("/sign-up")
    public String doSignUp(@Valid SignUpDto data,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("signUpData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.signUpData",
                    bindingResult);

            return "redirect:/sign-up";
        }

        boolean success = this.userService.signUp(data);
        if(!success) {
            return "redirect:/sign-up";
        }

        return "redirect:/login";
    }

}
