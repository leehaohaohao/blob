package com.lihao.controller;

import com.lihao.annotation.MonitorApiUsage;
import com.lihao.config.JwtProperty;
import com.lihao.constants.*;
import com.lihao.entity.dto.ResponsePack;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.RoleInfo;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.RoleEnum;
import com.lihao.enums.UidPrefixEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.RoleUserMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.redis.RedisTools;
import com.lihao.service.Impl.EmailServiceImpl;
import com.lihao.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@CrossOrigin
public class LoginController extends BaseController {
    @Resource
    private EmailServiceImpl emailService;
    @Resource
    private RedisTools redisTools;
    @Resource
    private RoleUserMapper roleUserMapper;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private CheckUtil checkUtil;
    @Resource
    private JwtProperty jwtProperty;
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    @PostMapping("/login")
    @Transactional
    @MonitorApiUsage
    public ResponsePack loginCon(HttpServletRequest request, HttpServletResponse response,
                                 String email, String password) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        if(userInfo == null || !userInfo.getPassword().toLowerCase().equals(DigestUtils.md5Hex(password))){
            throw new GlobalException(ExceptionConstants.EMAIL_NO_MATCH);
        }
        UserInfo updateUser = new UserInfo();
        updateUser.setUserId(userInfo.getUserId());
        updateUser.setLastLoginTime(new Date());
        userInfoMapper.updateByUserId(updateUser, updateUser.getUserId());
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userInfo.getUserId());
        String authorization = JwtUtil.createJwt(jwtProperty.getJWT_SECRET(),
                jwtProperty.getJWT_EXPIRE(),
                map);
        //将token存入redis设置一个月期限
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        redisTools.setTokenUserInfo(userInfoDto);
        /*String token = redisTools.setToken(userInfoDto.getUserId());
        CookieUtil.addCookie( response, StringConstants.TOKEN, token, Math.toIntExact(TimeConstants.ONE_MONTH),request.getContextPath());*/
        //将用户权限信息存入redis
        redisTools.setPermission(userInfo.getUserId(),commonUtil.getPermission(userInfo.getUserId()));
        return getSuccessResponsePack(authorization);
    }
    @PostMapping("/register")
    @Transactional
    @MonitorApiUsage
    public ResponsePack register(String email, String password,
                                 String code,
                                 HttpServletRequest request) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        if(userInfo != null){
            throw new GlobalException(ExceptionConstants.EMAIL_EXIST);
        }
        //校验密码格式
        CheckUtil.checkPassword(password);
        //校验邮箱验证码
        checkUtil.checkEmailCode(email,code);
        //添加用户信息
        Date date = new Date();
        userInfo = new UserInfo();
        String userId = StringUtil.getId(UidPrefixEnum.USER.getPrefix());
        userInfo.setEmail(email);
        userInfo.setPassword(DigestUtils.md5Hex(password));
        userInfo.setUserId(userId);
        userInfo.setCreateTime(date);
        userInfo.setName(userId);
        userInfo.setPhoto(StringConstants.DEFAULT_USER_AVATAR);
        userInfo.setConcern(NumberConstants.DEFAULT_CONCERN);
        userInfo.setFollowers(NumberConstants.DEFAULT_FOLLOWERS);
        userInfo.setPost(NumberConstants.DEFAULT_POST);
        userInfoMapper.insert(userInfo);
        //TODO 默认发布一个私密的教学帖子
        //TODO 保留最后一次所查看的文章
        //给用户绑定基本权限
        if(!roleUserMapper.insert(userId, RoleEnum.NORMAL.getRoleId()).equals(NumberConstants.DEFAULT_UPDATE_INSERT)){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //将用户权限信息存入redis
        redisTools.setPermission(userId,commonUtil.getPermission(userId));
        //更新成功，删除邮箱验证码
        redisTools.delete(RedisConstants.REDIS_EMAIL_CODE+email);
        return getSuccessResponsePack(StringConstants.SUCCESS_REGISTER);
    }
    @PostMapping("/email/code")
    @MonitorApiUsage
    public ResponsePack code(String email) throws GlobalException {
        if(Tools.isBlank(email)){
            throw new GlobalException(ExceptionConstants.INVALID_PARAM);
        }
        emailService.email(email);
        return getSuccessResponsePack(StringConstants.SUCCESS_SEND_EMAIL_CODE);
    }
    @PostMapping("/forget")
    @MonitorApiUsage
    public ResponsePack send(String email , String code,String password) throws GlobalException {
        //判断当前邮箱是否在网站注册
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        Optional.ofNullable(userInfo)
                .orElseThrow(() -> new GlobalException(ExceptionConstants.NO_EMAIL));
        //校验邮箱验证码
        checkUtil.checkEmailCode(email,code);
        //校验密码格式是否符合要求
        CheckUtil.checkPassword(password);
        UserInfo updateInfo = new UserInfo();
        updateInfo.setPassword(DigestUtils.md5Hex(password));
        userInfoMapper.updateByUserId(updateInfo, userInfo.getUserId());
        //更新成功，删除邮箱验证码
        redisTools.delete(RedisConstants.REDIS_EMAIL_CODE+email);
        return getSuccessResponsePack(StringConstants.SUCCESS_UPDATE_PASSWORD);
    }
    @PostMapping("/manager/login")
    @MonitorApiUsage
    public ResponsePack mLogin(String email,String password) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByEmail(email);
        Optional.ofNullable(userInfo)
                .filter(info->info.getPassword().equals(DigestUtils.md5Hex(password)))
                .orElseThrow(()->new GlobalException(ExceptionConstants.EMAIL_NO_MATCH));
        String userId = userInfo.getUserId();
        List<RoleInfo> roleInfoList = roleUserMapper.selectByUserId(userId);
        roleInfoList.stream()
                .filter(roleInfo -> roleInfo.getRoleId().equals(RoleEnum.NORMAL.getRoleId()))
                .findAny()
                .ifPresent(roleInfo -> {
                    try {
                        throw new GlobalException(ExceptionConstants.NO_PERMISSION);
                    } catch (GlobalException e) {
                        logger.error(e.getMessage(),e);
                        throw new RuntimeException(e);
                    }
                });
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        redisTools.setTokenUserInfo(userInfoDto);
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userInfo.getUserId());
        String authorization = JwtUtil.createJwt(jwtProperty.getJWT_SECRET(),
                jwtProperty.getJWT_EXPIRE(),
                map);
        //将用户权限信息存入redis
        redisTools.setPermission(userInfo.getUserId(),commonUtil.getPermission(userInfo.getUserId()));
        return getSuccessResponsePack(authorization);
    }
}
