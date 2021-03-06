package com.sunxu.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 孙许
 * @version 1.0
 * @date 2020/9/23 07:13
 */
public class Server {

    final static String QUIT = "quit";
    final static int DEFAULT_PORT = 8888;

    public static void main(String[] args) {
        ServerSocket serverSocket;

        try {
            // 绑定监听端口
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器,监听端口:" + DEFAULT_PORT);
            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        handle(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handle(Socket socket) throws IOException {
        System.out.println("客户端[" + socket.getPort() + "]已连接");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            String msg;
            // 读取客户端发送的消息
            while ((msg = reader.readLine()) != null) {

                // 查看客户端是否退出
                if (QUIT.equals(msg)) {
                    System.out.println("客户端[" + socket.getPort() + "]已断开连接");
                    break;
                }

                System.out.println("客户端[" + socket.getPort() + "]: " + msg);

                // 回复客户发送的消息
                writer.write("服务器: " + msg + "\n");
                writer.flush();
            }
        }
    }
}
