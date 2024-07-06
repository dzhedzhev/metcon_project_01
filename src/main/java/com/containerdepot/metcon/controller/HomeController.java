package com.containerdepot.metcon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String viewIndex() {
        return "index";
    }
    @GetMapping("/home")
    public String viewHome() {
        return "home";
    }
    @GetMapping("/about-us")
    public String viewAboutUs() {
        return "about-us";
    }
    @GetMapping("/contacts")
    public String contactsView() {
        return "contacts";
    }
}
