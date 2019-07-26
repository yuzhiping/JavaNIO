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
import java.io.InputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hexsmith
 * @version v1.0
 * @since 2018/11/24 20:37
 */
public class ClientHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientHandler.class);

    private static final int MAX_DATA_LENGTH = 1024;

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        LOGGER.info("新客户端接入");
        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                byte[] data = new byte[MAX_DATA_LENGTH];
                int len;
                while ((len = inputStream.read(data)) != -1) {
                    String message = new String(data, 0, len);
                    LOGGER.info("客户端传来消息:{}", message);
                    socket.getOutputStream().write(data);
                }
            }
        } catch (IOException e) {
            LOGGER.error("socket客户端发生异常，ex = {}", e.getMessage());
        }
    }

}
