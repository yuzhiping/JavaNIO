package com.github.hexsmith.netty.protocol;

/**
 * 指令类型
 *
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:35
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
}
