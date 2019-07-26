package com.github.hexsmith.netty.server.handler;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 14:58
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirstServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        LOGGER.info(LocalDateTime.now() + " :服务端读到数据->" + byteBuf.toString(StandardCharsets.UTF_8));
        LOGGER.info(LocalDateTime.now() + "写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext context) {
        byte[] bytes = "你好，欢迎学习netty".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = context.alloc().buffer();
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
