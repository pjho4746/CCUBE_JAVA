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

public class Message extends AppCompatActivity {
    private EditText messageEditText;
    private LinearLayout messageContainer;
    private ScrollView scrollView;

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
    }

    private void sendMessage() {
        String message = messageEditText.getText().toString();

        if (!TextUtils.isEmpty(message)) {
            // 메시지를 보냄
            logMessage("Sent: " + message);

            // 메시지를 화면에 표시
            displayMessage(message);

            // 입력창 비우기
            messageEditText.getText().clear();
        } else {
            logMessage("Message is empty");
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
