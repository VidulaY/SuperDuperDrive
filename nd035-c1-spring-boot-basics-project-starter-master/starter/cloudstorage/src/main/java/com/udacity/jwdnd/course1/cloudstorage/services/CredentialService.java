package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public CredentialService(CredentialsMapper credentialsMapper, UserMapper userMapper, AuthenticationService authenticationService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
    }

    public void addCredential(Credentials credential, Authentication authentication){
        System.out.println(" Inside Credentials ******");
        Credentials credentials = credentialsMapper.findCredentialById(credential.getCredentialsid());
        System.out.println(" Credentials ****** " + credential.getCredentialsid());
        if(credentials != null) {
            credentials.setUrl(credential.getUrl());
            credentials.setUsername(credential.getUsername());
            credentials.setPassword(credential.getPassword());
            credentialsMapper.updateCredentialById(credentials);
        } else {
            credentialsMapper.insert(new Credentials(null, credential.getUrl(), credential.getUsername(), new String("key1234"), credential.getPassword(), userMapper.getUser(credential.getUsername()).getUserid()));
        }
    }

    public List<Credentials> getCredentials(Integer userid){
        return credentialsMapper.getCredentials(userid);
    }

    public int deleteCredential(Integer credentialid){
        return credentialsMapper.delete(credentialid);
    }

    public int editCredential(Credentials credential){
        return credentialsMapper.update(credential);
    }
}
