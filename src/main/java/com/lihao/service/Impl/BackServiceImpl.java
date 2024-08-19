package com.lihao.service.Impl;

import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.*;
import com.lihao.entity.query.PostQuery;
import com.lihao.entity.query.TagQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.PostEnum;
import com.lihao.enums.PostOrderEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.PostMapper;
import com.lihao.mapper.TagMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.service.BackService;
import com.lihao.service.UserInfoService;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BackServiceImpl implements BackService {
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private PostMapper<Post, PostQuery> postMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private UserInfoService userInfoServiceImpl;
    @Override
    public Num num() {
        Num num = new Num();
        num.setUserNum(userInfoMapper.selectCount(UserStatusEnum.NORMAL.getStatus()));
        num.setUuserNum(userInfoMapper.selectCount(UserStatusEnum.ABNORMAL.getStatus()));
        PostQuery postQuery = new PostQuery();
        postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
        num.setPostNum(postMapper.selectCount(postQuery));
        postQuery.setPostStatus(PostEnum.REVIEWING.getStatus());
        num.setUpostNum(postMapper.selectCount(postQuery));
        num.setTagNum(tagMapper.selectCount());
        return num;
    }

    @Override
    public List<PostCoverDto> getHotPost(Page page) {
        PostQuery postQuery = new PostQuery();
        postQuery.setPage(page);
        postQuery.setOrderBy(PostOrderEnum.SORT_LIKE.getType());
        postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
        return postMapper.selectCoverList(postQuery);
    }

    @Override
    public List<Tag> getHotTag(Page page) {
        TagQuery tagQuery = new TagQuery();
        tagQuery.setPage(new Page(page.getPageSize(),page.getPageNum()));
        tagQuery.setOrderBy("nums desc");
        return tagMapper.selectList(tagQuery);
    }

    @Override
    public List<UserInfoDto> getPerson(Page page,Integer status) {
        UserQuery userQuery = new UserQuery();
        userQuery.setStatus(status);
        userQuery.setPage(new Page(page.getPageSize(), page.getPageNum()));
        userQuery.setOrderBy("last_login_time desc");
        List<UserInfo> userInfos = userInfoMapper.selectList(userQuery);
        List<UserInfoDto> userInfoDtos = userInfos.stream().map(userInfo -> {
            UserInfoDto userInfoDto = new UserInfoDto();
            BeanUtils.copyProperties(userInfo, userInfoDto);
            userInfoDto.setLastPostId(null);
            userInfoDto.setSelfTag(null);
            userInfoDto.setCreateTime(null);
            userInfoDto.setLastOffTime(null);
            userInfoDto.setCollect(null);
            userInfoDto.setFollowers(null);
            userInfoDto.setLove(null);
            userInfoDto.setConcern(null);
            userInfoDto.setPost(null);
            return userInfoDto;
        }).collect(Collectors.toList());
        return userInfoDtos;
    }

    @Override
    @Transactional
    public Integer updatePerson(UserInfo userInfo, MultipartFile file) throws GlobalException {
        userInfoServiceImpl.updateInfo(userInfo,file);
        return null;
    }
}
