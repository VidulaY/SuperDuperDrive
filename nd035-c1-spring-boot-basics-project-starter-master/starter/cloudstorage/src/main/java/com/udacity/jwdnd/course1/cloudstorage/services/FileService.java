package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FilesMapper filesMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public FileService(FilesMapper filesMapper, UserMapper userMapper, AuthenticationService authenticationService, UserService userService) {
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public void addFiles(Files file, Authentication authentication){
        Files files = filesMapper.findFileById(file.getFileid());
        if(files != null) {
            System.out.println("Duplicate File ");
        } else {
            filesMapper.insert(new Files(null, file.getFilename(), file.getContenttype(), file.getFilesize(), file.getFiledata(), userMapper.getUser(authentication.getName()).getUserid()));
        }
    }

    public List<Files> getFiles(Integer userid){
        return filesMapper.getFiles(userid);
    }

    public Files getFilesById(Integer fileid){
        return filesMapper.findFileById(fileid);
    }

    public int deleteFile(Integer fileid){
        return filesMapper.delete(fileid);
    }

    public boolean duplicateFile(String filename, Integer userid){
        Files file = filesMapper.findFileByName(filename, userid);
        if(file != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<Files> getAllFiles(){
        return filesMapper.getFiles(userService.getCurrentUser().getUserid());
    }
}



