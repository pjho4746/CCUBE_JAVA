package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Message extends AppCompatActivity {
    private EditText messageEditText;
    private LinearLayout messageContainer;
    private ScrollView scrollView;
    private PrintWriter clientWriter; // 클라이언트에서 서버로 메시지를 보내기 위한 PrintWriter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageEditText = findViewById(R.id.messageEditText);
        Button sendButton = findViewById(R.id.sendButton);
        messageContainer = findViewById(R.id.messageContainer);
        scrollView = findViewById(R.id.scrollView);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // 클라이언트 소켓 초기화 및 서버에 연결
        initClientSocket();
    }

    private void initClientSocket() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("192.168.10.1", 12345);
                clientWriter = new PrintWriter(socket.getOutputStream(), true);

                // 서버로부터 메시지를 받는 스레드 시작
                Scanner scanner = new Scanner(socket.getInputStream());
                while (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    // 서버에서 받은 메시지를 화면에 표시
                    runOnUiThread(() -> displayMessage("Server: " + message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString();

        if (clientWriter != null) {
            if (!TextUtils.isEmpty(message)) {
                // 메시지를 보내기
                clientWriter.println(message);
                clientWriter.flush(); // 버퍼를 비워주는 부분 추가

                // 메시지를 화면에 표시
                displayMessage("Sent: " + message);

                // 입력창 비우기
                messageEditText.getText().clear();
            } else {
                logMessage("Message is empty");
            }
        } else {
            logMessage("clientWriter is null");
        }
    }

    private void logMessage(String message) {
        Log.d("MessengerApp", message);
    }

    private void displayMessage(String message) {
        // 새로운 메시지를 텍스트 뷰로 만들어서 화면에 추가
        TextView textView = new TextView(this);
        textView.setText(message);

        // 말풍선 배경 이미지 적용
        textView.setBackgroundResource(R.drawable.bubble);
        textView.setTextColor(getResources().getColor(android.R.color.white));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // 오른쪽 정렬
        layoutParams.gravity = Gravity.END;

        textView.setLayoutParams(layoutParams);

        // 메시지 컨테이너에 추가
        messageContainer.addView(textView);

        // 스크롤뷰를 가장 아래로 스크롤
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}
