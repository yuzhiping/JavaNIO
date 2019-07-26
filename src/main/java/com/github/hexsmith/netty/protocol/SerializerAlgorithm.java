package com.github.hexsmith.netty.protocol;

/**
 * 序列化算法
 *
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:47
 */
public interface SerializerAlgorithm {

    /**
     * fastjson 序列化
     */
    byte JSON = 1;

}
