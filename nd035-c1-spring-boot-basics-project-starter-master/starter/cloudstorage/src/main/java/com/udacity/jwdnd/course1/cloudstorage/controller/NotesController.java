package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class NotesController {

    private NoteService noteService;
    private UserService userService;
    private AuthenticationService authenticationService;

    public NotesController(NoteService noteService, UserService userService, AuthenticationService authenticationService) {
        this.noteService = noteService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @ModelAttribute("notes")
    public Notes getNotesDto() {
        return new Notes();
    }

    @PostMapping("/notes")
    public String postNote(Authentication authentication, @ModelAttribute("notes") Notes notes, Model model){
        this.noteService.addNote(notes, authentication);
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }


    @GetMapping("/notes")
    public String getNote(Authentication authentication, Model model){
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/notes/delete/{noteid}")
    public String deleteNote(Authentication authentication, @PathVariable("noteid") Integer noteid, Model model){
        System.out.println("** Inside delete note **");
        this.noteService.deleteNote(noteid);
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @PostMapping("/notes/edit")
    public String editNote(Authentication authentication, @ModelAttribute Model model, Notes note){
        model.addAttribute("notesedit", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        this.noteService.editNote(note);
        model.addAttribute("notes", this.noteService.getNotes(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

}
