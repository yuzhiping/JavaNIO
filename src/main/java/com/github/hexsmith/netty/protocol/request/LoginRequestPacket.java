package com.github.hexsmith.netty.protocol.request;

import com.github.hexsmith.netty.protocol.AbstractPacket;
import com.github.hexsmith.netty.protocol.Command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 15:37
 */
@Getter
@Setter
public class LoginRequestPacket extends AbstractPacket {

    private String userId;

    private String username;

    private String password;

    /**
     * 指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
