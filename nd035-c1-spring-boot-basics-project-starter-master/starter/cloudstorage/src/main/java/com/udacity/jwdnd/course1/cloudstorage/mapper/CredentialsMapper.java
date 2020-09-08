package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialsid")
    int insert(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int delete(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} ")
    int update(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    public Credentials findCredentialById(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, "
            + "username=#{username} "
            + "password=#{password} "
            + "where credentialid=#{credentialid}")
    public void updateCredentialById(Credentials credentials);

}
