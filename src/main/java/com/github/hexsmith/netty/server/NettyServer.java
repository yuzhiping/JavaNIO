/*
 *  Copyright(C) 2016-2018 The hexsmith Authors
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.github.hexsmith.netty.server;

import com.github.hexsmith.netty.client.NettyClient;
import com.github.hexsmith.netty.server.handler.ServerChildHandler;
import com.github.hexsmith.netty.server.handler.ServerHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/11/12 23:31
 */
public class NettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);

    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childAttr(AttributeKey.newInstance("childAttr"), "childValue").handler(new ServerHandler())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) {
                        nioSocketChannel.pipeline().addLast(new StringDecoder());
                        nioSocketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) {
                                LOGGER.info(s);
                            }
                        });
                        nioSocketChannel.pipeline().addLast(new ServerChildHandler());
                    }
                });
            ChannelFuture channelFuture = bind(serverBootstrap, 8090);
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    private static ChannelFuture bind(final ServerBootstrap bootstrap, final int port) {
        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.bind(port).addListener(future -> {
                if (future.isSuccess()) {
                    LOGGER.info("端口[{}]绑定成功", port);
                } else {
                    LOGGER.error("端口[{}]绑定失败", port);
                    bind(bootstrap, port + 1);
                }
            }).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channelFuture;
    }

}
