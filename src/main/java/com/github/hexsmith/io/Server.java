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
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/11/23 20:15
 */
public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            LOGGER.error("服务器端启动失败，ex={}", e.getMessage());
        }
    }

    public void start() {
        new Thread(() -> {
            doStart();
        }).start();

    }

    private void doStart() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
