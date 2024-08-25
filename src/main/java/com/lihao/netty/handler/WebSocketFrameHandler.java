package com.lihao.netty.handler;

import com.lihao.config.JwtProperty;
import com.lihao.constants.ExceptionConstants;
import com.lihao.exception.GlobalException;
import com.lihao.netty.ChannelContext;
import com.lihao.util.JwtUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebSocketFrameHandler.class);
    @Resource
    private  JwtProperty jwtProperty;
    @Resource
    private  ChannelContext context;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // 处理 WebSocket 帧
        String token = frame.text();
        if (token == null) {
            ctx.channel().close();
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        Map<String, Object> map = JwtUtil.parseJwt(jwtProperty.getJWT_SECRET(), token);
        if (!map.containsKey("userId")) {
            ctx.channel().close();
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        String userId = (String) map.get("userId");
        context.addUserContext(userId, ctx.channel());
        logger.info("用户{}:上线", userId);
    }
}