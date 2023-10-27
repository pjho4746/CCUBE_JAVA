package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ex14 extends AppCompatActivity implements View.OnClickListener {
    // 버튼 객체 생성
    Button alertBtn;
    Button listBtn;
    Button dateBtn;
    Button timeBtn;

    // 이벤트 처리를 위해 dialog 객체를 맴버 변수로 선언
    AlertDialog listDialog;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex14);

        // View 객체 획득
        alertBtn=findViewById(R.id.ex14_button1);
        listBtn=findViewById(R.id.ex14_button2);
        dateBtn=findViewById(R.id.ex14_button3);
        timeBtn=findViewById(R.id.ex14_button4);

        // 버튼 이벤트 등록
        alertBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
    }

    // 매개변수의 문자열을 Toast로 띄우는 개발자 함수
    private void showToast(String message){
        Toast toast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Dialog Button 이벤트 처리
    DialogInterface.OnClickListener dialogListener=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(dialog==alertDialog && which == DialogInterface.BUTTON_POSITIVE){
                showToast("예 클릭");
            }
            else if(dialog==listDialog){
                // 목록 dialog의 항목이 선택되었을 때 항목 문자열 획득
                String[] list=getResources().getStringArray(R.array.dialog_array);
                showToast(list[which] + " 선택 하셨습니다.");
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v==alertBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle("알림!");
            builder.setMessage("정말 종료 하시겠습니까?");
            builder.setPositiveButton("예", dialogListener);
            builder.setNegativeButton("아니오", null);

            alertDialog=builder.create();
            alertDialog.show();
        }
        else if(v==listBtn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("알람 벨소리");
            builder.setSingleChoiceItems(R.array.dialog_array, 0, dialogListener);
            builder.setPositiveButton("확인", null);
            builder.setNegativeButton("취소", null);

            listDialog=builder.create();
            listDialog.show();
        }
        else if(v==dateBtn){
            // 현재 날짜로 dialog를 띄우기 위해 날짜를 구함
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    showToast(year+":"+(monthOfYear+1)+":"+dayOfMonth);
                }
            }, year, month, day);
            dateDialog.show();
        }
        else if(v==timeBtn){
            // 현재 시간으로 Dialog를 띄우기 위해 시간을 구함
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timeDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showToast(hourOfDay+":"+minute);
                }
            }, hour, minute, false);
            timeDialog.show();
        }
    }
}