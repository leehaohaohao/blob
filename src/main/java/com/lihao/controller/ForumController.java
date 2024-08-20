package com.lihao.controller;

import com.github.pagehelper.PageInfo;
import com.lihao.annotation.LApprovalPost;
import com.lihao.annotation.LPost;
import com.lihao.annotation.Login;
import com.lihao.annotation.Manager;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.RedisConstants;
import com.lihao.constants.StringConstants;
import com.lihao.constants.TimeConstants;
import com.lihao.entity.dto.PostCoverDto;
import com.lihao.entity.dto.PostDto;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.Page;
import com.lihao.entity.po.Post;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.JudgeEnum;
import com.lihao.enums.PostOrderEnum;
import com.lihao.enums.TypeEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.TagMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.redis.RedisTools;
import com.lihao.service.ForumService;
import com.lihao.util.CheckUtil;
import com.lihao.util.StringUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/forum")
@CrossOrigin
public class ForumController extends BaseController{
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ForumService forumService;
    @Resource
    private RedisTools redisTools;

    /**
     * 发布文章
     * @param post_content 文章内容
     * @param tags 标签
     * @param title 标题
     * @param file 封面
     * @return
     * @throws GlobalException
     */
    @PostMapping("/post")
    @LPost
    public ResponsePack post(String post_content,
                             String tags, String title, MultipartFile file) throws GlobalException {
        String userId = StringUtil.getUserId();
        //标题如果为空则采用默认标题
        if(Tools.isBlank(title)){
            title = StringConstants.DEFAULT_TITLE;
        }
        //判断内容是否为空
        if(Tools.isBlank(post_content)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        //判断标签是否为空
        if(!CheckUtil.checkTag(tags)){
            throw new GlobalException(ExceptionConstants.INVALID_TAG_FORMAT);
        }
        tags=tags.toLowerCase();
        Post post = new Post();
        post.setPostContent(post_content);
        post.setTag(tags);
        post.setTitle(title);
        post.setUserId(userId);
        post.setPostLike(0);
        post.setCollect(0);
        forumService.post(post,file);
        //更新用户发布帖子数并更新redis缓存
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        userInfo.setPost(userInfo.getPost()+1);
        userInfoMapper.updateByUserId(userInfo,userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        redisTools.setTokenUserInfo(userInfoDto);
        return getSuccessResponsePack(userInfoDto);
    }
    //动态获取标签
    @GetMapping("/get/tag")
    public ResponsePack getTag(){
        return getSuccessResponsePack(tagMapper.select());
    }

    /**
     * 获取标签下的文章
     * @param tagFuzzy 模糊标签
     * @param pageNum 页码
     * @param pageSize 页数
     * @return
     * @throws GlobalException
     */
    @PostMapping("/tag/post")
    @Login
    public ResponsePack getPostByTag(String tagFuzzy,int pageNum,int pageSize) throws GlobalException {
        Page page = new Page(pageSize,pageNum);
        List<PostCoverDto> postCoverDtoList = new ArrayList<>();
        postCoverDtoList = redisTools.getList(RedisConstants.REDIS_POST_KEY+tagFuzzy+":"+page.getPageNum());
        if(!postCoverDtoList.isEmpty()){
            return getSuccessResponsePack(new PageInfo<>(postCoverDtoList));
        }
        if(tagFuzzy.equals(StringConstants.RANDOM_POST)){
            postCoverDtoList = redisTools.getList(RedisConstants.REDIS_POST_KEY+StringConstants.RANDOM_POST+":"+StringUtil.getUserId()+":"+page.getPageNum());
            if(!postCoverDtoList.isEmpty()){
                return getSuccessResponsePack(new PageInfo<>(postCoverDtoList));
            }
            //走推荐算法
            return getSuccessResponsePack(new PageInfo<>(forumService.getRandomPost(page,StringUtil.getUserId())));
        }else{
            //走标签
            return getSuccessResponsePack(new PageInfo<>(forumService.getPostByTag(tagFuzzy,page,StringUtil.getUserId())));
        }
    }

    /**
     * 根据id获取文章
     * @param postId 文章id
     * @return
     * @throws GlobalException
     */
    @PostMapping("/id/post")
    @Login
    public ResponsePack getPostById(String postId) throws GlobalException {
        String userId = StringUtil.getUserId();
        if(Tools.isBlank(postId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        PostDto postDto = redisTools.getPostDto(postId);
        postDto = Optional.ofNullable(postDto).orElse(forumService.getPostById(postId,userId));
        return getSuccessResponsePack(postDto);
    }

    /**
     * 根据id审批帖子
     * @param postId 文章id
     * @param postStatus 文章状态
     * @return
     * @throws GlobalException
     */
    @PostMapping("/approval/post")
    @LApprovalPost
    public ResponsePack approvalPost(String postId,Integer postStatus) throws GlobalException {
        String approvalId = StringUtil.getUserId();
        forumService.approvalPost(postId,postStatus,approvalId);
        return getSuccessResponsePack(StringConstants.SUCCESS_OPERATE);
    }

    /**
     * 获取用户发布的帖子
     * @param otherId 他人id
     * @param pageSize 页码
     * @param pageNum 页数
     * @return
     * @throws GlobalException
     */
    @PostMapping("/user/post")
    @Login
    public ResponsePack getPostByUser(String otherId,Integer pageSize,Integer pageNum) throws GlobalException {
        String userId = StringUtil.getUserId();
        Page page = new Page(pageSize,pageNum);
        List<PostCoverDto> postCoverDtos = redisTools.getList(RedisConstants.REDIS_POST_KEY+"USER:"+pageNum+":"+otherId);
        if(postCoverDtos.isEmpty()){
            return getSuccessResponsePack(forumService.getPostByUserId(userId,otherId,page));
        }else {
            return getSuccessResponsePack(postCoverDtos);
        }
    }

    /**
     * 点赞或者收藏
     * @param postId 帖子id
     * @param status 帖子状态
     * @param type 类型
     * @return
     * @throws GlobalException
     */
    @PostMapping("/love/collect")
    @Login
    public ResponsePack likeOrCollect(String postId,Integer status,Integer type) throws GlobalException {
        if(JudgeEnum.getJudgeEnumByStatus(status) == null || Tools.isBlank(postId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        String userId = StringUtil.getUserId();
        forumService.loveOrCollect(userId,postId,status,type);
        return getSuccessResponsePack(null);
    }

    /**
     * 根据id获取审批的帖子内容
     * @param postId
     * @return
     * @throws GlobalException
     */
    @PostMapping("/id/approval/post")
    @Login
    public ResponsePack getApprovalPostById(String postId) throws GlobalException {
        if(Tools.isBlank(postId)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        String userId = StringUtil.getUserId();
        return getSuccessResponsePack(forumService.getApprovalPostById(postId,userId));
    }

    /**
     * 查询我发布的文章
     * @param pageNum
     * @param pageSize
     * @param sort
     * @param otherId
     * @return
     * @throws GlobalException
     */
    @PostMapping("/user/unapproval/post")
    @Login
    public ResponsePack getMyPost(Integer pageNum,Integer pageSize,Integer sort,String otherId) throws GlobalException {
        Optional.ofNullable(pageNum)
                .orElseThrow(() -> new GlobalException(ExceptionConstants.INVALID_PARAM));

        Optional.ofNullable(pageSize)
                .orElseThrow(() -> new GlobalException(ExceptionConstants.INVALID_PARAM));
        String userId = StringUtil.getUserId();
        if(Tools.isBlank(otherId)){
            otherId = userId;
        }
        Page page = new Page(pageSize,pageNum);
        String type = Optional.ofNullable(sort)
                .map(PostOrderEnum::getPostOrderEnumByStatus)
                .map(PostOrderEnum::getType)
                .orElse(PostOrderEnum.SORT_TIME.getType());

        List<PostCoverDto> postCoverDtos = redisTools.getList(RedisConstants.REDIS_POST_KEY+"USER:"+pageNum+":"+otherId);
        if(postCoverDtos.isEmpty()){
            return getSuccessResponsePack(forumService.getMyPost(page,userId,type,otherId));
        }else {
            return getSuccessResponsePack(postCoverDtos);
        }
    }

    /**
     * 用户喜欢，收藏
     * @param pageNum 页码
     * @param pageSize 一页数量
     * @param status 喜欢或收藏
     * @param otherId 其他用户id
     * @return
     * @throws GlobalException
     */
    @PostMapping("/my/like/collect/post")
    @Login
    public ResponsePack getMyLikePost(Integer pageNum,Integer pageSize,Integer status,String otherId) throws GlobalException {
        if(pageNum == null || pageSize == null || TypeEnum.getTypeEnumByStatus(status) == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        String userId = StringUtil.getUserId();
        if(otherId == null){
            otherId = userId;
        }
        Page page = new Page(pageSize,pageNum);
        List<PostCoverDto> postCoverDtos = null;
        if(status.equals(TypeEnum.LOVE.getStatus())){
            postCoverDtos = redisTools.getList(RedisConstants.REDIS_POST_KEY+"LIKE:"+pageNum+":"+userId);
        }else{
            postCoverDtos =  redisTools.getList(RedisConstants.REDIS_POST_KEY+"COLLECT:"+pageNum+":"+userId);
        }
        if(postCoverDtos.isEmpty()){
            return getSuccessResponsePack(forumService.getMyLikeCollectPost(page,otherId,status,userId));
        }else {
            return getSuccessResponsePack(postCoverDtos);
        }
    }
    @PostMapping("/approval/list")
    @Login
    @Manager
    public ResponsePack getApprovalList(Page page) throws GlobalException {
        CheckUtil.checkPage(page);
        return getSuccessResponsePack(forumService.getApprovalList(page));
    }
}
