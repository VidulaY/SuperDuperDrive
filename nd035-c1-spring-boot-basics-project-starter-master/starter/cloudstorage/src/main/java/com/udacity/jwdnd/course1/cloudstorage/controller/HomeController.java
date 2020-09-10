package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
//    private FileService fileService;

//    public HomeController(FileService fileService) {
//        this.fileService = fileService;
//    }

    @GetMapping
    public String getHomePage() {
        System.out.println("%%%%%%% Inside Home %%%%%");
        return "home";
    }

}
