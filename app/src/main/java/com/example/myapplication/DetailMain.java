package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailMain extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> datas; // ArrayList 데이터 형식 수정
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_main);

        listView = findViewById(R.id.main_list);
        listView.setOnItemClickListener(this);

        // 항목 구성 데이터 획득
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select location from tb_data where category='0'", null); // "whrer" 대신 "where"
        datas = new ArrayList<>();
        while (cursor.moveToNext()) {
            datas.add(cursor.getString(0));
        }
        db.close();

        // Adapter 적용으로 ListView 구성
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        adapter.addAll(datas); // 데이터를 어댑터에 추가
    }

    // 항목 선택 이벤트
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView textView = (TextView) view;
        category = textView.getText().toString();
        // Extra 데이터를 넘기면서 Intent 발생
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("category", category);
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            // DetailActivity가 넘긴 데이터 획득
            String location = data.getStringExtra("location");
            // 결과값 Toast로 출력
            Toast toast = Toast.makeText(this, category + ":" + location, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
