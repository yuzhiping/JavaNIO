package com.github.hexsmith.netty.protocol;

import com.github.hexsmith.netty.protocol.support.JOSNSerializer;
import com.github.hexsmith.netty.protocol.support.LoginRequestPacket;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:58
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Map<Byte, Class<? extends AbstractPacket>> PACKET_TYPE_MAP;

    private static final Map<Byte, Serializer> SERIALIZER_MAP;

    static {
        PACKET_TYPE_MAP = new HashMap<>(1 << 4);
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);

        SERIALIZER_MAP = new HashMap<>(1 << 4);
        Serializer serializer = new JOSNSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(AbstractPacket packet) {
        // 1、创建ByteBuf 对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2、序列化数据包
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        // 3、实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    public AbstractPacket decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);
        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        Class<? extends AbstractPacket> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return SERIALIZER_MAP.get(serializeAlgorithm);
    }

    private Class<? extends AbstractPacket> getRequestType(byte command) {
        return PACKET_TYPE_MAP.get(command);
    }

}
