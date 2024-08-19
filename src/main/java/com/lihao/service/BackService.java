package com.lihao.service;

import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.Num;
import com.lihao.entity.po.Page;
import com.lihao.entity.po.Tag;
import com.lihao.entity.po.UserInfo;
import com.lihao.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BackService {
    Num num();
    List<PostCoverDto> getHotPost(Page page);
    List<Tag> getHotTag(Page page);
    List<UserInfoDto> getPerson(Page page,Integer status);
    Integer updatePerson(UserInfo userInfo, MultipartFile file) throws GlobalException;
}
