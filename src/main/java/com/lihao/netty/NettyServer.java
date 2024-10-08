package com.lihao.netty;

import com.lihao.config.AppConfig;
import com.lihao.netty.handler.CustomWebSocketServerProtocolHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServer {
    @Resource
    private ChannelContext channelContext;
    @Resource
    private AppConfig appConfig;
    @Resource
    private CustomWebSocketServerProtocolHandler customWebSocketServerProtocolHandler;
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    public void start() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(64 * 1024))
                                .addLast(new IdleStateHandler(appConfig.getWsHeart(), 0, 0, TimeUnit.SECONDS))
                                .addLast(new NettyHeart(channelContext))
                                .addLast(new ChunkedWriteHandler())
                                .addLast(customWebSocketServerProtocolHandler);
                    }
                });
        ChannelFuture future = bootstrap.bind(appConfig.getWsPort()).sync();
        future.channel().closeFuture().sync();
    }
    @PreDestroy
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
