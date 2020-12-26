package com.github.hexsmith.heartbeat;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2020-12-26 22:47
 */
public class ClientPinger extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPinger.class);

    private final Random random = new Random();

    private static final Integer BASE_RANDOM = 8;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ping(ctx.channel());
    }

    private void ping(Channel channel) {
        int second = Math.max(1, random.nextInt(BASE_RANDOM));
        if (second == 5) {
            second = 6;
        }
        LOGGER.info("next heart beat will be send after {}s.", second);
        ScheduledFuture<?> scheduledFuture = channel.eventLoop().schedule(() -> {
            if (channel.isActive()) {
                LOGGER.info("sending heart beat to the server...");
                channel.writeAndFlush(ClientIdleStateTrigger.HEART_BEAT);
            } else {
                LOGGER.warn("The connection had broken,cancel the task that will send a heart beat.");
                channel.closeFuture();
                throw new RuntimeException();
            }
        }, second, TimeUnit.SECONDS);

        scheduledFuture.addListener(new GenericFutureListener() {

            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    ping(channel);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 当Channel已经断开的情况下, 仍然发送数据, 会抛异常, 该方法会被调用.
        cause.printStackTrace();
        ctx.close();
    }
}
