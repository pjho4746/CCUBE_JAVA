package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ArrayAdapter_ListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] arrayDatas;
    ListView arrayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter_list_view);

        arrayView = findViewById(R.id.custom_main_listview);
        arrayView.setOnItemClickListener(this);

        arrayDatas = getResources().getStringArray(R.array.location); // Fix the resource reference
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayDatas);
        arrayView.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, arrayDatas[position], Toast.LENGTH_SHORT).show();
    }
}
