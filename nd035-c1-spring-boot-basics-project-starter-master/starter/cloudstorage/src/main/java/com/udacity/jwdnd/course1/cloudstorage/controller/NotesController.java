package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class NotesController {

    private NoteService noteService;
    private UserService userService;
    private AuthenticationService authenticationService;
    private CredentialService credentialService;
    private FileService fileService;
    private EncryptionService encryptionService;

    public NotesController(NoteService noteService, UserService userService, AuthenticationService authenticationService, CredentialService credentialService, FileService fileService, EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.encryptionService = encryptionService;
    }

    @ModelAttribute("notes")
    public Notes getNotesDto() {
        return new Notes();
    }

    @PostMapping("/notes")
    public String postNote(Authentication authentication, @ModelAttribute("notes") Notes notes, Model model){
        this.noteService.addNote(notes, authentication);
        model.addAttribute("notesAddSuccess", "Note added successfully!");
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "result";
    }


    @GetMapping("/notes")
    public String getNote(Authentication authentication, Model model){
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNote(Authentication authentication, @PathVariable("noteid") Integer noteid, Model model){
        System.out.println("** Inside delete note **");
        this.noteService.deleteNote(noteid);
        model.addAttribute("notesDeleteSuccess", "Note deleted successfully!");
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "result";
    }

    @PostMapping("/notes/edit")
    public String editNote(Authentication authentication, @ModelAttribute Model model, Notes note){
        model.addAttribute("notesedit", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        this.noteService.editNote(note);
        model.addAttribute("noteUpdateSuccess", "Note updated successfully!");
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentials", this.credentialService.getCredentials(userService.getUser(authentication.getName()).getUserid()));
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "result";
    }

}
