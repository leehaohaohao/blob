package com.lihao.mapper;

import java.util.List;
import com.lihao.entity.po.PermissionInfo;

public interface PermissionRoleMapper {
    List<PermissionInfo> selectByRoleId(String roleId);
}
