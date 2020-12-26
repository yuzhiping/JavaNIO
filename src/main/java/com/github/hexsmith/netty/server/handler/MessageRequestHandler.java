package com.github.hexsmith.netty.server.handler;

import com.github.hexsmith.netty.protocol.request.MessageRequestPacket;
import com.github.hexsmith.netty.protocol.response.MessageResponsePacket;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-30 23:23
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        LOGGER.info(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
        messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
