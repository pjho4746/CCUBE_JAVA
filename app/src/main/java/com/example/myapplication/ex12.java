package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ex12 extends AppCompatActivity implements View.OnClickListener {

    EditText txt; // 사용자가 입력한 숫자와 연산자를 나타내는 EditText
    TextView resultText; // 결과를 표시할 TextView
    Button button[] = new Button[16]; // 계산기 버튼 배열
    String operator = ""; // 현재 선택된 연산자를 저장하는 변수
    int num1; // 첫 번째 피연산자
    int num2; // 두 번째 피연산자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex12);

        init(); // 레이아웃 요소 초기화
        initListener(); // 버튼에 OnClickListener 설정
    }

    public void init() {
        // 레이아웃에서 버튼과 EditText, TextView를 찾아와 변수에 할당
        button[0] = findViewById(R.id.ex12_0);
        button[1] = findViewById(R.id.ex12_1);
        button[2] = findViewById(R.id.ex12_2);
        button[3] = findViewById(R.id.ex12_3);
        button[4] = findViewById(R.id.ex12_4);
        button[5] = findViewById(R.id.ex12_5);
        button[6] = findViewById(R.id.ex12_6);
        button[7] = findViewById(R.id.ex12_7);
        button[8] = findViewById(R.id.ex12_8);
        button[9] = findViewById(R.id.ex12_9);
        button[10] = findViewById(R.id.ex12_sum);
        button[11] = findViewById(R.id.ex12_sub);
        button[12] = findViewById(R.id.ex12_mul);
        button[13] = findViewById(R.id.ex12_dev);
        button[14] = findViewById(R.id.ex12_equal);
        button[15] = findViewById(R.id.ex12_del);

        txt = findViewById(R.id.ex12edittext1);
        resultText = findViewById(R.id.ex12_result_text);
    }

    public void initListener() {
        // 버튼 배열에 OnClickListener 설정
        for (int i = 0; i <= 15; i++) {
            button[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // 숫자 버튼을 누르면 해당 숫자를 EditText에 추가
        for (int i = 0; i <= 13; i++) {
            if (button[i] == v) {
                txt.append(button[i].getText().toString());
            }
        }

        if (button[14] == v) {
            String expression = txt.getText().toString();
            if (expression.contains("+")) {
                String[] parts = expression.split("\\+");
                if (parts.length == 2) {
                    num1 = Integer.parseInt(parts[0]);
                    num2 = Integer.parseInt(parts[1]);
                    int sum = num1 + num2;
                    resultText.setText(Integer.toString(sum));
                }
            } else if (expression.contains("-")) {
                String[] parts = expression.split("-");
                if (parts.length == 2) {
                    num1 = Integer.parseInt(parts[0]);
                    num2 = Integer.parseInt(parts[1]);
                    int diff = num1 - num2;
                    resultText.setText(Integer.toString(diff));
                }
            } else if (expression.contains("x")) {
                String[] parts = expression.split("x");
                if (parts.length == 2) {
                    num1 = Integer.parseInt(parts[0]);
                    num2 = Integer.parseInt(parts[1]);
                    int product = num1 * num2;
                    resultText.setText(Integer.toString(product));
                }
            } else if (expression.contains("/")) {
                String[] parts = expression.split("/");
                if (parts.length == 2) {
                    num1 = Integer.parseInt(parts[0]);
                    num2 = Integer.parseInt(parts[1]);
                    if (num2 != 0) {
                        int quotient = num1 / num2;
                        resultText.setText(Integer.toString(quotient));
                    } else {
                        resultText.setText("Error: Division by zero");
                    }
                }
            }
        }

        if (button[15] == v) {
            txt.setText("");
            resultText.setText(""); // DEL 버튼을 누르면 결과 텍스트도 초기화
        }
    }
}

