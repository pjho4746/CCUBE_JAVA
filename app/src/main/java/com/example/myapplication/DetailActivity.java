package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //자신을 실행시킨 Intent 획득
        Intent intent = getIntent();
        //DetailMain에서 넘어온 데이터 획득
        String category = intent.getStringExtra("category");

        listView = findViewById(R.id.detail_list);
        listView.setOnItemClickListener(this);

        //항목 구성
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select location from tb_data where category=?", new String[]{category});

        datas = new ArrayList<>();
        while (cursor.moveToNext()) {
            datas.add(cursor.getString(0));
        }
        db.close();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
        listView.setAdapter(adapter);
    }

    // AdapterView.OnItemClickListener 인터페이스의 onItemClick 메서드를 오버라이드
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 클릭 이벤트 처리 로직을 추가하면 됩니다.
    }
}
