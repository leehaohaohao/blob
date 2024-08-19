package com.lihao.mapper;

import com.lihao.entity.po.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper<T,P> extends BaseMapper<T,P>{
    /**
     * 根据id更新
     */
    Integer updateByUserId(@Param("bean") T t,@Param("userId") String id);
    /**
     * 根据id删除
     */
    Integer deleteByUserId(String id);
    /**
     * 根据id获取对象
     */
    T selectByUserId(String id);
    /**
     * 根据Email更新
     */
    Integer updateByEmail(@Param("bean") T t,@Param("email") String email);
    /**
     * 根据Email删除
     */
    Integer deleteByEmail(String email);
    /**
     * 根据Email获取对象
     */
    T selectByEmail(String email);
    Integer selectCount(Integer status);
    List<T> selectList(@Param("query")P p);
}
