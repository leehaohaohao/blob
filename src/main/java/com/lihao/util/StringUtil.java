package com.lihao.util;

import com.lihao.config.UserContext;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.exception.GlobalException;

import java.util.UUID;

public class StringUtil {
    public static String setToken(String userId){
        String token = UUID.randomUUID().toString()+userId;
        return token;
    }
    public static String getId(String prefix){
        return prefix+CommonUtil.getId();
    }
    public static String getUserId() throws GlobalException {
        /*String token = CookieUtil.getCookie(request, StringConstants.TOKEN);
        String userId = null;
        if(token==null){
            throw new GlobalException("无效token！");
            //userId = "U1805230146664833024";
        }else {
            userId = StringUtil.getUserId(token);
        }
        //TODO 记得改回去*/
        String userId = UserContext.getUserId();
        if(userId==null){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        return userId;
    }
    public static String getUserId(String token){
        return token.substring(NumberConstants.ID_LENGTH_PREFIX,NumberConstants.ID_LENGTH_SUFFIX);
    }
    public static String getImgPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        return path + "/src/main/resources/static/img/";
    }

    public static String getPostCoverPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        return path + "/src/main/resources/static/post/cover/";
    }
    public static String getFeedBackPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        return path + "/src/main/resources/static/feedback/";
    }
}
