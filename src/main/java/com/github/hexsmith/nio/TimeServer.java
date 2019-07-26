package com.github.hexsmith.nio;

/**
 * 基于NIO方式的时间服务器
 *
 * @author yuzp17311
 * @date 2016/3/28
 */
public class TimeServer {

    public static void main(String[] args) {

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException el) {
                //采用默认值
            }
        }

        MultiplexerTimeServer timeServer;
        timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();

    }
}
