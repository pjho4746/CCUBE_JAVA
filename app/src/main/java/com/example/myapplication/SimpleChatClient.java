package com.example.myapplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // 로컬 서버의 포트 12345에 연결

            // 클라이언트에서 메시지를 전송하는 부분
            PrintWriter clientWriter = new PrintWriter(socket.getOutputStream(), true);

            // 클라이언트에서 콘솔 입력을 받아서 서버에게 전송
            Scanner consoleScanner = new Scanner(System.in);
            while (true) {
                String clientMessage = consoleScanner.nextLine();
                clientWriter.println(clientMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

