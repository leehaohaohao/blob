package com.lihao.redis;

import com.lihao.constants.RedisConstants;
import com.lihao.constants.TimeConstants;
import com.lihao.entity.dto.PostDto;
import com.lihao.entity.dto.UserInfoDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component("redisTools")
public class RedisTools {
    @Resource
    private RedisUtils redisUtils;
    public void setTokenUserInfo(UserInfoDto userInfoDto){
        redisUtils.setex(RedisConstants.REDIS_TOKEN_USER_INFO +userInfoDto.getUserId(), userInfoDto, TimeConstants.ONE_MONTH);
    }
    public UserInfoDto getTokenUserInfoDto(String userId){
        return (UserInfoDto)redisUtils.get(RedisConstants.REDIS_TOKEN_USER_INFO +userId);
    }
    public String setToken(String userId){
        String token = userId+ UUID.randomUUID();
        redisUtils.setex(RedisConstants.REDIS_TOKEN_USER_ID+userId,token,TimeConstants.ONE_MONTH);
        return token;
    }
    public String getToken(String userId){
        return (String) redisUtils.get(RedisConstants.REDIS_TOKEN_USER_ID+userId);
    }
    public void setPermission(String userId, Set<String> set){
        redisUtils.sadd(RedisConstants.REDIS_TOKEN_USER_PERMISSION+userId,set,TimeConstants.ONE_MONTH);
    }
    public Set<String> getPermission(String userId){
        return (Set<String>)redisUtils.get(RedisConstants.REDIS_TOKEN_USER_PERMISSION+userId);
    }
    public void setEmailCode(Integer code,String email){
        redisUtils.setex(RedisConstants.REDIS_EMAIL_CODE+email,code,TimeConstants.TEN_MINUTE);
    }
    public Integer getEmailCode(String email){
        return (Integer) redisUtils.get(RedisConstants.REDIS_EMAIL_CODE+email);
    }
    public void delete(String...key){
        redisUtils.delete(key);
    }
    /**
     * 将列表存入Redis
     * @param key 键
     * @param list 列表
     * @param time 有效时间
     * @return true成功 or false失败
     */
    public <T> boolean setLeftList(String key, List<T> list, long time) {
        if(list.size()==0){
            return false;
        }
        return redisUtils.lpushAll(key, list, time);
    }
    public <T> boolean setRightList(String key, List<T> list, long time) {
        if(list.size()==0){
            return false;
        }
        return redisUtils.rpushAll(key, list, time);
    }
    /**
     * 从Redis中获取列表
     * @param key 键
     * @return 列表
     */
    public <T> List<T> getList(String key) {
        return redisUtils.getQueueList(key);
    }
    public void deleteKeysWithPatternAndLimit(String pattern, long limit){
        redisUtils.deleteKeysWithPatternAndLimit(pattern, limit);
    }
    public void setPostDto(PostDto postDto){
        redisUtils.setex(RedisConstants.REDIS_POST_KEY+postDto.getPostId(),postDto,TimeConstants.ONE_SECOND);
    }
    public PostDto getPostDto(String postId){
         return (PostDto)redisUtils.get(RedisConstants.REDIS_POST_KEY+postId);
    }
}
