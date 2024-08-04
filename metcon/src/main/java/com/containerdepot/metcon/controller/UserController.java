package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.enums.UserRole;
import com.containerdepot.metcon.service.CompanyService;
import com.containerdepot.metcon.service.UserService;
import com.containerdepot.metcon.service.dtos.imports.SignUpDto;
import com.containerdepot.metcon.service.dtos.imports.UserDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    @ModelAttribute("allUsers")
    public List<UserDto> allUsers() {
        return this.userService.getAllUsers();
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
    @GetMapping("/register")
    public String viewSignUp() {
        return "register";
    }
    @GetMapping("/users")
    public String viewAllUsers() {
        return "users-all";
    }
    @PostMapping("/register")
    public String doSignUp(@Valid SignUpDto data,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("signUpData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.signUpData",
                    bindingResult);

            return "redirect:/register";
        }
        this.userService.signUp(data);

        return "redirect:/login";
    }
    @DeleteMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        this.userService.delete(id);
        return "redirect:/users";
    }

}
