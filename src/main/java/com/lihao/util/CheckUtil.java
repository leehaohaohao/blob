package com.lihao.util;

import com.lihao.config.JwtProperty;
import com.lihao.config.UserContext;
import com.lihao.entity.po.Page;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.NumberConstants;
import com.lihao.constants.StringConstants;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.RoleInfo;
import com.lihao.enums.PermissionEnum;
import com.lihao.enums.RoleEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.RoleUserMapper;
import com.lihao.redis.RedisTools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
@Component
public class CheckUtil {
    @Resource
    private JwtProperty jwtProperty;
    @Resource
    private RedisTools redisTools;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private RoleUserMapper roleUserMapper;
    public static boolean checkFile(MultipartFile file,boolean must) throws GlobalException {
        if(file == null || file.getOriginalFilename().isEmpty()){
            if(must){
                throw new GlobalException(ExceptionConstants.INVALID_FILE);
            }
            return false;
        }
        return true;
    }
    public void checkLogin() throws GlobalException {
        HttpServletRequest request = Tools.getRequest();
        String token = request.getHeader(jwtProperty.getJWT_TOKEN());
        if(token == null){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        Map<String,Object> map = JwtUtil.parseJwt(jwtProperty.getJWT_SECRET(),token);
        if(!map.containsKey("userId")){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        String userId = (String) map.get("userId");
        UserContext.setUserId(userId);
        /*String token = CookieUtil.getCookie(request, StringConstants.TOKEN);
        if(token == null ){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        String userId = token.substring(NumberConstants.ID_LENGTH_PREFIX,NumberConstants.ID_LENGTH_SUFFIX);
        String realToken = redisTools.getToken(userId);
        if(realToken == null || !realToken.equals(token)){
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        UserInfoDto userInfoDto = redisTools.getTokenUserInfoDto(userId);
        if(!userInfoDto.getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }*/
    }
    public void checkPost() throws GlobalException {
        Set<String> setPermissionId = commonUtil.getPermission(null);
        if(!setPermissionId.contains(PermissionEnum.PUBLISH_ARTICLES.getPermissionId())){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
    }
    public void checkNote() throws GlobalException {
        Set<String> setPermissionId = commonUtil.getPermission(null);
        if(!setPermissionId.contains(PermissionEnum.PUBLISH_NOTE.getPermissionId())){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
    }
    public void checkApprovalPost() throws GlobalException {
        Set<String> setPermissionId = commonUtil.getPermission(null);
        if(!setPermissionId.contains(PermissionEnum.APPROVAL_POST.getPermissionId())){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
    }
    public void checkComment() throws GlobalException{
        Set<String> setPermissionId = commonUtil.getPermission(null);
        if(!setPermissionId.contains(PermissionEnum.PUBLISH_COMMENTS.getPermissionId())){
            throw new GlobalException(ExceptionConstants.NO_PERMISSION);
        }
    }
    public static void checkImageType(MultipartFile file) throws GlobalException {
        String fileName = file.getOriginalFilename();
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        if(!Arrays.asList(StringConstants.IMAGE_SUFFIX).contains(fileSuffix.toLowerCase())){
            throw new GlobalException(ExceptionConstants.INVALID_FILE);
        }
    }
    public static void checkGender(Integer gender) throws GlobalException {
        if(gender!=null && (!gender.equals(1) && !gender.equals(0))){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
    }
    public static void checkPassword(String password) throws GlobalException {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,20}$";
        if(!Pattern.matches(regex, password)){
            throw new GlobalException(ExceptionConstants.INVALID_PASSWORD_FORMAT);
        }
    }
    public static boolean checkTag(String selfTag) throws GlobalException {
        if(selfTag == null){
            return false;
        }
        if(selfTag.isEmpty()){
            return false;
        }
        String[] tags = selfTag.split("\\|");
        if(tags.length>NumberConstants.TAG_NUMBER){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        for(String tag:tags){
            if(tag.length()> NumberConstants.TAG_LENGTH){
                throw new GlobalException(ExceptionConstants.INVALID_TAG_FORMAT);
            }
        }
        return true;
    }
    public void checkEmailCode(String email,String code) throws GlobalException {
        Integer realCode = redisTools.getEmailCode(email);
        //检查验证码是否正确
        if(!realCode.toString().equals(code)){
            throw new GlobalException(ExceptionConstants.EMAIL_CODE_FAULT);
        }
    }

    public void checkManager() throws GlobalException {
        HttpServletRequest request = Tools.getRequest();
        String token = CookieUtil.getCookie(request, StringConstants.TOKEN);
        String userId = token.substring(NumberConstants.ID_LENGTH_PREFIX,NumberConstants.ID_LENGTH_SUFFIX);
        List<RoleInfo> roleInfoList = roleUserMapper.selectByUserId(userId);
        for(RoleInfo roleInfo :roleInfoList){
            if(roleInfo.getRoleId().equals(RoleEnum.NORMAL.getRoleId())){
                throw new GlobalException(ExceptionConstants.NO_PERMISSION);
            }
        }
    }
    public static void checkPage(Page page) throws GlobalException {
        if(page == null || page.getPageNum() == null || page.getPageSize() == null){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
    }
}
