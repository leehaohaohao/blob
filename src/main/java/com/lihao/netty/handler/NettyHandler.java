package com.lihao.netty.handler;

import com.lihao.netty.ChannelContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@ChannelHandler.Sharable
@Component
public class NettyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final static Logger logger = LoggerFactory.getLogger(NettyHandler.class);
    @Resource
    private ChannelContext context;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        logger.info("收到心跳消息：{}", textWebSocketFrame.text());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有新的连接加入......");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String userId = context.getUserId(ctx.channel());
        logger.info("用户{}:切换页面或者下线", userId);
        context.updateUserLastTime(userId);
        context.removeUserFromSomeGroup(ctx.channel());
        context.removeUserContext(ctx.channel());
        ctx.close();
    }
}
