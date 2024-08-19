package com.lihao.listener;

import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.util.StringUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.concurrent.ConcurrentHashMap;
@Component
@WebListener
public class SessionListener implements HttpSessionListener {
    private final static Logger logger = LoggerFactory.getLogger(SessionListener.class);
    private static ConcurrentHashMap<String,Integer> sessionMap = new ConcurrentHashMap<>();
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    /*@Override
    public void sessionCreated(HttpSessionEvent se) {
        try {
            HttpServletRequest request = Tools.getRequest();
            String userId = StringUtil.getUserId(request);
            sessionMap.put(userId,sessionMap.getOrDefault(userId,0)+1);
            //TODO 在数据库存储日活信息
            logger.info("有用户进入网站=======>{},该用户开启{}个窗口,用户连接数{}",userId,sessionMap.get(userId),sessionMap.size());
        } catch (GlobalException e) {
            logger.info("有未登录用户进入网站=======>",e);
        }
    }*/

    /*@Override
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            HttpServletRequest request = Tools.getRequest();
            String userId = StringUtil.getUserId(request);
            if(sessionMap.containsKey(userId) && sessionMap.get(userId)>1){
                sessionMap.put(userId,sessionMap.get(userId)-1);
            }else if(sessionMap.containsKey(userId)){
                sessionMap.remove(userId);
                UserInfo updateInfo = new UserInfo();
                updateInfo.setUserId(userId);
                updateInfo.setLastOffTime(new Date());
                userInfoMapper.updateByUserId(updateInfo,userId);
            }
        } catch (GlobalException e) {
            logger.info("有未登录用户离开网站=======>",e);
        }
    }*/
}
