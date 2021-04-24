package edu.skku.map.pa2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.Arrays;

public class BoardAdapter extends BaseAdapter {
    Context context;
    Integer[] board;

    BoardAdapter(Context context, Integer[] board){
        this.board=board;
        this.context=context;
    }

    @Override
    public int getCount() {
        return board.length;
    }

    @Override
    public Object getItem(int position) {
        return board[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap square= Bitmap.createBitmap(40,40, Bitmap.Config.valueOf("ARGB_8888"));
        if(board[position]==0){square.eraseColor(Color.WHITE);}
        else{square.eraseColor(Color.BLACK);}


        ImageView view;
        if (convertView==null){
            view=new ImageView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(40,40));
            view.setScaleType(ImageView.ScaleType.CENTER); //view안의 이미지의 scaletype 조정

        }
        else{
            view=(ImageView) convertView;
        }
        view.setImageBitmap(square);
        return view;
    }
}
