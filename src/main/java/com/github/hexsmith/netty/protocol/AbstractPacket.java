package com.github.hexsmith.netty.protocol;

import lombok.Data;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:28
 */
@Data
public abstract class AbstractPacket {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     *
     * @return
     */
    public abstract Byte getCommand();

}
