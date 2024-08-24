package com.lihao.netty;

import com.lihao.config.JwtProperty;
import com.lihao.constants.ExceptionConstants;
import com.lihao.exception.GlobalException;
import com.lihao.util.JwtUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;


@ChannelHandler.Sharable
@Component
public class NettyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final static Logger logger = LoggerFactory.getLogger(NettyHandler.class);
    @Resource
    private ChannelContext context;
    @Resource
    private JwtProperty jwtProperty;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //TODO 读取用户心跳信息 存入redis
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("有新的连接加入......");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("用户{}:下线", context.getUserId(ctx.channel()));
        context.removeUserContext(ctx.channel());

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            String token = complete.requestHeaders().get(jwtProperty.getJWT_TOKEN());
            if(token == null){
                ctx.channel().close();
                throw new GlobalException(ExceptionConstants.NO_LOGGING);
            }
            Map<String,Object> map = JwtUtil.parseJwt(jwtProperty.getJWT_SECRET(),token);
            if(!map.containsKey("userId")){
                ctx.channel().close();
                throw new GlobalException(ExceptionConstants.NO_LOGGING);
            }
            String userId = (String) map.get("userId");
            context.addUserContext(userId,ctx.channel());
            logger.info("用户{}:上线", userId);
        }
    }
}
