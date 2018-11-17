package com.github.hexsmith.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 异步的时间服务器处理器
 * Created by yuzp17311 on 2016/3/28.
 */
public class AsyncTimeServerHandler implements Runnable {

    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;
    private int port;

    public AsyncTimeServerHandler(int port){
        this.port=port;
        try{
            asynchronousServerSocketChannel=AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port:"+port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {


        latch=new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }


    public  void doAccept(){
        asynchronousServerSocketChannel.accept(this,new AcceptCompletionHandler());
    }
}
