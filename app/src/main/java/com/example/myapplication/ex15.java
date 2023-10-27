package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ex15 extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView bellTextView;
    TextView labelTextView;
    CheckBox repeatCheckView;
    CheckBox vibrateCheckView;
    Switch switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex15);
        bellTextView = findViewById(R.id.bell_name);
        labelTextView = findViewById(R.id.label);
        repeatCheckView = findViewById(R.id.repeatCheck);
        vibrateCheckView = findViewById(R.id.vibrate);
        switchView = findViewById(R.id.onOff);

        bellTextView.setOnClickListener(this);
        labelTextView.setOnClickListener(this);

        // 각 CheckBox와 Switch에 체크 상태 변경 리스너를 설정
        repeatCheckView.setOnCheckedChangeListener(this);
        vibrateCheckView.setOnCheckedChangeListener(this);
        switchView.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bellTextView) {
            showToast("Bell text click event");
        } else if (v == labelTextView) {
            showToast("Label text click event");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == repeatCheckView) {
            showToast("Repeat text click event, isChecked: " + isChecked);
        } else if (buttonView == vibrateCheckView) {
            showToast("Vibrate text click event, isChecked: " + isChecked);
        } else if (buttonView == switchView) {
            showToast("Switch text click event, isChecked: " + isChecked);
        }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}