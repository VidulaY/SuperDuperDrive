package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialsMapper credentialsMapper;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @Autowired
    private EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, UserMapper userMapper, AuthenticationService authenticationService, UserService userService) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    public void addCredential(Credentials credential, Authentication authentication) {
        System.out.println(" Inside Credentials ******");
        Credentials credentials = credentialsMapper.findCredentialById(credential.getCredentialid());
        System.out.println(" Credentials ****** " + credential.getCredentialid());
        if(credentials != null) {
            credentials.setUrl(credential.getUrl());
            credentials.setUsername(credential.getUsername());
            String credentialPassword = credential.getPassword();
            String encryptionKey = getEncryptionKey(credential.getCredentialid());
            System.out.println("  encryptionKey ****** " + encryptionKey);
            System.out.println("  password ****** " + credentialPassword);
            String encryptedPassword = encryptionService.encryptValue(credentialPassword, encryptionKey);
            System.out.println("  encryptedPassword ****** " + encryptedPassword);
            credentials.setPassword(encryptedPassword);
            credentialsMapper.updateCredentialById(credentials);
        } else {
            String key = encryptionService.generateKey().toString();
            System.out.println(" Key ****** " + key);
            credentialsMapper.insert(new Credentials(null, credential.getUrl(), credential.getUsername(), key, encryptionService.encryptValue(credential.getPassword(), key), userMapper.getUser(credential.getUsername()).getUserid()));
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

    public String getDecryptedPassword(Integer credentialid){
        Credentials credentials = credentialsMapper.findCredentialById(credentialid);
        return encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
    }

    public String getEncryptionKey(Integer credentialid){
        Credentials credentials = credentialsMapper.findCredentialById(credentialid);
        return credentials.getKey();
    }

    public List<Credentials> getAllCredentials(){
        return credentialsMapper.getCredentials(userService.getCurrentUser().getUserid());
    }
}
