package edu.skku.map.pa2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LineAdapter extends BaseAdapter {
    Context context;
    int[] line=new int[200];

    LineAdapter(Context context, int[] line){
        this.context=context;
        this.line=line;
    }

    @Override
    public int getCount() {
        return line.length;
    }

    @Override
    public Object getItem(int position) {
        return line[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView==null){ //재사용 가능한 뷰가 없을 경우
            view= new TextView(context);
            view.setLayoutParams(new ViewGroup.LayoutParams(40,40));//view의 가로와 세로 길이 조정
        }
        else{
            view=(TextView) convertView; //재사용 가능한 뷰가 있을 경우 converview 사용
        }
        view.setTextSize(10);
        if (line[position]!=0){
            view.setText(Integer.toString(line[position]));
        }
        return view;
    }
}
