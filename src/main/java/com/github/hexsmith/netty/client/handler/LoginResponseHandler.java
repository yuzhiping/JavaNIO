package com.github.hexsmith.netty.client.handler;

import com.github.hexsmith.netty.protocol.request.LoginRequestPacket;
import com.github.hexsmith.netty.protocol.response.LoginResponsePacket;
import com.github.hexsmith.netty.util.LoginUtils;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-30 23:11
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResponseHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("hexsmith");
        loginRequestPacket.setPassword("root");

        // 写数据
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    /**
     * <strong>Please keep in mind that this method will be renamed to
     * {@code messageReceived(ChannelHandlerContext, I)} in 5.0.</strong>
     * <p>
     *
     * @param ctx                 the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.channel.SimpleChannelInboundHandler}
     *                            belongs to
     * @param loginResponsePacket the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            LOGGER.info(new Date() + ": 客户端登录成功");
            LoginUtils.markAsLogin(ctx.channel());
        } else {
            LOGGER.error(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }
}
