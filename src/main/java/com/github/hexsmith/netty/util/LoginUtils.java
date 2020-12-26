package com.github.hexsmith.netty.util;

import com.github.hexsmith.netty.protocol.Attributes;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-29 16:43
 */
public class LoginUtils {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }

}
