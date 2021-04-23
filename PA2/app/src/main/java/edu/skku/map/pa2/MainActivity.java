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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static final int GALLERY=0;
    TextView search;
    Button searchbtn;
    Button gallerybtn;
    ImageView image;
    Bitmap bm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search=findViewById(R.id.search);
        searchbtn=findViewById(R.id.searchbtn);
        gallerybtn=findViewById(R.id.gallerybtn);

        image=findViewById(R.id.image);



        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientId="EuYpZATCiT4LHkoG8c8Y";
                String clientSecret="nDiu2p_HYe";
                OkHttpClient client= new OkHttpClient();

                HttpUrl.Builder urlBuilder=HttpUrl.parse("https://openapi.naver.com/v1/search/image").newBuilder();
                try {
                    urlBuilder.addQueryParameter("query",URLEncoder.encode(search.getText().toString(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                String apiURL=urlBuilder.build().toString();


                Request req=new Request.Builder()
                        .addHeader("X-Naver-Client-Id", clientId)
                        .addHeader("X-Naver-Client-Secret", clientSecret)
                        .url(apiURL)
                        .build();

                client.newCall(req).enqueue(new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        search.setText("HTTP ERROR");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        final String Response =response.body().string();
                        Gson gson=new GsonBuilder().create();
                        ImageList items=gson.fromJson(Response, ImageList.class);
                        URL url=new URL(items.getItems().get(0).getThumbnail().toString());

                        Thread mThread=new Thread(){
                            @Override
                            public void run() {
                                try {
                                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();

                                    InputStream is=conn.getInputStream();
                                    bm= BitmapFactory.decodeStream(is);


                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        mThread.start();
                        try{
                            mThread.join();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                image.setImageBitmap(bm);
                            }
                        });
                    }
                });
            }
        });


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

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == GALLERY) && (resultCode == RESULT_OK)) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());

                bm= BitmapFactory.decodeStream(in);
                in.close();
                image.setImageBitmap(bm);

            } catch (Exception e) {

            }
        } else if (resultCode == RESULT_CANCELED) {
            // 이미지 선택 취소
        }
    }


}