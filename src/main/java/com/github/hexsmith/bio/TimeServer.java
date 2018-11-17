package com.github.hexsmith.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞方式的TimeServer
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
            while (true){
                socket=serverSocket.accept();
                new Thread(new TimeServerHandler(socket)).start();
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
