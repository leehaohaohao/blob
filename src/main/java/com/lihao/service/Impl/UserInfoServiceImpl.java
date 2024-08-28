package com.lihao.service.Impl;

import com.lihao.config.AppConfig;
import com.lihao.constants.ExceptionConstants;
import com.lihao.constants.StringConstants;
import com.lihao.entity.dto.OtherInfoDto;
import com.lihao.entity.dto.UserInfoDto;
import com.lihao.entity.po.Concern;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.enums.RelationshipEnum;
import com.lihao.enums.UserStatusEnum;
import com.lihao.exception.GlobalException;
import com.lihao.mapper.ConcernMapper;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.redis.RedisTools;
import com.lihao.service.UserInfoService;
import com.lihao.util.CheckUtil;
import com.lihao.util.FileUtil;
import com.lihao.util.Tools;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private RedisTools redisTools;
    @Resource
    private ConcernMapper concernMapper;
    @Resource
    private AppConfig appConfig;
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    private final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Override
    public UserInfoDto getUserInfo(String userId) {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        //考虑账号被封禁的情况
        if(userInfo.getStatus().equals(UserStatusEnum.ABNORMAL.getStatus())){
            userInfoDto.setName(appConfig.getErrorName());
            userInfoDto.setPhoto(appConfig.getErrorImg());
        }
        return userInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(UserInfo userInfo, MultipartFile file) throws GlobalException {
        String[] ss = null;
        if(file!=null && !file.getOriginalFilename().isEmpty()){
            CheckUtil.checkImageType(file);
            ss=FileUtil.fileBookLoad(file,StringConstants.AVATAR_PATH);
            if(ss == null ||ss[0]==null || ss.length!=2){
                throw new GlobalException(ExceptionConstants.SERVER_ERROR);
            }
        }
        CheckUtil.checkGender(userInfo.getGender());
        if(ss!=null){
            userInfo.setPhoto(ss[0]);
        }
        if(userInfoMapper.updateByUserId(userInfo,userInfo.getUserId())!=1){
            if(ss!=null && ss[1]!=null){
                FileUtil.removeFile(ss[1]);
            }
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        userInfo = userInfoMapper.selectByUserId(userInfo.getUserId());
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto);
        redisTools.setTokenUserInfo(userInfoDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo updateTag(UserInfo updateInfo) {
        userInfoMapper.updateByUserId(updateInfo, updateInfo.getUserId());
        return userInfoMapper.selectByUserId(updateInfo.getUserId());
    }

    @Override
    public OtherInfoDto otherInfo(String otherId, String userId) throws GlobalException {
        UserInfo userInfo = userInfoMapper.selectByUserId(userId);
        //如果是查看自己的信息
        if(userId.equals(otherId)&& userInfo.getStatus().equals(UserStatusEnum.NORMAL.getStatus())){
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfo.setEmail(null);
            userInfo.setTelephone(null);
            userInfo.setCreateTime(null);
            userInfo.setLastOffTime(null);
            userInfo.setLastLoginTime(null);
            BeanUtils.copyProperties(userInfo,userInfoDto);
            OtherInfoDto otherInfoDto = new OtherInfoDto();
            otherInfoDto.setUserInfoDto(userInfoDto);
            otherInfoDto.setStatus(RelationshipEnum.MINE.getStatus());
            return otherInfoDto;
        }
        UserInfo otherInfo = userInfoMapper.selectByUserId(otherId);
        //检验用户是否存在
        if(userInfo == null || otherInfo == null){
            throw new GlobalException(ExceptionConstants.SERVER_ERROR);
        }
        //检验用户状态是否正常
        //如果other是异常状态这显示异常用户信息
        if(!UserStatusEnum.NORMAL.equals(UserStatusEnum.getUserStatusEnum(otherInfo.getStatus()))){
            otherInfo.setName(appConfig.getErrorName());
            otherInfo.setPhoto(appConfig.getErrorImg());
        }
        //查看用户与查看用户之间的关系
        Concern userConcern = concernMapper.select(userId,otherId);
        Concern otherConcern = concernMapper.select(otherId,userId);
        //除去无用信息
        otherInfo.setEmail(null);
        otherInfo.setTelephone(null);
        otherInfo.setCreateTime(null);
        otherInfo.setLastOffTime(null);
        otherInfo.setLastLoginTime(null);
        UserInfoDto otherInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(otherInfo,otherInfoDto);
        OtherInfoDto infoDto = new OtherInfoDto();
        infoDto.setUserInfoDto(otherInfoDto);
        //判断关系
        if(userConcern == null && otherConcern != null){
            infoDto.setStatus(RelationshipEnum.FAN.getStatus());
        }else if(userConcern != null && otherConcern == null){
            infoDto.setStatus(RelationshipEnum.CONCERN.getStatus());
        }else if(userConcern != null && otherConcern != null){
            infoDto.setStatus(RelationshipEnum.FRIEND.getStatus());
        }else if(userConcern == null && otherConcern == null){
            infoDto.setStatus(RelationshipEnum.STRANGER.getStatus());
        }
        return infoDto;
    }
}
