package com.lihao.mapper;

import com.lihao.entity.po.LoveCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoveCollectMapper<T,Q> {
    LoveCollect select(@Param("query") T t);
    Integer insertOrUpdate(@Param("bean") T t);
    List<LoveCollect> selectList(@Param("query")Q q);
}
