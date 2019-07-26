/*
 *  Copyright(C) 2016-2018 The hexsmith Authors
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.github.hexsmith.io;

import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/11/24 20:51
 */
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8080;

    private static final int SLEEP_TIME = 5000;

    public static void main(String[] args) throws IOException {

        final Socket socket = new Socket(HOST, PORT);

        new Thread(() -> {
            LOGGER.info("客户端启动成功");
            while (true) {
                try {
                    String message = "hello,world " + LocalDate.now();
                    LOGGER.info("客户端发送数据:{}", message);
                    socket.getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    LOGGER.error("客户端发送数据发生异常,message = {}", e.getMessage());
                }
                sleep();
            }
        }).start();

    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
