package com.lihao.service.Impl;

import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.entity.dto.GroupDto;
import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.*;
import com.lihao.entity.query.GroupQuery;
import com.lihao.entity.query.PostQuery;
import com.lihao.entity.query.TagQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.GroupEnum;
import com.lihao.enums.PostEnum;
import com.lihao.enums.PostOrderEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.*;
import com.lihao.service.BackService;
import com.lihao.service.UserInfoService;

import com.lihao.util.FileUtil;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
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
    @Resource
    private GroupMapper<Group, GroupQuery> groupMapper;
    @Resource
    private ApiStatisticsMapper apiStatisticsMapper;
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
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setStatus(GroupEnum.NORMAL.getStatus());
        num.setGroupNum(groupMapper.selectCount(groupQuery));
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

    @Override
    public List<GroupDto> groupList(Page page, Integer status) {
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setStatus(status);
        groupQuery.setOrderBy("time desc");
        groupQuery.setPage(page);
        List<Group> groups = groupMapper.selectList(groupQuery);
        List<GroupDto> groupDtos = groups.stream().map(group -> {
            GroupDto groupDto = new GroupDto();
            BeanUtils.copyProperties(group, groupDto);
            UserInfo userInfo = userInfoMapper.selectByUserId(group.getUserId());
            groupDto.setUserName(userInfo.getName());
            return groupDto;
        }).collect(Collectors.toList());
        return groupDtos;
    }

    @Override
    @Transactional
    public void updateGroup(Group group, MultipartFile file) throws GlobalException {
        Optional.ofNullable(file).filter(f->!f.getOriginalFilename().isEmpty()).ifPresent(f -> {
            String[]ss = FileUtil.fileBookLoad(f, StringUtil.getGroupAvatarPath());
            group.setAvatar(ss[0]);
        });
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setId(group.getId());
        if(!groupMapper.update(group,groupQuery).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
    }

    @Override
    public Map<String,List<ApiStatistics>> apiList() {
        List<ApiStatistics> apiStatistics = apiStatisticsMapper.selectList();
        HashMap<String,List<ApiStatistics>> map = new HashMap<>();
        for(ApiStatistics api:apiStatistics){
            String apiName = api.getName();
            String className = apiName.split("\\.")[0];
            map.computeIfAbsent(className,(k->new ArrayList<>())).add(api);
        }
        return map;
    }
}
