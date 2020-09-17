package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, CredentialService credentialService, NoteService noteService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        System.out.println("%%%%%%% Inside Home %%%%%");
        model.addAttribute("files", this.fileService.getAllFiles());
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getAllCredentials());
        model.addAttribute("notes", this.noteService.getAllNotes());
        return "home";
    }


}
