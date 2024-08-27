package com.lihao.mapper;

import com.lihao.entity.po.GroupUser;
import org.apache.ibatis.annotations.Param;

public interface GroupUserMapper {
    GroupUser select(@Param("groupId")String groupId, @Param("userId")String userId);
    Integer insert(GroupUser groupUser);
}
