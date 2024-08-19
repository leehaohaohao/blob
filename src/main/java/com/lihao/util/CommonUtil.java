package com.lihao.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.po.PermissionInfo;
import com.lihao.entity.po.RoleInfo;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.PermissionRoleMapper;
import com.lihao.mapper.RoleUserMapper;
import com.lihao.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class CommonUtil {
    @Resource
    private RoleUserMapper roleUserMapper;
    @Resource
    private PermissionRoleMapper permissionRoleMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    public Set<String> getPermission(String userId) throws GlobalException {
        if(userId == null || userId.isEmpty()){
            userId = StringUtil.getUserId();
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if(userInfo == null){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        List<RoleInfo> roleInfos = roleUserMapper.selectByUserId(userId);
        if(roleInfos == null){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
        Set<String> setPermissionId = new HashSet<>();
        for(RoleInfo roleInfo :roleInfos){
            if(roleInfo == null){
                throw new GlobalException(ExceptionConstants.NO_PERMISSION);
            }
            List<PermissionInfo> permissionInfos = permissionRoleMapper.selectByRoleId(roleInfo.getRoleId());
            if(permissionInfos == null){
                continue;
            }
            for(PermissionInfo info :permissionInfos){
                setPermissionId.add(info.getPermissionId());
            }
        }
        return setPermissionId;
    }
    public String getRandomNum(int len){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < len; i++){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    public static long getId(){
        Snowflake snowflake = IdUtil.getSnowflake();
        return snowflake.nextId();
    }
}
