package com.lihao.netty;

import com.lihao.config.JwtProperty;
import com.lihao.constants.ExceptionConstants;
import com.lihao.exception.GlobalException;
import com.lihao.util.JwtUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@ChannelHandler.Sharable
@Component
public class CustomWebSocketServerHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LoggerFactory.getLogger(CustomWebSocketServerHandler.class);
    @Resource
    private ChannelContext context;
    @Resource
    private JwtProperty jwtProperty;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            // 读取Sec-WebSocket-Protocol头
            String subprotocols = req.headers().get("Sec-WebSocket-Protocol");

            // 验证子协议
            if (subprotocols == null) {
                ctx.close();
                return;
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(req), subprotocols, true);
            WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                // 在握手响应中包含Sec-WebSocket-Protocol头
                HttpHeaders headers = new DefaultHttpHeaders();
                headers.set("Sec-WebSocket-Protocol", subprotocols);
                handshaker.handshake(ctx.channel(), req, headers, ctx.newPromise());
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;
            /*String token = complete.requestHeaders().get(jwtProperty.getJWT_TOKEN());*/
            String token = complete.requestHeaders().get("Sec-WebSocket-Protocol");
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

    private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + "/ws";
        return "ws://" + location;
    }
}
