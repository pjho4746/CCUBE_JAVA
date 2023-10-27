package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Vibrator;
import android.media.ToneGenerator;

public class ex13 extends AppCompatActivity implements View.OnClickListener {

    Button vibrationBtn;
    Button beepBtn;
    Button customBtn;

    private MediaPlayer player; // MediaPlayer 객체를 필드로 선언합니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex13);

        vibrationBtn = findViewById(R.id.ex13button1);
        beepBtn = findViewById(R.id.ex13button2);
        customBtn = findViewById(R.id.ex13button3);

        vibrationBtn.setOnClickListener(this);
        beepBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == vibrationBtn) {
            // 진동 버튼을 클릭한 경우
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(1000); // 1초간 진동
            }
        } else if (v == beepBtn) {
            // BEEP 버튼을 클릭한 경우
            if (player != null) {
                player.release(); // 이전에 초기화한 MediaPlayer 리소스를 해제합니다.
            }
            player = MediaPlayer.create(this, R.raw.kakaotalk);
            player.start();
        } else if (v == customBtn) {
            // CUSTOM SOUND 버튼을 클릭한 경우
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150); // Beep 소리 재생
        }
    }
}

