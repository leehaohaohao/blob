package com.lihao.mapper;

import org.apache.ibatis.annotations.Param;

public interface PermissionMapper<T,P> extends BaseMapper<T,P> {
    /**
     * 根据Id更新
     */
    Integer updateById(@Param("bean") T t, @Param("id") String id);


    /**
     * 根据Id删除
     */
    Integer deleteById(@Param("id") String id);


    /**
     * 根据Id获取对象
     */
    T selectById(@Param("id") String id);
}
