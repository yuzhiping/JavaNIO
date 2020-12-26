package com.github.hexsmith.netty.client.handler;

import com.github.hexsmith.netty.protocol.response.MessageResponsePacket;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-30 23:16
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageResponseHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket)
        throws Exception {
        LOGGER.info(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
    }
}
