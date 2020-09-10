package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class FilesController {

    private FileService fileService;
    private UserService userService;
    private AuthenticationService authenticationService;

    public FilesController(FileService fileService, UserService userService, AuthenticationService authenticationService) {
        this.fileService = fileService;
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @ModelAttribute("files")
    public Files getFilesDto() {
        return new Files();
    }

    @PostMapping("/fileUpload")
    public String postFile(MultipartFile fileUpload, Authentication authentication, @ModelAttribute("files") Files files, Model model){
        try{
            if(!fileUpload.getOriginalFilename().isBlank() && !fileService.duplicateFile(fileUpload.getOriginalFilename(), userService.getUser(authentication.getName()).getUserid())) {
                files.setUserid(userService.getUser(authentication.getName()).getUserid());
                files.setFilename(fileUpload.getOriginalFilename());
                files.setFiledata(fileUpload.getBytes());
                files.setContenttype(fileUpload.getContentType());
                files.setFilesize(fileUpload.getSize());
                this.fileService.addFiles(files, authentication);
                model.addAttribute("fileUploadSuccess","File uploaded successfully!");
            } else {
                model.addAttribute("fileUploadError","File upload error");
            }
            model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }


    @GetMapping("/files")
    public String getNote(Authentication authentication, Model model){
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/files/delete/{fileid}")
    public String deleteFile(Authentication authentication, @PathVariable("fileid") Integer fileid, Model model){
        System.out.println("** Inside delete file **");
        this.fileService.deleteFile(fileid);
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        return "home";
    }

    @GetMapping("/files/view/{fileid}")
    public ResponseEntity viewFile(Authentication authentication, @PathVariable("fileid") Integer fileid, Model model){
        System.out.println("** Inside view file **");
        model.addAttribute("files", this.fileService.getFiles(userService.getUser(authentication.getName()).getUserid()));
        Files file = fileService.getFilesById(fileid);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file.getFiledata());
    }

}

