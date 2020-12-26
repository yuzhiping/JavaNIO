package com.github.hexsmith.netty.codec;

import com.github.hexsmith.netty.protocol.PacketCodeC;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-30 22:58
 */
public class PacketDecoder extends ByteToMessageDecoder {
    /**
     * Decode the from one {@link io.netty.buffer.ByteBuf} to an other. This method will be called till either the input
     * {@link io.netty.buffer.ByteBuf} has nothing to read when return from this method or till nothing was read from the input
     * {@link io.netty.buffer.ByteBuf}.
     *
     * @param ctx the {@link io.netty.channel.ChannelHandlerContext} which this {@link io.netty.handler.codec.ByteToMessageDecoder} belongs to
     * @param in  the {@link io.netty.buffer.ByteBuf} from which to read data
     * @param out the {@link java.util.List} to which decoded messages should be added
     * @throws Exception is thrown if an error occurs
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodeC.INSTANCE.decode(in));
    }
}
