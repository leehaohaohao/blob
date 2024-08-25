package com.lihao.netty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lihao.entity.dto.GroupCommentDto;
import com.lihao.entity.po.UserInfo;
import com.lihao.entity.query.UserQuery;
import com.lihao.mapper.UserInfoMapper;
import com.lihao.util.Tools;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ChannelContext {
    @Resource
    private UserInfoMapper<UserInfo, UserQuery> userInfoMapper;
    private Logger logger = LoggerFactory.getLogger(ChannelContext.class);
    public static final ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ChannelGroup> groupChannelMap = new ConcurrentHashMap<>();
    //添加用户上下文信息
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
    //从channel中取出用户id
    public String getUserId(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        return attribute.get();
    }
    //将用户添加到组内
    public void addGroupContext(String groupId,String userId){
        ChannelGroup newGroup = groupChannelMap.get(groupId);
        if(newGroup == null){
            newGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            groupChannelMap.put(groupId,newGroup);
        }
        Channel channel = userChannelMap.get(userId);
        if(channel == null){
            return;
        }
        //检查用户是否已经在其他群组中
        for(Map.Entry<String,ChannelGroup> entry:groupChannelMap.entrySet()){
            ChannelGroup group = entry.getValue();
            if(group.contains(channel)&& !entry.getKey().equals(groupId)){
                group.remove(channel);
            }
        }
        //将新用户添加到群组中
        newGroup.add(channel);
    }
    //移除用户上下文
    public void removeUserContext(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attribute.get();
        if(!Tools.isBlank(userId)){
            if(userChannelMap.containsKey(userId)){
                userChannelMap.remove(userId);
                //更新用户下线时间
                UserInfo userInfo = new UserInfo();
                userInfo.setLastOffTime(new Date());
                userInfoMapper.updateByUserId(userInfo,userId);

            }
        }
    }
    //用户下线从群组中移除他的信息，要遍历群组移除所有组里他的信息
    public void removeUserFromSomeGroup(Channel channel){
        for(ChannelGroup channels:groupChannelMap.values()){
            channels.remove(channel);
        }
    }
    //群主从组里移除组员
    public void removeUserFromGroup(String userId,String otherId){
        if(!Tools.isBlank(otherId)){
            //从群组移除其他用户
            if(!userChannelMap.containsKey(otherId)){
                return;
            }
            if(!groupChannelMap.containsKey(userId)){
                return;
            }
            Channel channel = userChannelMap.get(otherId);
            ChannelGroup group = groupChannelMap.get(userId);
            group.remove(channel);
        }
    }
    public void sendMessage(GroupCommentDto commentDto) throws JsonProcessingException {
        ChannelGroup group = groupChannelMap.get(commentDto.getGroupId());
        Channel user = userChannelMap.get(commentDto.getUserId());
        if(group == null || user == null){
            //TODO 不插入数据库
            return;
        }
        for(Channel channel:group){
            if(channel!=user){
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                String json = objectMapper.writeValueAsString(commentDto);
                String userId = getUserId(channel);
                logger.info("发送消息给{}：{}",userId,json);
                try {
                    channel.writeAndFlush(new TextWebSocketFrame(json));
                } catch (Exception e) {
                    logger.error("发送失败",e);
                    throw new RuntimeException(e);
                }
                logger.info("发送消息成功！");
            }
        }
    }
}
