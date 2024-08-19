package com.lihao.mapper;

import com.lihao.entity.po.Concern;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConcernMapper<T,Q> {
    Concern select(@Param("userId") String userId,
                   @Param("concernId") String concernId);
    Integer insert(@Param("bean")T t);
    List<Concern> selectList(@Param("query")Q q);

}
