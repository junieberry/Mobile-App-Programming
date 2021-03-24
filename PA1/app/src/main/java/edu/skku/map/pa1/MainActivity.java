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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button btn3;
    Button btn4;
    Button shuffle;
    GridView grid;
    int n=3; //그리드뷰의 행 개수
    ArrayList<Bitmap> images = new ArrayList<Bitmap>(); //자른 이미지들의 연결 리스트
    Bitmap[] original;
    Bitmap bm;
    Bitmap w3;
    Bitmap w4;
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
        w3=Bitmap.createBitmap(bm,0,0,bm.getWidth()/3, bm.getHeight()/3);
        w3.eraseColor(Color.WHITE); //3x3 흰색 이미지
        w4=Bitmap.createBitmap(bm,0,0,bm.getWidth()/4, bm.getHeight()/4);
        w4.eraseColor(Color.WHITE);//4x4 흰색 이미지
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
                grid.setAdapter(new ImageAdapter(context, images,n));
            }
        });

        //클릭 이벤트
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() { //그리드뷰의 아이템을 클릭했을때
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap w=(n==3)?w3:w4; //
                int i= images.indexOf(w);
                int[]is={i-n,i+n,i-1,i+1};

                if((position==i-n)||(position==i+n)){
                    Collections.swap(images,position,i);
                    grid.setAdapter(new ImageAdapter(context, images, n));//어댑터에 적용
                    if (Arrays.equals(original,images.toArray(new Bitmap[images.size()]))){ //원본과 같은가?
                        Toast.makeText(context,"FINISH!",Toast.LENGTH_LONG).show(); //같다면 토스트 메세지 출력!!
                    }
                }
                if((position==i-1)&&(position/n==i/n)){
                    Collections.swap(images,position,i);
                    grid.setAdapter(new ImageAdapter(context, images, n));//어댑터에 적용
                    if (Arrays.equals(original,images.toArray(new Bitmap[images.size()]))){
                        Toast.makeText(context,"FINISH!",Toast.LENGTH_LONG).show();
                    }
                }
                if((position==i+1)&&(position/n==i/n)){
                    Collections.swap(images,position,i);
                    grid.setAdapter(new ImageAdapter(context, images, n));//어댑터에 적용
                    if (Arrays.equals(original,images.toArray(new Bitmap[images.size()]))){
                        Toast.makeText(context,"FINISH!",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    //이미지의 행 개수를 입력 받아 그리드뷰 이미지 세팅해주는 함수
    public void setgrid(int n){
        images.clear(); //이미지 리스트 초기화
        grid.setNumColumns(n); //행 개수 n으로
        this.n=n;

        //비트맵 자르기
        ////createbitmap : 원본, 시작 x, 시작 y, width, height
        for (int i=0; i<n; i++){
            for (int j=0; j<n; j++){
                images.add(Bitmap.createBitmap(bm,bm.getWidth()/n*j,bm.getHeight()/n*i,bm.getWidth()/n, bm.getHeight()/n));
            }
        }
        if (n==3){images.set(n*n-1,w3);}
        else if(n==4){images.set(n*n-1,w4);}
        original=images.toArray(new Bitmap[images.size()]);
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
        return (position/num)*10+position%num;
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