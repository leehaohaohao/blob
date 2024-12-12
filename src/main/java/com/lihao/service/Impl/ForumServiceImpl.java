package com.lihao.service.Impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.github.pagehelper.PageHelper;
import com.lihao.constants.*;
import com.lihao.entity.dto.*;
import com.lihao.entity.po.*;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ForumServiceImpl implements ForumService {
    private final static Logger looger = LoggerFactory.getLogger(ForumServiceImpl.class);
    @Resource
    private PostMapper<Post, PostQuery> postMapper;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LoveCollectMapper<LoveCollect, LoveCollectQuery> loveCollectMapper;
    @Resource
    private RedisTools redisTools;
    @Resource
    private TagMapper tagMapper;
    private final ConcurrentHashMap<String,Set<String>> postCacheMap = new ConcurrentHashMap<>();
    private final ConcurrentHashSet<String> userIdCache = new ConcurrentHashSet<>();
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(Post post, MultipartFile file) throws GlobalException {
        Date curDate = new Date();
        post.setPostTime(curDate);
        post.setPostStatus(PostEnum.REVIEWING.getStatus());
        post.setPostId(StringUtil.getId(UidPrefixEnum.POST.getPrefix()));
        //储存封面
        String[] ss = Optional.ofNullable(file)
                .filter(f->!Tools.isBlank(file.getOriginalFilename()))
                .map(f->FileUtil.fileBookLoad(file,StringConstants.POST_COVER_IMAGE_PATH))
                .orElse(null);
        if (ss == null) {
            post.setCover(StringConstants.DEFAULT_POST_COVER);
        } else {
            if (ss.length != NumberConstants.FILE_ARRAY_LENGTH) {
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
        //TODO
        approvalPost(post.getPostId(),PostEnum.NORMAL.getStatus(),"U1805198690294980608");
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
        List<PostCoverDto> postCoverDtos = posts.stream().map(post->{
            PostCoverDto postCoverDto = new PostCoverDto();
            BeanUtils.copyProperties(post, postCoverDto);
            try {
                postCoverDto.setOtherInfoDto(userInfoService.otherInfo(post.getUserId(),userId));
            } catch (GlobalException e) {
                looger.error(e.getMessage(),e);
                throw new RuntimeException(e);
            }
            return postCoverDto;
        }).collect(Collectors.toList());
        //TODO 缓存策略优化
        redisTools.setLeftList(RedisConstants.REDIS_POST_KEY+tagFuzzy+":"+page.getPageNum(), postCoverDtos,TimeConstants.ONE_minute);
        return postCoverDtos;
    }
    @Override
    public PostDto getPostById(String postId,String userId) throws GlobalException {
        Post post = postMapper.selectByPostId(postId);
        Optional.ofNullable(post)
                .orElseThrow(()->new GlobalException(ExceptionConstants.INVALID_PARAM));
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
        LoveCollect loveCollectQuery = new LoveCollect();
        loveCollectQuery.setUserId(userId);
        loveCollectQuery.setPostId(postId);
        //添加点赞和收藏信息
        LoveCollect loveCollect = loveCollectMapper.select(loveCollectQuery);
        Optional.ofNullable(loveCollect)
                .ifPresentOrElse(
                        lc -> {
                            postDto.setIsLove(lc.getLove());
                            postDto.setIsCollect(lc.getCollect());
                        },
                        () -> {
                            postDto.setIsLove(false);
                            postDto.setIsCollect(false);
                        }
                );
        //存入缓存
        redisTools.setPostDto(postDto);
        return postDto;
    }
    @Scheduled(fixedRate = 300000)
    public void setPostCache(){
        List<Post> posts = postMapper.selectRandom(PostEnum.NORMAL.getStatus(),100);
        //清楚用户id缓存
        userIdCache.clear();
        //查询所有用户id
        userIdCache.addAll(posts.stream().map(Post::getUserId).collect(Collectors.toSet()));
        postCacheMap.clear();
        //查询所有文章封面信息
        List<PostCoverDto> postDtos = posts.stream().map(post -> {
            PostCoverDto postCoverDto = new PostCoverDto();
            BeanUtils.copyProperties(post, postCoverDto);
            return postCoverDto;
        }).toList();
        redisTools.delete(RedisConstants.REDIS_POST_RAND);
        redisTools.setLeftList(RedisConstants.REDIS_POST_RAND, postDtos,6*TimeConstants.ONE_minute);
    }
    @Override
    public List<PostCoverDto> getRandomPost(Page page, String userId) throws GlobalException {
        List<PostCoverDto> postCoverDtos = redisTools.getList(RedisConstants.REDIS_POST_RAND);
        List<PostCoverDto> result = new ArrayList<>();
        if(page.getPageNum().equals(1)){
           postCacheMap.clear();
        }
        Random random = new Random();
        while(postCoverDtos.isEmpty()){
            setPostCache();
            postCoverDtos=redisTools.getList(RedisConstants.REDIS_POST_RAND);
        }
        //判断是否已经添加过
        if(!postCacheMap.containsKey(userId)){
            postCacheMap.put(userId,new ConcurrentHashSet<>());
        }
        //随机索引列表
        //文章数量有限时可能出现死循环
        Set<Integer> randomIndex = new HashSet<>();
        if(postCoverDtos.size()<=page.getPageSize()){
            while(randomIndex.size() < postCoverDtos.size()){
                randomIndex.add(random.nextInt(postCoverDtos.size()));
            }
        }else{
            while(randomIndex.size() < page.getPageSize()){
                randomIndex.add(random.nextInt(postCoverDtos.size()));
            }
        }
        //异步并行处理
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for(int index :randomIndex){
            PostCoverDto postCoverDto = postCoverDtos.get(index);
            if(!postCacheMap.get(userId).contains(postCoverDto.getPostId())){
                futures.add(CompletableFuture.runAsync(()->{
                    //异步获取otherInfo
                    try {
                        postCoverDto.setOtherInfoDto(userInfoService.otherInfo(postCoverDto.getUserId(),userId));
                        synchronized (result){
                            result.add(postCoverDto);
                        }
                    } catch (GlobalException e) {
                        throw new RuntimeException(e);
                    }
                    postCacheMap.get(userId).add(postCoverDto.getPostId());
                }));
            }
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approvalPost(String postId,Integer postStatus,String approvalId) throws GlobalException {
        Optional.ofNullable(PostEnum.getPostEnum(postStatus))
                .orElseThrow(()->new GlobalException(ExceptionConstants.INVALID_PARAM));
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
        UserInfo otherInfo;
        List<PostCoverDto> postCoverDtos;
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
            postCoverDtos=posts.stream().map(post -> {
                PostCoverDto postCoverDto = new PostCoverDto();
                BeanUtils.copyProperties(post, postCoverDto);
                return postCoverDto;
            }).collect(Collectors.toList());
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
            postCoverDtos=posts.stream().map(post -> {
                PostCoverDto postCoverDto = new PostCoverDto();
                BeanUtils.copyProperties(post, postCoverDto);
                return postCoverDto;
            }).collect(Collectors.toList());
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
        /*//添加缓存
        redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"USER:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);*/
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
        List<PostCoverDto> postCoverDtos = list.stream().map(collect->{
            PostQuery postQuery = new PostQuery();
            postQuery.setPostStatus(PostEnum.NORMAL.getStatus());
            postQuery.setPostId(collect.getPostId());
            List<PostCoverDto> postCoverDto = postMapper.selectCoverList(postQuery);
            for(int i = 0;i<postCoverDto.size();i++){
                OtherInfoDto otherInfoDto = null;
                try {
                    otherInfoDto = userInfoService.otherInfo(postCoverDto.get(i).getUserId(),userId);
                } catch (GlobalException e) {
                    looger.error(e.getMessage(),e);
                    throw new RuntimeException(e);
                }
                postCoverDto.get(i).setOtherInfoDto(otherInfoDto);
            }
            return postCoverDto.get(0);
        }).collect(Collectors.toList());
        /*//添加缓存
        if(like){
            redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"LIKE:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        }else {
            redisTools.setRightList(RedisConstants.REDIS_POST_KEY+"COLLECT:"+page.getPageNum()+":"+otherId,postCoverDtos,TimeConstants.ONE_minute);
        }*/
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(String postId) throws GlobalException {
        Post post = postMapper.selectByPostId(postId);
        Optional.ofNullable(post).orElseThrow(()->new GlobalException(ExceptionConstants.INVALID_PARAM));
        if(!post.getUserId().equals(StringUtil.getUserId())){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
        Post updatePost = new Post();
        updatePost.setPostStatus(PostEnum.DELETED.getStatus());
        if(!postMapper.updateByPostId(updatePost,postId).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //更新用户文章数
        UserInfo userInfo = userInfoMapper.selectByUserId(post.getUserId());
        UserInfo updateInfo = new UserInfo();
        updateInfo.setPost(userInfo.getPost()-1);
        if(!userInfoMapper.updateByUserId(updateInfo,post.getUserId()).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
    }
}
