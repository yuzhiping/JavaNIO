package com.hexsmith.netty.nio;

/**
 * NIO时间服务器客户端
 *
 * @author hexsmith
 * @date 2016/3/27
 */
public class TimeClient {

    public static void main(String[]args){
        int port=8080;
        if(args!=null&&args.length>0) {
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){

            }
        }
        new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();


    }
}
