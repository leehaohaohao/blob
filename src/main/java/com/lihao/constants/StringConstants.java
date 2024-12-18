package com.lihao.constants;

import com.lihao.util.StringUtil;

public class StringConstants {
    public final static String TOKEN = "USER_TOKEN";
    public final static String SUCCESS_LOG = "登陆成功！";
    public final static String SUCCESS_REGISTER = "注册成功！";
    public final static String DEFAULT_TITLE = "无标题";
    public final static String SOLVE_IT = "技术小哥正在快马加鞭的帮您解决！";
    public final static String DEFAULT_USER_AVATAR = "img/defAva.png";
    public final static String DEFAULT_POST_COVER = "img/background.png";
    public final static String DEFAULT_GROUP_AVATAR = "img/group.png";
    public final static String AVATAR_PATH = StringUtil.getImgPath();
    public final static String POST_COVER_IMAGE_PATH = StringUtil.getPostCoverPath();
    public final static String[] IMAGE_SUFFIX = {".png",".jpg",".jpeg",".gif",".svg"};
    public final static String SUCCESS_SEND_EMAIL_CODE = "邮箱验证码发送成功！";
    public final static String SUCCESS_UPDATE_PASSWORD = "更改密码成功！";
    public final static String SUCCESS_OPERATE = "操作成功！";
    public final static String RANDOM_POST = "random_post";
    public final static String URL="http://121.40.154.188:9090/blob/";
    public final static boolean isDev = false;
}
