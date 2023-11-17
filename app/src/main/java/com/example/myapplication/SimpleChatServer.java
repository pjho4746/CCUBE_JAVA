package com.example.myapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // 서버 소켓을 포트 12345로 생성

            System.out.println("Server is waiting for a client...");

            Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 대기

            System.out.println("Client connected");

            // 클라이언트로부터 메시지를 받는 스레드 시작
            new Thread(() -> {
                try {
                    Scanner scanner = new Scanner(clientSocket.getInputStream());
                    while (scanner.hasNextLine()) {
                        String message = scanner.nextLine();
                        System.out.println("Client: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // 서버에서 메시지를 전송하는 부분
            PrintWriter serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            // 서버에서 콘솔 입력을 받아서 클라이언트에게 전송
            Scanner consoleScanner = new Scanner(System.in);
            while (true) {
                String serverMessage = consoleScanner.nextLine();
                serverWriter.println(serverMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

