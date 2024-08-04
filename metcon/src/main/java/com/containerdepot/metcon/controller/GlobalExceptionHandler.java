package com.containerdepot.metcon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleObjectNotFound(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("object-not-found");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
