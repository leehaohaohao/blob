package com.lihao.util;

import com.lihao.config.UserContext;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.constants.StringConstants;
import com.lihao.exception.GlobalException;

import java.util.UUID;

public class StringUtil {
    public static String getId(String prefix){
        return prefix+CommonUtil.getId();
    }
    public static String getUserId() throws GlobalException {
        String userId = UserContext.getUserId();
        if(userId==null){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        return userId;
    }
    public static String getImgPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        if(StringConstants.isDev){
            return path + "/src/main/resources/static/img/";
        }
        return "/app/blob/static/img";
    }

    public static String getPostCoverPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        if(StringConstants.isDev) {
            return path + "/src/main/resources/static/post/cover/";
        }
        return "/app/blob/static/post/cover";
    }
    public static String getGroupAvatarPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        if(StringConstants.isDev){
        return path + "/src/main/resources/static/group/avatar/";
        }
        return "/app/blob/static/group/avatar";
    }
    public static String getFeedBackPath() {
        // 获取项目根目录
        String path = System.getProperty("user.dir");
        // 获取图片存放的完整路径
        if(StringConstants.isDev) {
            return path + "/src/main/resources/static/feedback/";
        }
        return "/app/blob/static/feedback";
    }
}
