package edu.skku.map.pa2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    static final int GALLERY=0;
    TextView search;
    Button searchbtn;
    Button gallerybtn;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search=findViewById(R.id.search);
        searchbtn=findViewById(R.id.searchbtn);
        gallerybtn=findViewById(R.id.gallerybtn);

        image=findViewById(R.id.image);

//        searchbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // build.gradle과 manifest에 okhttp와 INTERNET PERMISSION 수정해둠
//                String key=search.getText().toString();
//                OkHttpClient client= new OkHttpClient();
//            }
//        });

        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent()
                        .setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 1. 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == GALLERY) && (resultCode == RESULT_OK)) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());

                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                image.setImageBitmap(img);

            } catch (Exception e) {

            }
        } else if (resultCode == RESULT_CANCELED) {
            // 이미지 선택 취소
        }
    }
}