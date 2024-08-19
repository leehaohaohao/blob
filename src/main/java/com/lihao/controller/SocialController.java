package com.lihao.controller;

import com.lihao.annotation.LComment;
import com.lihao.annotation.Login;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.po.Comment;
import com.lihao.entity.po.Page;
import com.lihao.entity.vo.ConcernVo;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.exception.GlobalException;
import com.lihao.service.SocialService;
import com.lihao.util.StringUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social")
@CrossOrigin
public class SocialController extends BaseController{
    @Resource
    private SocialService socialService;
    @RequestMapping("/concern")
    @Login
    public ResponsePack concern(String otherId) throws GlobalException {

        return getSuccessResponsePack(socialService.concern(otherId, StringUtil.getUserId()));
    }
    @RequestMapping("/comment")
    @LComment
    public ResponsePack comment(String postId, String parentId,String commentContent) throws GlobalException {
        String userId = StringUtil.getUserId();
        //判断主要参数是否为空
        if(Tools.isBlank(commentContent,postId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(parentId != null && parentId.equals("null")){
            parentId = null;
        }
        String commentId = StringUtil.getId(UidPrefixEnum.COMMENT.getPrefix());
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setPostId(postId);
        comment.setParentId(parentId);
        comment.setCommentId(commentId);
        comment.setCommentContent(commentContent);
        return getSuccessResponsePack(socialService.comment(comment));
    }
    @RequestMapping("/get/comment")
    @Login
    public ResponsePack getComment(String postId){
        return getSuccessResponsePack(null);
    }
    @PostMapping("/get/concern/follower")
    @Login
    public ResponsePack getConcern(@RequestBody ConcernVo concernVo) throws GlobalException {
        String userId = StringUtil.getUserId();
        Integer status = concernVo.getStatus();
        Page page = concernVo.getPage();
        String otherId = concernVo.getOtherId();
        if(Tools.isBlank(otherId)){
            otherId = userId;
        }
        //TODO status写死了
        if(!status.equals(0) && !status.equals(1)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        if(page == null || page.getPageNum() == null || page.getPageSize() == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        return getSuccessResponsePack(socialService.getConcernFollower(page,userId,status,otherId));
    }
}
