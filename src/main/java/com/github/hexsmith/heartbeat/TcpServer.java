package com.github.hexsmith.heartbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2020-12-26 23:37
 */
public class TcpServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpServer.class);

    private final int port;
    private final ServerHandlerInitializer serverHandlerInitializer;

    public TcpServer(int port) {
        this.port = port;
        this.serverHandlerInitializer = new ServerHandlerInitializer();
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(this.serverHandlerInitializer);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(port).sync();

            LOGGER.info("Server start listen at " + port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 2222;
        new TcpServer(port).start();
    }
}
