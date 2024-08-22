package com.lihao.service;

import com.lihao.entity.dto.CommentDto;
import com.lihao.entity.dto.OtherInfoDto;
import com.lihao.entity.po.Comment;
import com.lihao.entity.po.Page;
import com.lihao.exception.GlobalException;

import java.util.List;
import java.util.Map;

public interface SocialService {
    OtherInfoDto concern(String otherId,String userId) throws GlobalException;
    Map<String,String> comment(Comment comment) throws GlobalException;
    List<OtherInfoDto> getConcernFollower(Page page, String userId,Integer status,String otherId) throws GlobalException;
    List<CommentDto> getComment(String postId) throws GlobalException;
}
