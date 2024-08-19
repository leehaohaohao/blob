package com.lihao.service;

import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.PostDto;
import com.lihao.entity.po.Page;
import com.lihao.entity.po.Post;
import com.lihao.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ForumService {
    void post(Post post, MultipartFile file) throws GlobalException;
    List<PostCoverDto> getPostByTag (String tagFuzzy, Page page, String userId) throws GlobalException;
    PostDto getPostById(String postId, String userId) throws GlobalException;
    List<PostCoverDto> getRandomPost(Page page, String userId) throws GlobalException;
    void approvalPost(String postId,Integer postStatus,String approvalId) throws GlobalException;
    List<PostCoverDto> getPostByUserId(String userId, String otherId, Page page) throws GlobalException;
    void loveOrCollect(String userId,String postId,Integer status,Integer type) throws GlobalException;
    PostDto getApprovalPostById(String postId, String userId) throws GlobalException;
    List<PostCoverDto> getMyPost(Page page,String userId,String type,String otherId) throws GlobalException;
    List<PostCoverDto> getMyLikeCollectPost(Page page,String otherId,Integer status,String userId) throws GlobalException;
    List<PostCoverDto> getApprovalList(Page page);
}
