package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Intent2Main extends AppCompatActivity implements View.OnClickListener {

    Button contactsBtn;      // 주소록 앱 연동 버튼
    TextView resultView;     // 결과를 표시하는 텍스트 뷰

    Button cameraDataBtn;
    ImageView resultImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent2_main);

        // XML 레이아웃에서 버튼과 텍스트 뷰, 이미지 뷰를 찾아옵니다.
        contactsBtn = findViewById(R.id.btn_contacts);
        resultView = findViewById(R.id.resultView);
        cameraDataBtn = findViewById(R.id.btn_camera_data);
        resultImageView = findViewById(R.id.resultImageView);

        // 버튼에 클릭 리스너를 등록합니다.
        contactsBtn.setOnClickListener(this);
        cameraDataBtn.setOnClickListener(this);  // 카메라 앱 버튼에 클릭 리스너를 추가합니다.
    }

    @Override
    public void onClick(View v) {
        if (v == contactsBtn) {
            // 주소록 앱을 열기 위한 Intent를 생성합니다.
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

            // startActivityForResult()를 사용하여 액티비티를 시작하고 결과를 기다립니다.
            startActivityForResult(intent, 10);
        } else if (v == cameraDataBtn) {
            // 카메라 앱을 열기 위한 Intent를 생성합니다.
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // startActivityForResult()를 사용하여 카메라 앱을 실행하고 촬영 결과를 기다립니다.
            startActivityForResult(intent, 30);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            // 주소록에서 선택한 연락처 정보의 데이터 URI를 가져와서 표시합니다.
            String result = data.getDataString();
            resultView.setText(result);
        } else if (requestCode == 30 && resultCode == RESULT_OK) {
            // 카메라 앱에서 촬영한 사진을 가져와서 이미지 뷰에 표시합니다.
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            resultImageView.setImageBitmap(bitmap);
        }
    }
}
