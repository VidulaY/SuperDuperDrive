package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    public ErrorController() {
    }

    @GetMapping
    public String getResultPage(Model model){
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
