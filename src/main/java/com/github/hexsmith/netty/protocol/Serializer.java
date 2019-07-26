package com.github.hexsmith.netty.protocol;

import com.github.hexsmith.netty.protocol.support.JOSNSerializer;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:41
 */
public interface Serializer {

    /**
     * 默认序列化方式
     */
    byte JSON_SERIALIZER = SerializerAlgorithm.JSON;

    /**
     * 默认序列化器
     */
    Serializer DEFAULT = new JOSNSerializer();

    /**
     * 序列化算法
     *
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化
     *
     * @param object 待序列化对象
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param tClass 反序列化后的类
     * @param bytes  二进制对象
     * @param <T>    实例对象类型
     * @return
     */
    <T> T deserialize(Class<T> tClass, byte[] bytes);

}
