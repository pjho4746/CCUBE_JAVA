package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player; // MediaPlayer 객체를 필드로 선언합니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // MediaPlayer 객체를 초기화하고 오디오 파일을 재생합니다.
        player = MediaPlayer.create(this, R.raw.kakaotalk);
        player.start();
    }
}
