package com.containerdepot.metcon.controller;

import com.containerdepot.metcon.model.MetconUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String viewIndex(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails instanceof MetconUserDetails metconUserDetails) {
            model.addAttribute("welcomeMessage", ", " + metconUserDetails.getFullName());
        }
        else {
            model.addAttribute("welcomeMessage", "");
        }


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
