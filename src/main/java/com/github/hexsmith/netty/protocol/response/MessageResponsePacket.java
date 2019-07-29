package com.github.hexsmith.netty.protocol.response;

import com.github.hexsmith.netty.protocol.AbstractPacket;
import com.github.hexsmith.netty.protocol.Command;

import lombok.Data;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-29 16:39
 */
@Data
public class MessageResponsePacket extends AbstractPacket {

    private String message;

    /**
     * 指令
     *
     * @return
     */
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
