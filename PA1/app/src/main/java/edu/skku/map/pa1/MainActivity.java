package edu.skku.map.pa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button btn3;
    Button btn4;
    Button shuffle;
    GridView grid;
    int n=3;
    ArrayList<Bitmap> images = new ArrayList<Bitmap>(); //자른 이미지들의 연결 리스트
    Bitmap bm;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //메인 화면에 있는 구성요소들 모두 가져오기
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        shuffle = (Button)findViewById(R.id.shuffle);
        grid=(GridView)findViewById(R.id.grid);
        this.bm=BitmapFactory.decodeResource(getResources(), R.drawable.doggy); //doggy 이미지를 비트맵으로 받아옴
        this.context=this;


        //아무 버튼도 누르기 전 초기 3x3 상태
        setgrid(3);

        //3x3 버튼
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgrid(3);
            }});

        //4x4 버튼
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setgrid(4);
            }
        });

        //SHUFFLE 버튼
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.shuffle(images);
                grid.setAdapter(new ImageAdapter(context, images, n));
            }
        });

    }
    //이미지의 행 개수를 입력 받아 그리드뷰 이미지 세팅해주는 함수
    public void setgrid(int n){
        images.clear(); //이미지 리스트 초기화
        grid.setNumColumns(n); //행 개수 n으로

        //비트맵 자르기
        ////createbitmap : 원본, 시작 x, 시작 y, width, height
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                images.add(Bitmap.createBitmap(bm,bm.getWidth()/n*j,bm.getHeight()/n*i,bm.getWidth()/n, bm.getHeight()/n));
            }
        }
        images.get(n*n-1).eraseColor(Color.WHITE); //마지막 칸은 흰색으로 만들기

        //TODO
        //다 완료했을때 마지막으로 images shuffle하고 넣기

        grid.setAdapter(new ImageAdapter(context, images, n));//어댑터에 적용
    }

}


//그리드뷰에 이미지 넣기 위한 커스텀 어댑터 생성
class ImageAdapter extends BaseAdapter{
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    Context context;
    int num;

    ImageAdapter(Context context, ArrayList<Bitmap> images, int num){
        this.context=context;
        this.images=images;
        this.num=num;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;

        if (convertView==null){ //재사용 가능한 뷰가 없을 경우
            view= new ImageView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(1200/num,1200/num));//view의 가로와 세로 길이 조정
            view.setScaleType(ImageView.ScaleType.CENTER); //view안의 이미지의 scaletype 조정
            view.setPadding(10,20,10,20); //패딩 조정
        }
        else{
            view=(ImageView) convertView; //재사용 가능한 뷰가 있을 경우 converview 사용
        }

        view.setImageBitmap(images.get(position));
        return view;

    }
}