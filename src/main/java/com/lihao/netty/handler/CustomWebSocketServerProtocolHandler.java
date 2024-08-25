package com.lihao.netty.handler;

import com.lihao.config.JwtProperty;
import com.lihao.constants.ExceptionConstants;
import com.lihao.exception.GlobalException;
import com.lihao.netty.ChannelContext;
import com.lihao.netty.CustomWebSocketServerHandler;
import com.lihao.netty.NettyHandler;
import com.lihao.util.JwtUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
@ChannelHandler.Sharable
public class CustomWebSocketServerProtocolHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private Logger logger = LoggerFactory.getLogger(CustomWebSocketServerProtocolHandler.class);
    private final String websocketPath = "/ws";
    @Resource
    private JwtProperty jwtProperty;
    @Resource
    private ChannelContext context;
    @Resource
    private WebSocketFrameHandler webSocketFrameHandler;
    @Resource
    private NettyHandler nettyHandler;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // 处理握手请求
        String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
        if (subprotocols == null) {
            ctx.close();
            return;
        }
        // 验证子协议中的 token
        Map<String, Object> map = JwtUtil.parseJwt(jwtProperty.getJWT_SECRET(), subprotocols);
        if (!map.containsKey("userId")) {
            ctx.close();
            throw new GlobalException(ExceptionConstants.NO_LOGGING);
        }
        String userId = (String) map.get("userId");
        context.addUserContext(userId, ctx.channel());
        logger.info("用户{}:上线", userId);

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                getWebSocketLocation(req), subprotocols, true);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            HttpHeaders headers = new DefaultHttpHeaders();
            headers.set("Sec-WebSocket-Protocol", subprotocols);
            handshaker.handshake(ctx.channel(), req, headers, ctx.newPromise());
        }

        // 将处理器替换为处理 WebSocket 帧的处理器
        ctx.pipeline().replace(this, "nettyHandler", nettyHandler);
    }

    private String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HttpHeaderNames.HOST) + websocketPath;
        return "ws://" + location;
    }
}
