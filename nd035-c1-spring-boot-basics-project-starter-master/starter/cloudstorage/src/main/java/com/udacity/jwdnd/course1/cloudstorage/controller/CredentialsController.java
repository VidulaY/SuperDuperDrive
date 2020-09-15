package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {

    private CredentialService credentialService;
    private UserService userService;
    private AuthenticationService authenticationService;
    private EncryptionService encryptionService;
    private NoteService noteService;
    private FileService fileService;

    public CredentialsController(CredentialService credentialService, UserService userService, AuthenticationService authenticationService, EncryptionService encryptionService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @ModelAttribute("credentials")
    public Credentials getCredentialsDto() {
        return new Credentials();
    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionServiceDto() {
        return new EncryptionService();
    }

    @PostMapping("/credentials")
    public String postCredentials(Authentication authentication, @ModelAttribute("credentials") Credentials credentials, Model model){
        this.credentialService.addCredential(credentials, authentication);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }


    @GetMapping("/credentials")
    public String getCredentials(Authentication authentication, Model model){
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredentials(Authentication authentication, @PathVariable("credentialid") Integer credentialid, Model model){
        System.out.println("** Inside delete Credentials **");
        this.credentialService.deleteCredential(credentialid);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @PostMapping("/credentials/edit")
    public String editCredentials(Authentication authentication, @ModelAttribute Model model, Credentials credentials){
        model.addAttribute("credentialsedit", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        this.credentialService.editCredential(credentials);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

}
