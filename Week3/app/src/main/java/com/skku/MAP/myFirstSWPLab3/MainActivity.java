package com.skku.MAP.myFirstSWPLab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView)findViewById(R.id.textView);
        Button btn1 = (Button)findViewById(R.id.button_top);
        btn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String id=(text.getText()=="2019312326")? "김준영":"2019312326";
                text.setText(id);
            }
        });

        final ImageView image=(ImageView)findViewById(R.id.image);
        Button btn2 = (Button)findViewById(R.id.button_bottom);
        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (i==0){

                    image.setImageResource(R.drawable.kotlin);
                    i++; }
                else if (i==1){

                    image.setImageResource(R.drawable.flutter);
                    i++;}
                else{

                    image.setImageResource(R.drawable.java);
                    i=0;
                    }
            }
        });

    }
}