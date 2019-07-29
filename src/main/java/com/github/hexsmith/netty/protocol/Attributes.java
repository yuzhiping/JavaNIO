package com.github.hexsmith.netty.protocol;

import io.netty.util.AttributeKey;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2019-07-29 16:41
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
