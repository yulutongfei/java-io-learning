package com.sunxu.bio;

import java.io.*;
import java.net.Socket;

/**
 * @author 孙许
 * @version 1.0
 * @date 2020/9/23 07:12
 */
public class Client {

    public static void main(String[] args) {
        final String QUIT = "quit";
        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        Socket socket;

        try {
            // 创建socket
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建IO流
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 // 等待用户输入信息
                 BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

                while (true) {
                    String input = consoleReader.readLine();

                    // 发送给服务器
                    writer.write(input + "\n");
                    writer.flush();

                    // 读取服务器返回的消息
                    String msg = reader.readLine();
                    if (msg != null) {
                        System.out.println(msg);
                    } else {
                        System.out.println("您已退出");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
