package com.lihao.mapper;

import com.lihao.entity.po.GroupComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupCommentMapper<T,Q> {
    List<T> selectByGroupId(@Param("query")Q q);
    Integer insert(@Param("bean")T t);
}
