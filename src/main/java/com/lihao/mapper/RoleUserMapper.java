package com.lihao.mapper;

import com.lihao.annotation.Post;
import com.lihao.entity.po.RoleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleUserMapper{
    List<RoleInfo> selectByUserId(String userId);

    Integer insert(@Param("userId")String userId,@Param("roleId")String roleId);
}
