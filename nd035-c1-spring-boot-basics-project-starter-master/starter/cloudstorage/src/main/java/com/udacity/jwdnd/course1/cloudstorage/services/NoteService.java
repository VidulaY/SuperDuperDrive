package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NotesMapper noteMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public NoteService(NotesMapper noteMapper, UserMapper userMapper, AuthenticationService authenticationService) {
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public void addNote(Notes note, Authentication authentication){
        Notes notes = noteMapper.findNoteById(note.getNoteid());
        if(notes != null) {
            notes.setNotetitle(note.getNotetitle());
            notes.setNotedescription(note.getNotedescription());
            noteMapper.updateNoteById(notes);
        } else {
            noteMapper.insert(new Notes(null, note.getNotetitle(), note.getNotedescription(), userMapper.getUser(authentication.getName()).getUserid()));
        }
    }

    public List<Notes> getNotes(Integer userid){
        return noteMapper.getNotes(userid);
    }

    public int deleteNote(Integer noteid){
        return noteMapper.delete(noteid);
    }

    public int editNote(Notes note){
        return noteMapper.update(note);
    }

}
