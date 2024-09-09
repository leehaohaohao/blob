package com.lihao.service;

import com.lihao.entity.dto.OtherInfoDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.UserInfo;
import com.lihao.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {
    UserInfoDto getUserInfo(String userId);
    void updateInfo(UserInfo userInfo, MultipartFile file) throws GlobalException;
    UserInfoDto updateTag(UserInfo userInfo);
    OtherInfoDto otherInfo(String otherId, String userId) throws GlobalException;
}
