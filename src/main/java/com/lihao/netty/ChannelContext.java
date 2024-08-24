package com.lihao.netty;

import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.util.Tools;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.Resource;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ChannelContext {
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    public static final ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ChannelGroup> groupChannelMap = new ConcurrentHashMap<>();

    public void addUserContext(String userId,Channel channel){
        String channelId = channel.id().toString();
        AttributeKey attributeKey = null;
        if(!AttributeKey.exists(channelId)){
            attributeKey = AttributeKey.newInstance(channelId);
        }else{
            attributeKey = AttributeKey.valueOf(channelId);
        }
        channel.attr(attributeKey).set(userId);
        if(userChannelMap.containsKey(userId)){
            userChannelMap.replace(userId,channel);
        }else{
            userChannelMap.put(userId,channel);
        }
    }
    public String getUserId(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        return attribute.get();
    }
    public void addGroupContext(String groupId,String userId){
        ChannelGroup group = groupChannelMap.get(groupId);
        if(group == null){
            group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groupChannelMap.put(groupId,group);
        }
        Channel channel = userChannelMap.get(userId);
        if(channel == null){
            return;
        }
        group.add(channel);
    }
    public void removeUserContext(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attribute.get();
        if(!Tools.isBlank(userId)){
            userChannelMap.remove(userId);
        }
        //更新用户下线时间
        UserInfo userInfo = new UserInfo();
        userInfo.setLastOffTime(new Date());
        userInfoMapper.updateByUserId(userInfo,userId);
    }
}
