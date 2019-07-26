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
package com.github.hexsmith.netty;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/11/26 23:53
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info(LocalDate.now() + "客户端写入数据");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
    }

    private ByteBuf getByteBuf(ChannelHandlerContext context) {
        // 获取二进制抽象ByteBuf
        ByteBuf byteBuf = context.alloc().buffer();
        byte[] bytes = "hello,hexsmith".getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(LocalDateTime.now() + ": 客户端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
