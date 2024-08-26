package com.lihao.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyHeart extends ChannelInboundHandlerAdapter {
    private final ChannelContext context;
    private static final Logger logger = LoggerFactory.getLogger(NettyHeart.class);
    public NettyHeart(ChannelContext context) {
        this.context = context;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            Attribute<String> attribute =ctx.channel().attr(AttributeKey.valueOf(ctx.channel().id().toString()));
            String userId = attribute.get();
            if(event.state()== IdleState.READER_IDLE){
                logger.info("用户{}:心跳超时",userId);
                context.removeUserFromSomeGroup(ctx.channel());
                context.removeUserContext(ctx.channel());
                ctx.close();
            }
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
