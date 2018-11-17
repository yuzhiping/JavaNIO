package com.github.hexsmith.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 同步阻塞方式的TimeServerHandler
 * Created by yuzp17311 on 2016/3/27.
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;


    public TimeServerHandler(Socket socket){
        this.socket=socket;
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


        BufferedReader in=null;
        PrintWriter out=null;

        try {
            in=new BufferedReader(new InputStreamReader(
                    this.socket.getInputStream()
            ));
            out=new PrintWriter(this.socket.getOutputStream(),true);
            String currentTime=null;
            String body=null;
            while (true){
                body=in.readLine();
                if (body==null){
                    break;
                }
                System.out.println("The time server receive order:"+body);
                currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(
                        System.currentTimeMillis()
                ).toString():"BAD ORDER";
                out.println(currentTime);
            }
        }catch (Exception e){
            if(in!=null){
                try{
                    in.close();
                }catch (IOException el){
                    el.printStackTrace();
                }
            }
            if(out!=null){
                out.close();
                out=null;
            }
            if(this.socket!=null){
                try {
                    this.socket.close();
                }catch (IOException el){
                    el.printStackTrace();
                }
                this.socket=null;

            }
        }

    }
}
