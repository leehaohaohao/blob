package com.lihao.netty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lihao.constants.ExceptionConstants;
import com.lihao.entity.dto.GroupCommentDto;
import com.lihao.exception.GlobalException;
import com.lihao.util.Tools;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ChannelContext {
    private Logger logger = LoggerFactory.getLogger(ChannelContext.class);
    public static final ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ChannelGroup> groupChannelMap = new ConcurrentHashMap<>();
    //添加用户上下文信息
    @PreDestroy
    public void clear(){
        userChannelMap.clear();
        groupChannelMap.clear();
        logger.info("清除人员、群组信息");
    }

    /**
     * 添加用户上下文信息
     * @param userId
     * @param channel
     */
    public void addUserContext(String userId, Channel channel) {
        // 获取Channel的唯一ID
        String channelId = channel.id().toString();
        AttributeKey<String> attributeKey = null;
        // 检查是否已经存在该Channel的AttributeKey
        if (!AttributeKey.exists(channelId)) {
            attributeKey = AttributeKey.newInstance(channelId);
        } else {
            attributeKey = AttributeKey.valueOf(channelId);
        }
        // 将userId设置为Channel的属性
        channel.attr(attributeKey).set(userId);
        // 如果不存在，添加新的用户-Channel映射
        userChannelMap.put(userId, channel);
    }

    //从channel中取出用户id
    public String getUserId(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        return attribute.get();
    }
    //将用户添加到组内
    public void addGroupContext(String groupId,String userId){
        ChannelGroup newGroup = groupChannelMap.get(groupId);
        if(newGroup == null || newGroup.isEmpty()){
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
        if (newGroup.add(channel)) {
            logger.info("用户{}加入群组{}", userId, groupId);
        } else {
            if (newGroup.contains(channel)) {
                logger.info("用户{}已经在群组{}中", userId, groupId);
            } else {
                logger.warn("用户{}未能加入群组{}，原因未知", userId, groupId);
            }
        }

    }
    //移除用户上下文
    public void removeUserContext(Channel channel){
        Attribute<String> attribute = channel.attr(AttributeKey.valueOf(channel.id().toString()));
        String userId = attribute.get();
        if(!Tools.isBlank(userId)){
            if(userChannelMap.containsKey(userId)){
                logger.info("已经将用户{}移除！",userId);
                userChannelMap.remove(userId);
            }
        }
        // 关闭用户的 Channel
        if (channel.isActive()) {
            channel.close();
            logger.info("用户{}的Channel已关闭", userId);
        }
    }
    //用户下线从群组中移除他的信息，要遍历群组移除所有组里他的信息
    public void removeUserFromSomeGroup(Channel channel){
        for(ChannelGroup channels:groupChannelMap.values()){
            logger.info("已经将用户从组内移除！");
            channels.remove(channel);
        }
    }
    //群主从组里移除组员
    public void removeUserFromGroup(String groupId,String otherId){
        if(!Tools.isBlank(otherId)){
            //从群组移除其他用户
            if(!userChannelMap.containsKey(otherId)){
                return;
            }
            if(!groupChannelMap.containsKey(groupId)){
                return;
            }
            Channel channel = userChannelMap.get(otherId);
            ChannelGroup group = groupChannelMap.get(groupId);
            group.remove(channel);
        }
    }
    public void sendMessage(GroupCommentDto commentDto) throws JsonProcessingException, GlobalException {
        ChannelGroup group = groupChannelMap.get(commentDto.getGroupId());
        Channel user = userChannelMap.get(commentDto.getUserId());
        if(group == null || user == null){
            throw new GlobalException(ExceptionConstants.GROUP_NOT_EXIST);
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
