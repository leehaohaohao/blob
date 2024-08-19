package com.lihao.aspect;

import com.lihao.annotation.*;
import com.lihao.exception.GlobalException;
import com.lihao.util.CheckUtil;
import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@Aspect
public class NormalAspect {
    @Resource
    private CheckUtil checkUtil;
    private static final Logger logger = LoggerFactory.getLogger(NormalAspect.class);
    @Before("@annotation(com.lihao.annotation.Login)")
    public void login(JoinPoint joinPoint) throws GlobalException {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Login login = method.getAnnotation(Login.class);
        if(login ==null){
            return;
        }
        if(login.logging()){
            checkUtil.checkLogin();
            //TODO 判断用户状态，是否被网站拉黑
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
}
