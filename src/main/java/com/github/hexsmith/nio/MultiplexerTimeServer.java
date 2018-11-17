package com.hexsmith.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器
 * Created by yuzp17311 on 2016/3/28.
 */
public class MultiplexerTimeServer  implements Runnable{

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    /*
     *初始化多路复用器，绑定监听端口
     * @param port
     *          端口
     */
    public MultiplexerTimeServer(int port){

        try{
            selector=Selector.open();
            serverSocketChannel=ServerSocketChannel.open();
            //设置为异步非阻塞方式
            serverSocketChannel.configureBlocking(false);
            //backlog:请求队列的最大连接数
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            //将serverSocketChannel注册到selector,监听SelectionKey.OP_ACCEPT操作位
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port:"+port);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

    }


    public void stop(){
        this.stop=true;
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

        while (!stop){
            try {
                //休眠时间设置为1s。无论是否有读写等事件发生都会被唤醒
                selector.select(1000);
                Set<SelectionKey> selectionKeys=selector.selectedKeys();
                Iterator<SelectionKey> it=selectionKeys.iterator();
                SelectionKey key=null;
                while (it.hasNext()){
                    key=it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    }catch (Exception e){
                        if(key!=null){
                            key.cancel();
                            if(key.channel()!=null){
                                key.channel().close();
                            }
                        }
                    }
                }
            }catch (Throwable t){
                t.printStackTrace();
            }
        }

        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector!=null){
            try {
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }


    private void handleInput(SelectionKey key)throws IOException{

        if(key.isValid()){
            //处理新接入的消息
            if (key.isAcceptable()){
                //Accept new connection

                ServerSocketChannel ssc= (ServerSocketChannel) key.channel();
                SocketChannel sc=ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);
            }

            if (key.isReadable()){
                //Read the data
                SocketChannel sc= (SocketChannel) key.channel();
                ByteBuffer readBuffer=ByteBuffer.allocate(1024);
                int readBytes=sc.read(readBuffer);
                //读到了字节，对字节进行编解码
                if (readBytes>0){
                    //将缓冲区的limit设置为position，position设置为0，用于后续对缓冲区的读写操作
                    readBuffer.flip();
                    //根据缓冲区可读字节个数创建数组
                    byte []bytes=new byte[readBuffer.remaining()];
                    //将缓冲区可读字节数组复制到新创建的字节数组中
                    readBuffer.get(bytes);
                    String body=new String(bytes,"UTF-8");
                    System.out.println("The time server receive order:"+body);
                    String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?
                            new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    doWrite(sc,currentTime);
                }else if(readBytes<0){//链路已经关闭
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                }else ;//读到0字节，忽略
            }
        }

    }

    /**
     * 将应答消息异步发送给客户端
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel,String response) throws IOException{
        if(response!=null&&response.trim().length()>0){
            //将字符串编码成字节数组
            byte [] bytes=response.getBytes();
            //根据字节数组容量创建ByteBuffer
            ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
            //将字节数组放到缓冲区
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }

}
