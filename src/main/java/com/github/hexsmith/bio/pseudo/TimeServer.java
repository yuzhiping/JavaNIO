package com.github.hexsmith.bio.pseudo;

import com.github.hexsmith.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步方式的TimeServer
 * Created by yuzp17311 on 2016/3/27.
 */
public class TimeServer {

    /**
     * @param args
     *          参数表
     * @throws IOException
     */
    public static void main(String []args)throws IOException{
        int port=8080;
        if(args!=null&&args.length>0){
            try{
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                //采用默认值
            }
        }
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            System.out.println("The time Server start in port:"+port);
            Socket socket=null;
            TimeServerHandlerExecutePool singleExecutor=new TimeServerHandlerExecutePool(50,10000);//创建I/O任务线程池
            while (true){
                socket=serverSocket.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        }finally {
            if(serverSocket!=null){
                System.out.println("The time server close");
                serverSocket.close();
                serverSocket=null;
            }
        }

    }


}
