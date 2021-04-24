package edu.skku.map.pa2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static final int GALLERY=0;
    ArrayList<Integer> nonogram;
    Integer[] board;
    int[] row;
    int[] column;
    EditText search;
    Button searchbtn;
    Button gallerybtn;
    GridView grid;
    GridView row_grid;
    GridView col_grid;
    Bitmap bm;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context=this;
        search=findViewById(R.id.search);
        searchbtn=findViewById(R.id.searchbtn);
        gallerybtn=findViewById(R.id.gallerybtn);
        grid=findViewById(R.id.grid);
        row_grid=findViewById(R.id.row_grid);
        col_grid=findViewById(R.id.col_grid);
        board= new Integer[400];
        row= new int[200];
        column= new int[400];

        Arrays.fill(board,0);
        Arrays.fill(row,0);
        Arrays.fill(board,0);

        grid.setAdapter(new BoardAdapter(context,board));

        // search 버튼 눌렀을때
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //보드 초기화
                Arrays.fill(board,0);
                //naver api start
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

                // 요청 보내기
                client.newCall(req).enqueue(new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        search.setText("HTTP ERROR");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        //Gson 사용해 첫번째로 검색되는 이미지의 주소 가져오기
                        final String Response =response.body().string();
                        Gson gson=new GsonBuilder().create();
                        ImageList items=gson.fromJson(Response, ImageList.class);
                        URL url=new URL(items.getItems().get(0).getThumbnail().toString());

                        // 쓰레드를 사용해 이미지 주소 기반으로 bitmap 가져오기
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
                            public void run() {// 가져온 bitmap 기반으로 노노그램 설정
                                nonogram=BitmapProcess(bm);
                                row=SetRow(nonogram);
                                column=Setcolumn(nonogram);
                                row_grid.setAdapter(new LineAdapter(context, row));
                                col_grid.setAdapter(new LineAdapter(context, column));

                            }
                        });

                    }
                });
            }
        });

        //Gallery 버튼
        gallerybtn.setOnClickListener(new View.OnClickListener() {

            // 겔러리로 intent 보내기
            @Override
            public void onClick(View v) {
                //보드 초기화
                Arrays.fill(board,0);
                Intent galleryIntent=new Intent()
                        .setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(galleryIntent, GALLERY);
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i,j=0;
                if (nonogram!=null){
                    if (nonogram.get(position)==1){
                        board[position]=1;
                        grid.setAdapter(new BoardAdapter(context,board));

                    }
                    else{
                        Toast.makeText(context,"WRONG!",Toast.LENGTH_SHORT).show();
                        Arrays.fill(board,0);
                        grid.setAdapter(new BoardAdapter(context,board));
                    }


                    //정답 확인용
                    if (Arrays.equals(board, nonogram.toArray(new Integer[nonogram.size()]))){
                        Toast.makeText(context,"FINISH!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == GALLERY) && (resultCode == RESULT_OK)) {
            try {
                // inputstream으로 bitmap 가져오기
                InputStream in = getContentResolver().openInputStream(data.getData());
                bm= BitmapFactory.decodeStream(in);
                in.close();
                nonogram=BitmapProcess(bm);
                row=SetRow(nonogram);
                column=Setcolumn(nonogram);
                row_grid.setAdapter(new LineAdapter(context, row));
                col_grid.setAdapter(new LineAdapter(context, column));

            } catch (Exception e) {

            }
        } else if (resultCode == RESULT_CANCELED) {
            // 이미지 선택 취소
        }
    }

    ArrayList<Integer> BitmapProcess(Bitmap bm){
        ArrayList<Integer> nonogram=new ArrayList<Integer>();

        // 1. Resizeing image into square
        int scale= Math.min(bm.getHeight(), bm.getWidth());
        bm=Bitmap.createBitmap(bm, 0,0,scale,scale);

        //2. Convert image into black and white photos
        for (int x = 0; x<scale; x++){
            for (int y=0; y<scale; y++){
                int R,G,B,A;
                int pixel;
                int gray;
                pixel=bm.getPixel(x,y);
                A = Color.alpha(pixel);
                R=Color.red(pixel);
                G=Color.green(pixel);
                B=Color.blue(pixel);
                gray=((0.2126*R+0.7152*G+0.0722*B)>128)?255:0;
                bm.setPixel(x,y,Color.argb(A,gray,gray,gray));
            }
            
        }
            
        // 3-1.Split images into 20 * 20 pieces
        scale=scale/20;
        for (int i=0; i<20; i++){
            for (int j=0; j<20; j++){
                Bitmap tbm = Bitmap.createBitmap(bm,scale*j,scale*i,scale, scale);
                int R=0,G=0,B=0;
                int pixel;
                double gray;
                // 3-2. determine the block’s color
                for (int x=0; x<scale; x++){
                    for (int y=0; y<scale; y++){
                        pixel=tbm.getPixel(x,y);
                        R=R+Color.red(pixel);
                        G=G+Color.green(pixel);
                        B=B+Color.blue(pixel);
                    }
                }
                // 3-3 average grayscale value
                gray=(0.2126*R+0.7152*G+0.0722*B)/(scale*scale);
                // 흑이면 1, 백이면 0
                if (gray>128){nonogram.add(0);}
                else{nonogram.add(1);}
            }
        }
        return nonogram;
    }

    int[] SetRow(ArrayList nonogram){
        int[] Row=new int [200];
        Arrays.fill(Row,0);

        // line set
        for (int i=0; i<20; i++){
            ArrayList<Integer> row = new ArrayList<Integer>();
            int sum=0;
            for (int j=0; j<20; j++){
                if (nonogram.get(20 * i + j).toString().equals("1")){
                    sum++;
                    if (j==19){
                        row.add(sum);
                    }
                }
                else if (sum!=0){
                    row.add(sum);
                    sum=0;
                }
            }

            for (int j=0; j<row.size(); j++){
                Row[10*i+9-j]=row.get(j);
            }
        }
        return Row;
    }

    int[] Setcolumn(ArrayList<Integer> nonogram){
        int[] Column=new int [200];
        Arrays.fill(Column,0);

        // line set
        for (int i=0; i<20; i++){
            ArrayList<Integer> column = new ArrayList<Integer>();
            int sum=0;
            for (int j=0; j<20; j++){
                if (nonogram.get(20 * j + i).toString().equals("1")){
                    sum++;
                    if (j==19){
                        column.add(sum);
                    }
                }
                else if (sum!=0){
                    column.add(sum);
                    sum=0;
                }
            }

            for (int j=0; j<column.size(); j++){
                Column[20*(9-j)+i]=column.get(j);
            }
        }

        return Column;
    }
}