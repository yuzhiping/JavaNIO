package com.github.hexsmith.netty.protocol.response;

import com.github.hexsmith.netty.protocol.AbstractPacket;
import com.github.hexsmith.netty.protocol.Command;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-26 17:43
 */
@Setter
@Getter
public class LoginResponsePacket extends AbstractPacket {

    public boolean success;

    private String reason;

    /**
     * 指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
