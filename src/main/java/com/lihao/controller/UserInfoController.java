package com.lihao.controller;

import com.lihao.annotation.Login;
import com.lihao.annotation.MonitorApiUsage;
import com.lihao.entity.dto.OtherInfoDto;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.UserInfo;
import com.lihao.exception.GlobalException;
import com.lihao.redis.RedisTools;
import com.lihao.service.UserInfoService;
import com.lihao.util.CheckUtil;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserInfoController extends BaseController {
    @Resource
    private RedisTools redisTools;
    @Resource
    private UserInfoService userInfoService;
    @RequestMapping("/info")
    @Login
    @MonitorApiUsage
    public ResponsePack getUserAvatarInfo() throws GlobalException {
        String userId = StringUtil.getUserId();
        UserInfoDto userInfoDto = redisTools.getTokenUserInfoDto(userId);
        //缓存里没有
        userInfoDto= Optional.ofNullable(userInfoDto)
                .orElse(userInfoService.getUserInfo(userId));
        redisTools.setTokenUserInfo(userInfoDto);
        return getSuccessResponsePack(userInfoDto);
    }
    @RequestMapping("/updateInfo")
    @Login
    @MonitorApiUsage
    public ResponsePack updateInfo(String name,
                                   String telephone,
                                   Integer gender,
                                   MultipartFile file) throws GlobalException {
        String userId = StringUtil.getUserId();
        UserInfo updateInfo = new UserInfo();
        updateInfo.setUserId(userId);
        updateInfo.setName(name);
        updateInfo.setTelephone(telephone);
        updateInfo.setGender(gender);
        userInfoService.updateInfo(updateInfo,file);
        return getSuccessResponsePack(null);
    }
    @RequestMapping("/updateTag")
    @Login
    @MonitorApiUsage
    public ResponsePack updateTag(String selfTag) throws GlobalException {
        if(!CheckUtil.checkTag(selfTag)){
            return getSuccessResponsePack(null);
        }
        //统一转小写
        selfTag=selfTag.toLowerCase();
        String userId = StringUtil.getUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setSelfTag(selfTag);
        userInfo.setUserId(userId);
        userInfo = userInfoService.updateTag(userInfo);
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        return getSuccessResponsePack(userInfoDto);
    }
    @RequestMapping("/other/info")
    @Login
    @MonitorApiUsage
    public ResponsePack otherInfo(String otherId) throws GlobalException {
        String userId = StringUtil.getUserId();
        OtherInfoDto otherInfoDto = userInfoService.otherInfo(otherId,userId);
        return getSuccessResponsePack(otherInfoDto);
    }
}
