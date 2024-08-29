package com.lihao.aspect;

import com.lihao.annotation.*;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.RedisConstants;
import com.lihao.entity.po.ApiStatistics;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.redis.RedisUtils;
import com.lihao.util.CheckUtil;
import com.lihao.util.CommonUtil;
import com.lihao.util.StringUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Aspect
public class NormalAspect {
    @Resource
    private CheckUtil checkUtil;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @Resource
    private RedisUtils redisUtils;
    private static final Logger logger = LoggerFactory.getLogger(NormalAspect.class);
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();
    @Before("@annotation(com.lihao.annotation.Login)")
    public void login(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Login login = method.getAnnotation(Login.class);
        if(login ==null){
            return;
        }
        if(login.logging()){
            checkUtil.checkLogin();
            //判断用户状态，是否被网站拉黑
            UserInfo userInfo = userInfoMapper.selectByUserId(StringUtil.getUserId());
            if(!UserStatusEnum.NORMAL.equals(UserStatusEnum.getUserStatusEnum(userInfo.getStatus()))){
                throw new GlobalException(ExceptionConstants.ACCOUNT_ERROR);
            }
        }
    }
    //检查发布文章
    @Before("@annotation(com.lihao.annotation.Post)")
    public void post(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Post post = method.getAnnotation(Post.class);
        if(post == null){
            return;
        }
        if(post.checkPostPermission()){
            checkUtil.checkPost();
        }
    }
    //检查发布公告
    @Before("@annotation(com.lihao.annotation.Note)")
    public void note(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Note note = method.getAnnotation(Note.class);
        if(note == null){
            return;
        }
        if(note.checkNotePermission()){
            checkUtil.checkNote();
        }
    }
    //检查登陆和发布文章
    @Before("@annotation(com.lihao.annotation.LPost)")
    public void Lpost(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LPost lPost = method.getAnnotation(LPost.class);
        if(lPost == null){
            return;
        }
        if(lPost.checkLPost()){
            checkUtil.checkLogin();
            checkUtil.checkPost();
        }
    }
    //检查登陆和发布公告
    @Before("@annotation(com.lihao.annotation.LNote)")
    public void Lnote(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LNote lNote = method.getAnnotation(LNote.class);
        if(lNote == null){
            return;
        }
        if(lNote.checkLNote()){
            checkUtil.checkLogin();
            checkUtil.checkNote();
        }
    }
    //检查登陆和审核文章
    @Before("@annotation(com.lihao.annotation.LApprovalPost)")
    public void Lapproval(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        LApprovalPost lApprovalPost = method.getAnnotation(LApprovalPost.class);
        if(lApprovalPost == null){
            return;
        }
        if(lApprovalPost.checkLApprovalPost()){
            checkUtil.checkLogin();
            checkUtil.checkApprovalPost();
        }
    }
    //检查登陆和发表评论
    @Before("@annotation(com.lihao.annotation.LComment)")
    public void Lcomment(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        LComment lComment = method.getAnnotation(LComment.class);
        if(lComment == null){
            return;
        }
        if(lComment.checkLComment()){
            checkUtil.checkLogin();
            checkUtil.checkComment();
        }
    }
    //检查管理员身份
    @Before("@annotation(com.lihao.annotation.Manager)")
    public void manager(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        Manager manager = method.getAnnotation(Manager.class);
        if(manager == null){
            return;
        }
        if(manager.checkManager()){
            checkUtil.checkManager();
        }
    }
    @Around("@annotation(com.lihao.annotation.MonitorApiUsage)")
    public Object monitorApiUsage(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String apiName = className+"."+methodName;
        //加锁 防止并发出现数据统计问题
        ReentrantLock lock = lockMap.computeIfAbsent(apiName, k -> new ReentrantLock());
        lock.lock();
        try {
            ApiStatistics api = (ApiStatistics) redisUtils.hget(RedisConstants.REDIS_API,apiName);
            if(api == null){
                api = new ApiStatistics();
                api.setName(apiName);
                api.setId(StringUtil.getId(UidPrefixEnum.API.getPrefix()));
                api.setMinTime(100000000000L);
            }
            api.setCount(api.getCount()+1);
            api.setMinTime(Math.min(api.getMinTime(), responseTime));
            api.setMaxTime(Math.max(api.getMaxTime(), responseTime));
            double currentAverage = api.getAverageTime();
            long count = api.getCount();
            double newAverage = currentAverage + (responseTime - currentAverage) / count;
            api.setAverageTime(newAverage);

            //将更新后的统计数据存回redis
            redisUtils.hset(RedisConstants.REDIS_API,apiName,api);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return result;
    }
}
