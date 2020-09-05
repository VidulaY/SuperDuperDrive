package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int delete(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid} ")
    int update(Notes notes);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    public Notes findNoteById(Integer noteId);

    @Update("UPDATE NOTES SET notetitle=#{notetitle}, "
            + "notedescription=#{notedescription} "
            + "where noteid=#{noteid}")
    public void updateNoteById(Notes note);

}
