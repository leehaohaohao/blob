package com.lihao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lihao.entity.dto.GroupCommentDto;
import com.lihao.entity.po.Group;
import com.lihao.entity.po.GroupComment;
import com.lihao.entity.query.GroupCommentQuery;
import com.lihao.entity.query.GroupQuery;
import com.lihao.entity.vo.GroupCommentVo;
import com.lihao.exception.GlobalException;

import java.util.List;

public interface GroupService {
    void create(Group group) throws GlobalException;
    Group select(GroupQuery groupQuery);
    GroupCommentVo selectGroupComment(GroupCommentQuery groupCommentQuery, String userId) throws GlobalException;
    List<Group> selectGroupList(GroupQuery groupQuery);
    Group selectMyGroup(GroupQuery groupQuery);
    void add2Group(String userId,String groupId);
    void chat(GroupComment groupComment) throws GlobalException, JsonProcessingException;
}
