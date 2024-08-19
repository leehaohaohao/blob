package com.lihao.mapper;

import com.lihao.entity.po.Note;
import com.lihao.entity.query.NoteQuery;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface NoteMapper<T,Q> {
    Integer insert(@Param("bean") T t);
    List<T> select(@Param("query") Q q);
    Integer update(@Param("bean")T t,@Param("query")Q q);
    Note selectById( @Param("query")Q q,@Param("id")String noteId);
}
