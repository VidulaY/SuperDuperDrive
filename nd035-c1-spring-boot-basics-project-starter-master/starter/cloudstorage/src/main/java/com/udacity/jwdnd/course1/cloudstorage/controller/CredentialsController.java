package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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

    public CredentialsController(CredentialService credentialService, UserService userService, AuthenticationService authenticationService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @ModelAttribute("credentials")
    public Credentials getCredentialsDto() {
        return new Credentials();
    }

    @PostMapping("/credentials")
    public String postCredentials(Authentication authentication, @ModelAttribute("credentials") Credentials credentials, Model model){
        this.credentialService.addCredential(credentials, authentication);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }


    @GetMapping("/credentials")
    public String getCredential(Authentication authentication, Model model){
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/credentials/delete/{credentialid}")
    public String deleteCredentials(Authentication authentication, @PathVariable("credentialid") Integer credentialid, Model model){
        System.out.println("** Inside delete credential **");
        this.credentialService.deleteCredential(credentialid);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @PostMapping("/credentials/edit")
    public String editNote(Authentication authentication, @ModelAttribute Model model, Credentials credentials){
        model.addAttribute("credentialsedit", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        this.credentialService.editCredential(credentials);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

}
