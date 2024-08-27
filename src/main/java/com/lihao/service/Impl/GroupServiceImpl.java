package com.lihao.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.entity.dto.GroupCommentDto;
import com.lihao.entity.po.Group;
import com.lihao.entity.po.GroupComment;
import com.lihao.entity.po.GroupUser;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.GroupCommentQuery;
import com.lihao.entity.query.GroupQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.entity.vo.GroupCommentVo;
import com.lihao.enums.GroupEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.GroupCommentMapper;
import com.lihao.mapper.GroupMapper;
import com.lihao.mapper.GroupUserMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.netty.ChannelContext;
import com.lihao.service.GroupService;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupMapper<Group, GroupQuery> groupMapper;
    @Resource
    private GroupCommentMapper<GroupComment, GroupCommentQuery> groupCommentMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private GroupUserMapper groupUserMapper;
    @Resource
    private ChannelContext context;
    @Override
    public void create(Group group) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByUserId(group.getUserId());
        Optional.ofNullable(userInfo)
                .filter(info->info.getStatus().equals(UserStatusEnum.NORMAL.getStatus()))
                .orElseThrow(()->new GlobalException(ExceptionConstants.SERVER_ERROR));
        //判断用户是否已经创建过群组并且目前正常
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setUserId(userInfo.getUserId());
        groupQuery.setStatus(GroupEnum.NORMAL.getStatus());
        Group judge = groupMapper.select(groupQuery);
        if(judge!=null){
            throw new GlobalException(ExceptionConstants.GROUP_EXIST);
        }
        if(!groupMapper.insert(group).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
    }

    @Override
    public Group select(GroupQuery groupQuery) {
        return groupMapper.select(groupQuery);
    }

    @Override
    public GroupCommentVo selectGroupComment(GroupCommentQuery groupCommentQuery, String userId) throws GlobalException {
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setId(groupCommentQuery.getGroupId());
        Group group = groupMapper.select(groupQuery);
        Optional.ofNullable(group)
                        .orElseThrow(()->new GlobalException(ExceptionConstants.GROUP_NOT_EXIST));
        groupCommentQuery.setOrderBy("time desc");
        List<GroupComment> groupComments = groupCommentMapper.selectByGroupId(groupCommentQuery);
        //status为0说明是自己否则为他人
        List<GroupCommentDto> commentDtos = groupComments.stream()
                .map(comment->{
                    GroupCommentDto commentDto = new GroupCommentDto();
                    BeanUtils.copyProperties(comment,commentDto);
                    UserInfo userInfo = userInfoMapper.selectByUserId(comment.getUserId());
                    if(userInfo.getUserId().equals(userId)){
                        commentDto.setStatus(0);
                    }else{
                        commentDto.setStatus(1);
                    }
                    commentDto.setName(userInfo.getName());
                    commentDto.setAvatar(userInfo.getPhoto());
                    return commentDto;
                })
                .toList();
        GroupCommentVo groupCommentVo = new GroupCommentVo();
        groupCommentVo.setGroupCommentDtos(commentDtos);
        groupCommentVo.setOwnerId(group.getUserId());
        return groupCommentVo;
    }

    @Override
    public List<Group> selectGroupList(GroupQuery groupQuery) {
        List<Group> groups = groupMapper.selectList(groupQuery);
        return groups;
    }

    @Override
    public Group selectMyGroup(GroupQuery groupQuery) {
        return groupMapper.select(groupQuery);
    }

    @Override
    public void add2Group(String userId, String groupId) throws GlobalException {
        GroupUser groupUser = groupUserMapper.select(groupId,userId);
        if(groupUser == null){
            context.addGroupContext(groupId,userId);
        }else{
            throw new GlobalException(ExceptionConstants.GROUP_REJECT_USER);
        }
    }

    @Override
    public void chat(GroupComment groupComment) throws GlobalException, JsonProcessingException {
        //查询是否已经被踢出群组
        GroupUser groupUser = groupUserMapper.select(groupComment.getGroupId(),groupComment.getUserId());
        if(groupUser!=null){
            throw new GlobalException(ExceptionConstants.GROUP_REMOVE_YOU);
        }
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setId(groupComment.getGroupId());
        Group group = groupMapper.select(groupQuery);
        //判断群组状态
        if(group.getStatus().equals(GroupEnum.ABNORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.GROUP_NOT_EXIST);
        }
        UserInfo userInfo = userInfoMapper.selectByUserId(groupComment.getUserId());
        //判断用户状态
        if(userInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        //通过netty广播消息
        GroupCommentDto commentDto = new GroupCommentDto();
        BeanUtils.copyProperties(groupComment,commentDto);
        commentDto.setName(userInfo.getName());
        commentDto.setAvatar(userInfo.getPhoto());
        commentDto.setOwnerId(group.getUserId());
        context.sendMessage(commentDto);
        //向数据库插入聊天消息
        if(!groupCommentMapper.insert(groupComment).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
    }

    @Override
    public void exit() throws GlobalException {
        String userId = StringUtil.getUserId();
        context.removeUserFromSomeGroup(context.userChannelMap.get(userId));
    }

    @Override
    public void removeFromGroup(String groupId, String otherId,String userId) throws GlobalException {
        //如果是移除自己 TODO 暂定无效参数，后续可以判定为解散群组
        if(userId.equals(otherId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setUserId(userId);
        Group group = groupMapper.select(groupQuery);
        UserInfo userInfo = userInfoMapper.selectByUserId(otherId);
        if( group == null
                || !group.getId().equals(groupId)
                ||group.getStatus().equals(GroupEnum.ABNORMAL.getStatus())
                || userInfo == null
                || userInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())
        ){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(otherId);
        groupUserMapper.insert(groupUser);
        context.removeUserFromGroup(groupId,otherId);
    }
}
