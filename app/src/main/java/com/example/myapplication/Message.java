package com.example.myapplication;

import android.os.AsyncTask;
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

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Message extends AppCompatActivity {
    private EditText messageEditText;
    private LinearLayout messageContainer;
    private ScrollView scrollView;
    private PrintWriter clientWriter; // 클라이언트에서 서버로 메시지를 보내기 위한 PrintWriter
    private Socket socket; // 소켓 변수를 클래스 레벨로 옮김

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
                // 메시지 전송을 백그라운드 스레드에서 수행
                new SendMessageTask().execute();
            }
        });

        // 클라이언트 소켓 초기화 및 서버에 연결
        new InitClientSocketTask().execute();
    }

    // AsyncTask 클래스를 사용하여 백그라운드에서 소켓 초기화 수행
    private class InitClientSocketTask extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                socket = new Socket("10.0.2.2", 12348);
                clientWriter = new PrintWriter(socket.getOutputStream(), true);
                return "Connection successful";
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to initialize client socket: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("Connection successful")) {
                // 서버로부터 메시지를 받는 스레드 시작
                new Thread(() -> {
                    try {
                        Scanner scanner = new Scanner(socket.getInputStream());
                        while (scanner.hasNextLine()) {
                            String message = scanner.nextLine();
                            // 서버에서 받은 메시지를 화면에 표시
                            publishProgress("Server: " + message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                logMessage(result);  // 실패한 경우 로그에 실패 이유 출력
            }
        }
    }

    // AsyncTask를 사용하여 백그라운드 스레드에서 메시지를 보내는 작업 수행
    private class SendMessageTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String message = messageEditText.getText().toString();

            if (clientWriter != null) {
                if (!TextUtils.isEmpty(message)) {
                    // 메시지를 보내기
                    clientWriter.println(message);
                    clientWriter.flush(); // 버퍼를 비워주는 부분 추가
                } else {
                    logMessage("Message is empty");
                }
            } else {
                logMessage("clientWriter is null");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // 메시지를 화면에 표시
            displayMessage(messageEditText.getText().toString());

            // 입력창 비우기
            messageEditText.getText().clear();
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
