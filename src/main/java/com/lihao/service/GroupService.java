package com.lihao.service;

import com.lihao.entity.dto.GroupCommentDto;
import com.lihao.entity.po.Group;
import com.lihao.entity.po.GroupComment;
import com.lihao.entity.query.GroupCommentQuery;
import com.lihao.entity.query.GroupQuery;
import com.lihao.exception.GlobalException;

import java.util.List;

public interface GroupService {
    void create(Group group) throws GlobalException;
    Group select(GroupQuery groupQuery);
    List<GroupCommentDto> selectGroupComment(GroupCommentQuery groupCommentQuery,String userId);
    List<Group> selectGroupList(GroupQuery groupQuery);
    Group selectMyGroup(GroupQuery groupQuery);
}
