package com.hexsmith.netty.nio;

import java.io.IOException;

/**
 * 基于NIO方式的时间服务器
 * Created by yuzp17311 on 2016/3/28.
 */
public class TimeServer {

    public static void main(String []args) throws IOException{

        int port=8080;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException el){
                //采用默认值
            }
        }

        MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();

    }
}
