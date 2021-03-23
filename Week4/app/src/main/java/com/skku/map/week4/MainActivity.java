package com.skku.map.week4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list=(ListView)findViewById(R.id.list);
        TextView text=(TextView)findViewById(R.id.text);

        TextView first=(TextView)findViewById(R.id.first);
        TextView second=(TextView)findViewById(R.id.second);
        TextView third=(TextView)findViewById(R.id.third);

        ArrayList data1=new ArrayList();
        ArrayList data2=new ArrayList();
        ArrayList<String> data3=new ArrayList<String>();

        for(int i=0; i<11; i++){
            data1.add(i);
            if (i<10){
                data2.add(2<<i);}
        }

        data3.add("2019312326");
        data3.add("Junyoung Kim");
        data3.add("Department of Computer Education");
        data3.add("College of Education");
        data3.add("Sungkyunkwan University");

        ArrayAdapter adapter1=new ArrayAdapter(this, android.R.layout.simple_list_item_1, data1);
        ArrayAdapter adapter2=new ArrayAdapter(this, android.R.layout.simple_list_item_1, data2);
        ArrayAdapter adapter3=new ArrayAdapter(this, android.R.layout.simple_list_item_1, data3);

        list.setAdapter(adapter1);
        text.setText("First");

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapter1);
                text.setText("First");
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapter2);
                text.setText("Second");
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setAdapter(adapter3);
                text.setText("Third");
            }
        });
    }
}