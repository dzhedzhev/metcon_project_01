package com.containerdepot.metcon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContainerController {
    @GetMapping("/containers-all")
    public String containersAllView() {
        return "/containers-all";
    }
}
