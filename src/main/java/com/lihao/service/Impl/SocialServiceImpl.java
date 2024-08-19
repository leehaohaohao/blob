package com.lihao.service.Impl;

import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.constants.RedisConstants;
import com.lihao.entity.dto.OtherInfoDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.*;
import com.lihao.entity.query.CommentQuery;
import com.lihao.entity.query.ConcernQuery;
import com.lihao.entity.query.PostQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.CommentEnum;
import com.lihao.enums.PostEnum;
import com.lihao.enums.RelationshipEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.CommentMapper;
import com.lihao.mapper.ConcernMapper;
import com.lihao.mapper.PostMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.redis.RedisTools;
import com.lihao.service.SocialService;
import com.lihao.service.UserInfoService;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SocialServiceImpl implements SocialService {
    @Resource
    private ConcernMapper<Concern, ConcernQuery> concernMapper;
    @Resource
    private RedisTools redisTools;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private CommentMapper<Comment, CommentQuery> commentMapper;
    @Resource
    private PostMapper<Post, PostQuery> postMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OtherInfoDto concern(String otherId,String userId) throws GlobalException {
        //TODO 取消关注
        //判断是否已经关注过
        Concern concern = concernMapper.select(userId,otherId);
        if(concern != null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        //检验双方账号状态
        UserInfo otherInfo = userInfoMapper.selectByUserId(otherId);
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        if(otherInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())
        || userInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //向关注表里插入数据
        Concern insertConcern = new Concern();
        insertConcern.setConcernId(otherId);
        insertConcern.setUserId(userId);
        insertConcern.setConcernTime(new Date());
        if(!concernMapper.insert(insertConcern).equals(1)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //更新双方粉丝数
        UserInfo updateUserInfo = new UserInfo();
        UserInfo updateOtherInfo = new UserInfo();
        updateUserInfo.setConcern(userInfo.getConcern()+1);
        updateOtherInfo.setFollowers(otherInfo.getFollowers()+1);
        userInfoMapper.updateByUserId(updateUserInfo,userId);
        userInfoMapper.updateByUserId(updateOtherInfo,otherId);
        OtherInfoDto otherInfoDto = userInfoService.otherInfo(otherId,userId);
        //除掉redis缓存
        redisTools.delete(RedisConstants.REDIS_TOKEN_USER_INFO+userId);
        redisTools.delete(RedisConstants.REDIS_TOKEN_USER_INFO+otherId);
        redisTools.deleteKeysWithPatternAndLimit(RedisConstants.REDIS_POST_KEY, NumberConstants.DELETE_NUM);
        return otherInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String,String> comment(Comment comment) throws GlobalException {
        //判断评论人是否正常
        UserInfo userInfo = userInfoMapper.selectByUserId(comment.getUserId());
        if(userInfo == null || userInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //判断评论的帖子是否正常
        Post post = postMapper.selectByPostId(comment.getPostId());
        if(post == null || !post.getPostStatus().equals(PostEnum.NORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //判断父评论是否正常
        if(!Tools.isBlank(comment.getParentId())){
            Comment parentComment = commentMapper.selectByCommentId(comment.getParentId());
            if(parentComment == null || parentComment.getCommentStatus().equals(CommentEnum.DELETE.getStatus())){
                throw new GlobalException(ExceptionConstants.PARENT_COMMENT_DELETED);
            }
            //设置顶级父评论
            if(Tools.isBlank(parentComment.getTopId())){
                comment.setTopId(comment.getParentId());
            }else {
                comment.setTopId(parentComment.getTopId());
            }
        }
        comment.setCommentStatus(CommentEnum.NORMAL.getStatus());
        comment.setCommentDate(new Date());
        if(!commentMapper.insert(comment).equals(1)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        Map<String,String> map = new HashMap<>();
        map.put("commentId",comment.getCommentId());
        map.put("topId",comment.getTopId());
        return map;
    }

    @Override
    public List<OtherInfoDto> getConcernFollower(Page page, String userId,Integer status,String otherId) throws GlobalException {
        ConcernQuery concernQuery = new ConcernQuery();
        concernQuery.setPage(page);
        concernQuery.setOrderBy("concern_time asc");
        //判断是查找关注还是查找粉丝
        if(status.equals(0)){
            concernQuery.setUserId(otherId);
        }else{
            concernQuery.setConcernId(otherId);
        }
        List<Concern> concerns = concernMapper.selectList(concernQuery);
        Set<String> sets = new LinkedHashSet<>();
        if(status.equals(0)){
            for(Concern concern:concerns){
                sets.add(concern.getConcernId());
            }
        }else{
            for(Concern concern:concerns){
                sets.add(concern.getUserId());
            }
        }

        List<OtherInfoDto> otherInfoDtos = new ArrayList<>();
        for(String set:sets){
            //如果查找的是自己的信息返回
            if(set.equals(userId)){
                continue;
            }
            //判断关系
            OtherInfoDto otherInfoDto = userInfoService.otherInfo(set,userId);
            otherInfoDtos.add(otherInfoDto);
        }
        return otherInfoDtos;
    }
}
