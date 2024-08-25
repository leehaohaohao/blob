package com.lihao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lihao.annotation.Login;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.StringConstants;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.po.Group;
import com.lihao.entity.po.GroupComment;
import com.lihao.entity.po.Page;
import com.lihao.entity.query.GroupCommentQuery;
import com.lihao.entity.query.GroupQuery;
import com.lihao.enums.GroupEnum;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.exception.GlobalException;
import com.lihao.netty.ChannelContext;
import com.lihao.service.GroupService;
import com.lihao.util.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 群组聊天
 */
@RestController
@RequestMapping("/group")
@CrossOrigin
public class GroupController extends BaseController {
    @Resource
    private GroupService groupServiceImpl;
    @Resource
    private ChannelContext context;
    /**
     * 创建群组
     * @param file
     * @param name
     * @return
     * @throws GlobalException
     */
    @PostMapping("/create")
    @Login
    public ResponsePack create(MultipartFile file,String name) throws GlobalException {
        Group group = new Group();
        if(file == null){
            group.setAvatar(StringConstants.DEFAULT_GROUP_AVATAR);
        }else{
            String[] ss = FileUtil.fileBookLoad(file, StringUtil.getGroupAvatarPath());
            group.setAvatar(ss[0]);
        }
        group.setId(StringUtil.getId(UidPrefixEnum.GROUP.getPrefix()));
        group.setName(name);
        group.setUserId(StringUtil.getUserId());
        group.setTime(new Date());
        groupServiceImpl.create(group);
        return getSuccessResponsePack(null);
    }

    /**
     * 根据id查询群组
     * @param groupId
     * @return
     * @throws GlobalException
     */
    @PostMapping("/select/group")
    @Login
    public ResponsePack selectGroup(String groupId) throws GlobalException {
        if(Tools.isBlank(groupId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setId(groupId);
        groupQuery.setStatus(GroupEnum.NORMAL.getStatus());
        return getSuccessResponsePack(groupServiceImpl.select(groupQuery));
    }

    /**
     * 查询群组历史聊天
     * @param groupId 群组id
     * @param pageSize 一页数量
     * @param pageNum 页码
     * @return
     * @throws GlobalException
     */
    @PostMapping("/select/comment")
    @Login
    public ResponsePack selectGroupComment(String groupId,Integer pageSize,Integer pageNum) throws GlobalException {
        if(Tools.isBlank(groupId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        GroupCommentQuery groupCommentQuery = new GroupCommentQuery();
        Page page = new Page(pageSize,pageNum);
        groupCommentQuery.setGroupId(groupId);
        groupCommentQuery.setPage(page);
        return getSuccessResponsePack(groupServiceImpl.selectGroupComment(groupCommentQuery,StringUtil.getUserId()));
    }

    /**
     * 查询群组列表
     * @param pageSize 一页数量
     * @param pageNum 页码
     * @return
     * @throws GlobalException
     */
    @PostMapping("/select/list")
    @Login
    public ResponsePack selectGroupList(Integer pageSize,Integer pageNum) throws GlobalException {
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setOrderBy("time asc");
        groupQuery.setPage(new Page(pageSize,pageNum));
        groupQuery.setStatus(GroupEnum.NORMAL.getStatus());
        return getSuccessResponsePack(groupServiceImpl.selectGroupList(groupQuery));
    }

    /**
     * 查询我的群组
     * @return
     * @throws GlobalException
     */
    @RequestMapping("/select/my")
    @Login
    public ResponsePack selectMyGroup() throws GlobalException {
        GroupQuery groupQuery = new GroupQuery();
        groupQuery.setUserId(StringUtil.getUserId());
        groupQuery.setStatus(GroupEnum.NORMAL.getStatus());
        return getSuccessResponsePack(groupServiceImpl.selectMyGroup(groupQuery));
    }

    /**
     * 加入群组
     * @param groupId
     * @return
     * @throws GlobalException
     */
    @PostMapping("/add")
    @Login
    public ResponsePack add2Group(String groupId) throws GlobalException {
        context.addGroupContext(groupId,StringUtil.getUserId());
        return getSuccessResponsePack(null);
    }

    /**
     * 移除群组中某位在线的用户
     * @param otherId
     * @return
     * @throws GlobalException
     */
    @PostMapping("/remove")
    @Login
    public ResponsePack removeFromGroup(String otherId) throws GlobalException {
        context.removeUserFromGroup(StringUtil.getUserId(),otherId);
        return getSuccessResponsePack(null);
    }

    /**
     * 删除群组
     * @param groupId 群组id
     * @return
     * @throws GlobalException
     */
    @PostMapping("/delete")
    @Login
    public ResponsePack deleteGroup(String groupId) throws GlobalException {
        return getSuccessResponsePack(null);
    }

    /**
     * 群组发消息聊天
     * @param groupId 群id
     * @param content 消息内容
     * @return
     * @throws GlobalException
     */
    @PostMapping("/chat")
    @Login
    public ResponsePack chat(String groupId,String content) throws GlobalException, JsonProcessingException {
        GroupComment groupComment = new GroupComment();
        groupComment.setId(StringUtil.getId(UidPrefixEnum.GROUP_COMMENT.getPrefix()));
        groupComment.setTime(new Date());
        groupComment.setContent(content);
        groupComment.setGroupId(groupId);
        groupComment.setUserId(StringUtil.getUserId());
        groupServiceImpl.chat(groupComment);
        return getSuccessResponsePack(null);
    }
}
