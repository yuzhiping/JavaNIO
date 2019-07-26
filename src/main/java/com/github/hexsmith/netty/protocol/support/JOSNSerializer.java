package com.github.hexsmith.netty.protocol.support;

import com.alibaba.fastjson.JSON;
import com.github.hexsmith.netty.protocol.Serializer;
import com.github.hexsmith.netty.protocol.SerializerAlgorithm;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:49
 */
public class JOSNSerializer implements Serializer {
    /**
     * 序列化算法
     *
     * @return
     */
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    /**
     * 序列化
     *
     * @param object 待序列化对象
     * @return
     */
    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    /**
     * 反序列化
     *
     * @param tClass 反序列化后的类
     * @param bytes  二进制对象
     * @return
     */
    @Override
    public <T> T deserialize(Class<T> tClass, byte[] bytes) {
        return JSON.parseObject(bytes, tClass);
    }
}
