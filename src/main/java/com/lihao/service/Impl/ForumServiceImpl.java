package com.lihao.service.Impl;

import com.github.pagehelper.PageHelper;
import com.lihao.constants.*;
import com.lihao.entity.dto.*;
import com.lihao.entity.po.*;
import com.lihao.entity.query.CommentQuery;
import com.lihao.entity.query.LoveCollectQuery;
import com.lihao.entity.query.PostQuery;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.*;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.*;
import com.lihao.redis.RedisTools;
import com.lihao.service.ForumService;
import com.lihao.service.UserInfoService;
import com.lihao.util.FileUtil;
import com.lihao.util.StringUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumServiceImpl implements ForumService {
    @Resource
    private PostMapper<Post, PostQuery> postMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private CommentMapper<Comment, CommentQuery> commentMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LoveCollectMapper<LoveCollect, LoveCollectQuery> loveCollectMapper;
    @Resource
    private RedisTools redisTools;
    @Resource
    private TagMapper tagMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Post post, MultipartFile file) throws GlobalException {
        Date curDate = new Date();
        post.setPostTime(curDate);
        post.setPostStatus(PostEnum.REVIEWING.getStatus());
        post.setPostId(StringUtil.getId(UidPrefixEnum.POST.getPrefix()));
        //储存封面
        String[] ss = null;
        if(file==null || file.getOriginalFilename().isEmpty()){
            post.setCover(StringConstants.DEFAULT_POST_COVER);
        }else{
            ss = FileUtil.fileBookLoad(file,StringConstants.POST_COVER_IMAGE_PATH);
            if(ss.length!=NumberConstants.FILE_ARRAY_LENGTH){
                throw new GlobalException(ExceptionConstants.SERVER_ERROR);
            }
            post.setCover(ss[0]);
        }
        //插入帖子
        if(postMapper.insert(post)!=1){
            if(ss!=null && ss[1]!=null){
                FileUtil.removeFile(ss[1]);
            }
            throw new GlobalException(ExceptionConstants.PUBLISH_FAIL);
        }
    }
    @Override
    public List<PostCoverDto> getPostByTag(String tagFuzzy, Page page, String userId) throws GlobalException {
        //降序 模糊匹配相关标签
        PostQuery postQuery = new PostQuery();
        postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
        postQuery.setOrderBy("post_time asc");
        if(tagFuzzy!=null){
            postQuery.setTagFuzzy(tagFuzzy);
        }
        postQuery.setPage(page);
        //除去无用信息
        List<Post> posts = postMapper.selectList(postQuery);
        List<PostCoverDto> postCoverDtos = new ArrayList<>();
        //TODO 用stream流并行处理提高速度
        for(Post post:posts){
            //将用户与帖子发布人关联存入
            PostCoverDto postCoverDto = new PostCoverDto();
            BeanUtils.copyProperties(post, postCoverDto);
            postCoverDto.setOtherInfoDto(userInfoService.otherInfo(post.getUserId(),userId));
            postCoverDtos.add(postCoverDto);
        }
        //TODO 缓存策略优化
        redisTools.setLeftList(RedisConstants.REDIS_POST_KEY+tagFuzzy+":"+page.getPageNum(), postCoverDtos,TimeConstants.ONE_DAY);
        return postCoverDtos;
    }
    @Override
    public PostDto getPostById(String postId,String userId) throws GlobalException {
        Post post = postMapper.selectByPostId(postId);
        if(post == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        //判断当前id的post的状态
        if(PostEnum.NORMAL != PostEnum.getPostEnum(post.getPostStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post,postDto);
        UserInfo userInfo = userInfoMapper.selectByUserId(postDto.getUserId());
        if(!userInfo.getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //添加帖子发布人基本信息
        postDto.setName(userInfo.getName());
        postDto.setPhoto(userInfo.getPhoto());
        //判断帖子发布人与查看人的关系
        OtherInfoDto otherInfoDto = userInfoService.otherInfo(post.getUserId(),userId);
        postDto.setStatus(otherInfoDto.getStatus());
        //查询帖子相关评论信息
        List<CommentDto> parentCommentDtos = getCommentDto(postId);
        postDto.setParentCommentDto(parentCommentDtos);
        LoveCollect loveCollectQuery = new LoveCollect();
        loveCollectQuery.setUserId(userId);
        loveCollectQuery.setPostId(postId);
        //添加点赞和收藏信息
        LoveCollect loveCollect = loveCollectMapper.select(loveCollectQuery);
        if(loveCollect == null){
            postDto.setIsLove(false);
            postDto.setIsCollect(false);
        }else{
            postDto.setIsLove(loveCollect.getLove());
            postDto.setIsCollect(loveCollect.getCollect());
        }
        //存入缓存
        redisTools.setPostDto(postDto);
        return postDto;
    }

    @Override
    public List<PostCoverDto> getRandomPost(Page page, String userId) throws GlobalException {
        //TODO 推荐算法仍需优化
        List<PostCoverDto> postCoverDtos = new ArrayList<>();
        List<Post> posts = postMapper.selectRandom(PostEnum.NORMAL.getStatus(),page.getPageSize());
        for(Post post:posts){
            PostCoverDto postCoverDto = new PostCoverDto();
            BeanUtils.copyProperties(post, postCoverDto);
            postCoverDto.setOtherInfoDto(userInfoService.otherInfo(post.getUserId(),userId));
            postCoverDtos.add(postCoverDto);
        }
        //TODO 缓存策略优化
        redisTools.setLeftList(RedisConstants.REDIS_POST_KEY+StringConstants.RANDOM_POST+":"+userId+":"+page.getPageNum(), postCoverDtos,TimeConstants.ONE_DAY);
        return postCoverDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approvalPost(String postId,Integer postStatus,String approvalId) throws GlobalException {
        if(PostEnum.getPostEnum(postStatus)==null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        Post post = postMapper.selectByPostId(postId);
        //判断是否已经审核
        if(postStatus.equals(post.getPostStatus())){
            throw new GlobalException(ManagerExceptionConstants.REVIEWED);
        }
        //检验用户状态
        UserInfo userInfo = userInfoMapper.selectByUserId(post.getUserId());
        if(!UserStatusEnum.NORMAL.equals(UserStatusEnum.getUserStatusEnum(userInfo.getStatus()))){
            throw new GlobalException(ManagerExceptionConstants.INVALID_USER_ID);
        }
        if(postStatus.equals(PostEnum.NORMAL.getStatus())){
            //将标签添加到标签数据库中
            String[] tags = post.getTag().split("\\|");
            for(String tag :tags){
                tagMapper.insertOrUpdate(tag);
            }
        }
        //进行部分数据更新
        Post updatePost = new Post();
        updatePost.setApprovalTime(new Date());
        updatePost.setPostStatus(postStatus);
        postMapper.updateByPostId(updatePost,postId);
    }

    @Override
    public List<PostCoverDto> getPostByUserId(String userId, String otherId, Page page) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        UserInfo otherInfo = null;
        List<PostCoverDto> postCoverDtos = new ArrayList<>();
        PostQuery postQuery = new PostQuery();
        postQuery.setOrderBy("post_time desc");
        postQuery.setUserId(otherId);
        postQuery.setPage(page);
        postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
        //如果是查看自己的发布文章
        if(userId == otherId){
            if(userInfo.getStatus().equals(UserStatusEnum.ABNORMAL)){
                throw new GlobalException(ExceptionConstants.INVALID_PARAM);
            }
            List<Post> posts = postMapper.selectMainList(postQuery);
            //获取用户一些信息
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfo.setEmail(null);
            userInfo.setTelephone(null);
            userInfo.setCreateTime(null);
            userInfo.setLastOffTime(null);
            userInfo.setLastLoginTime(null);
            BeanUtils.copyProperties(userInfo,userInfoDto);
            OtherInfoDto otherInfoDto = new OtherInfoDto();
            otherInfoDto.setStatus(RelationshipEnum.MINE.getStatus());
            otherInfoDto.setUserInfoDto(userInfoDto);
            for(Post post:posts){
                PostCoverDto postCoverDto = new PostCoverDto();
                BeanUtils.copyProperties(post, postCoverDto);
                postCoverDtos.add(postCoverDto);
            }
            postCoverDtos.get(0).setOtherInfoDto(otherInfoDto);
        }else{
            otherInfo = userInfoMapper.selectByUserId(otherId);
            //检验用户状态是否正常
            if(!UserStatusEnum.NORMAL.equals(UserStatusEnum.getUserStatusEnum(userInfo.getStatus()))
                    || !UserStatusEnum.NORMAL.equals(UserStatusEnum.getUserStatusEnum(otherInfo.getStatus())
            )){
                throw new GlobalException(ExceptionConstants.SERVER_ERROR);
            }
            //获取其他用户信息
            OtherInfoDto otherInfoDto = userInfoService.otherInfo(otherId,userId);
            PageHelper.startPage(page.getPageNum(),page.getPageSize());
            List<Post> posts = postMapper.selectMainList(postQuery);
            for(Post post:posts){
                PostCoverDto postCoverDto = new PostCoverDto();
                BeanUtils.copyProperties(post, postCoverDto);
                postCoverDtos.add(postCoverDto);
            }
            postCoverDtos.get(0).setOtherInfoDto(otherInfoDto);
        }
        redisTools.setLeftList(RedisConstants.REDIS_POST_KEY+"USER:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        return postCoverDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loveOrCollect(String userId, String postId, Integer status, Integer type) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        Post post = postMapper.selectByPostId(postId);
        //检查用户和帖子状态
        if(userInfo==null || !userInfo.getStatus().equals(UserStatusEnum.NORMAL.getStatus())
        || post == null || !post.getPostStatus().equals(PostEnum.NORMAL.getStatus())
        || TypeEnum.getTypeEnumByStatus(type) == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        UserInfo updateInfo = new UserInfo();
        updateInfo.setUserId(userId);
        updateInfo.setLove(userInfo.getLove());
        updateInfo.setCollect(userInfo.getCollect());
        Date curDate = new Date();
        LoveCollect loveCollectQuery = new LoveCollect();
        loveCollectQuery.setUserId(userId);
        loveCollectQuery.setPostId(postId);
        LoveCollect loveCollect = loveCollectMapper.select(loveCollectQuery);
        //判断是否增加喜欢、取消喜欢、增加收藏、取消收藏
        switch (TypeEnum.getTypeEnumByStatus(type)){
            case LOVE -> {
                if(status.equals(JudgeEnum.ADD.getStatus())){
                    if(loveCollect == null){
                        loveCollect = new LoveCollect();
                    }else {
                        if(loveCollect.getLove()!=null && loveCollect.getLove()){
                            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
                        }
                    }
                    loveCollect.setLoveTime(curDate);
                    loveCollect.setLove(true);
                    post.setPostLike(post.getPostLike()+1);
                    updateInfo.setLove(updateInfo.getLove()+1);
                }else{
                    if(loveCollect == null){
                        loveCollect = new LoveCollect();
                    }else {
                        if(loveCollect.getLove()!=null && !loveCollect.getLove()){
                            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
                        }
                    }
                    loveCollect.setLoveTime(curDate);
                    loveCollect.setLove(false);
                    post.setPostLike(post.getPostLike()-1);
                    updateInfo.setLove(updateInfo.getLove()-1);
                }
            }
            case COLLECT -> {
                if(status.equals(JudgeEnum.ADD.getStatus())){
                    if(loveCollect == null){
                        loveCollect = new LoveCollect();
                    }else {
                        if(loveCollect.getCollect()!=null && loveCollect.getCollect()){
                            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
                        }
                    }
                    loveCollect.setCollectTime(curDate);
                    loveCollect.setCollect(true);
                    post.setCollect(post.getCollect()+1);
                    updateInfo.setCollect(updateInfo.getCollect()+1);
                }else{
                    if(loveCollect == null){
                        loveCollect = new LoveCollect();
                    }else {
                        if(loveCollect.getCollect()!=null && !loveCollect.getCollect()){
                            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
                        }
                    }
                    loveCollect.setCollectTime(curDate);
                    loveCollect.setCollect(false);
                    post.setCollect(post.getCollect()-1);
                    updateInfo.setCollect(updateInfo.getCollect()-1);
                }
            }
            default -> {
                throw new GlobalException(ExceptionConstants.INVALID_PARAM);
            }
        }
        userInfoMapper.updateByUserId(updateInfo,userId);
        loveCollect.setUserId(userId);
        loveCollect.setPostId(postId);
        loveCollectMapper.insertOrUpdate(loveCollect);
        //更新点赞信息
        postMapper.updateByPostId(post,postId);
    }

    @Override
    public PostDto getApprovalPostById(String postId, String userId) throws GlobalException {
        Post post = postMapper.selectByPostId(postId);
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        //检验帖子的用户id是否与当前请求用户相同 帖子状态是否是正在审批 用户状态是否正常
        if(post == null  || !post.getPostStatus().equals(PostEnum.REVIEWING.getStatus())
        || userInfo == null || !userInfo.getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post,postDto);
        postDto.setName(userInfo.getName());
        postDto.setPhoto(userInfo.getPhoto());
        postDto.setPostLike(null);
        postDto.setCollect(null);
        return postDto;
    }

    @Override
    public List<PostCoverDto> getMyPost(Page page,String userId,String type,String otherId) throws GlobalException {
        PostQuery postQuery = new PostQuery();
        postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
        postQuery.setOrderBy(type);
        postQuery.setUserId(otherId);
        postQuery.setPage(page);
        List<PostCoverDto> postCoverDtos = postMapper.selectCoverList(postQuery);
        OtherInfoDto otherInfoDto = userInfoService.otherInfo(otherId,userId);
        if(!postCoverDtos.isEmpty()){
            postCoverDtos.get(0).setOtherInfoDto(otherInfoDto);
        }
        //添加缓存
        redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"USER:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        return postCoverDtos;
    }

    @Override
    public List<PostCoverDto> getMyLikeCollectPost(Page page, String otherId,Integer status,String userId) throws GlobalException {
        Boolean like = false;
        LoveCollectQuery loveCollectQuery = new LoveCollectQuery();
        loveCollectQuery.setPage(page);
        loveCollectQuery.setUserId(otherId);
        switch (TypeEnum.getTypeEnumByStatus(status)){
            case LOVE -> {
                loveCollectQuery.setLove(true);
                loveCollectQuery.setOrderBy("love_time desc");
                like = true;
            }
            case COLLECT -> {
                loveCollectQuery.setCollect(true);
                loveCollectQuery.setOrderBy("collect_time desc");
                like = false;
            }
        }
        List<LoveCollect> list = loveCollectMapper.selectList(loveCollectQuery);
        List<PostCoverDto> postCoverDtos = new ArrayList<>();
        for(LoveCollect collect : list){
            PostQuery postQuery = new PostQuery();
            postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
            postQuery.setPostId(collect.getPostId());
            List<PostCoverDto> postCoverDto = postMapper.selectCoverList(postQuery);
            for(int i = 0;i<postCoverDto.size();i++){
                OtherInfoDto otherInfoDto = userInfoService.otherInfo(postCoverDto.get(i).getUserId(),userId);
                postCoverDto.get(i).setOtherInfoDto(otherInfoDto);
            }
            postCoverDtos.add(postCoverDto.get(0));
        }
        //添加缓存
        if(like){
            redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"LIKE:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        }else {
            redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"COLLECT:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        }
        return postCoverDtos;
    }

    @Override
    public List<PostCoverDto> getApprovalList(Page page) {
        PostQuery postQuery = new PostQuery();
        postQuery.setPage(new Page(page.getPageSize(), page.getPageNum()));
        postQuery.setPostStatus(PostEnum.REVIEWING.getStatus());
        postQuery.setOrderBy("post_time desc");

        List<PostCoverDto> postCoverDtos = postMapper.selectCoverList(postQuery);
        return postCoverDtos.stream().peek(coverDto -> {
            OtherInfoDto otherInfoDto = new OtherInfoDto();
            UserInfo userInfo = userInfoMapper.selectByUserId(coverDto.getUserId());
            UserInfoDto vo = new UserInfoDto();
            vo.setName(userInfo.getName());
            otherInfoDto.setUserInfoDto(vo);
            coverDto.setOtherInfoDto(otherInfoDto);
        }).collect(Collectors.toList());
    }


    //查找帖子全部评论
    private List<CommentDto> getCommentDto(String postId){
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setPostId(postId);
        commentQuery.setCommentStatus(CommentEnum.NORMAL.getStatus());
        commentQuery.setOrderBy("comment_date desc");
        commentQuery.setIsNull(true);
        List<CommentDto> commentDtos = commentMapper.selectSpecialList(commentQuery);
        BFSCommentDto(commentDtos,postId);
        return commentDtos;
    }
    //全部查找算法
    //TODO 有时间继续进行优化 如加入分页 提高查询效率
    //TODO 封禁用户评论信息处理
    private void BFSCommentDto(List<CommentDto> target,String postId){
        for(CommentDto commentDto:target){
            String userId = commentDto.getUserId();
            UserInfo userInfo = userInfoMapper.selectByUserId(userId);
            String parentId = commentDto.getParentId();
            if(!Tools.isBlank(parentId)){
                Comment comment = commentMapper.selectByCommentId(commentDto.getParentId());
                UserInfo parentInfo = userInfoMapper.selectByUserId(comment.getUserId());
                commentDto.setParentName(parentInfo.getName());
            }
            commentDto.setUserName(userInfo.getName());
            commentDto.setPhoto(userInfo.getPhoto());
            CommentQuery commentQuery = new CommentQuery();
            commentQuery.setParentId(commentDto.getCommentId());
            commentQuery.setPostId(postId);
            commentQuery.setCommentStatus(CommentEnum.NORMAL.getStatus());
            commentQuery.setIsNull(false);
            commentQuery.setOrderBy("comment_date desc");
            List<CommentDto> childCommentDto = commentMapper.selectSpecialList(commentQuery);
            if(!childCommentDto.isEmpty()){
                commentDto.setChildCommentDto(childCommentDto);
                BFSCommentDto(childCommentDto,postId);
            }
        }
    }
}
