package com.github.hexsmith.netty.codec;

import com.github.hexsmith.netty.protocol.AbstractPacket;
import com.github.hexsmith.netty.protocol.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-30 23:00
 */
public class PacketEncoder extends MessageToByteEncoder<AbstractPacket> {
    /**
     * Encode a message into a {@link io.netty.buffer.ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link io.netty.buffer.ByteBuf} into which the encoded message will be written
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, ByteBuf out) throws Exception {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
