package com.lihao.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMapper<T,Q> {
    T select(@Param("query")Q q);
    Integer insert(@Param("bean")T t);
    Integer update(@Param("bean")T t,@Param("query")Q q);
    List<T> selectList(@Param("query")Q q);
    Integer selectCount(@Param("query")Q q);
}
