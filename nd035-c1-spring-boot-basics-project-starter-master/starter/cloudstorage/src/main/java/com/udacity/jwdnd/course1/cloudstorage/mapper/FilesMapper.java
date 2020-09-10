package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> getFiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(Files files);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    int delete(Integer fileid);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    public Files findFileById(Integer fileid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    public Files findFileByName(String filename, Integer userid);

    @Update("UPDATE FILES SET filename=#{filename}, "
            + "contenttype=#{contenttype}, "
            + "filesize=#{filesize}, "
            + "userid=#{userid}, "
            + "filedata=#{filedata}, "
            + "where noteid=#{noteid}")
    public void updateFileById(Files files);

}


