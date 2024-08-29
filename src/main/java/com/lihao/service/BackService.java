package com.lihao.service;

import com.lihao.entity.dto.GroupDto;
import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.*;
import com.lihao.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BackService {
    Num num();
    List<PostCoverDto> getHotPost(Page page);
    List<Tag> getHotTag(Page page);
    List<UserInfoDto> getPerson(Page page,Integer status);
    Integer updatePerson(UserInfo userInfo, MultipartFile file) throws GlobalException;
    List<GroupDto> groupList(Page page, Integer status);
    void updateGroup(Group group,MultipartFile file) throws GlobalException;
    Map<String,List<ApiStatistics>> apiList();
}
