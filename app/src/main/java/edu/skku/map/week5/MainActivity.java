package edu.skku.map.week5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edit_id=findViewById(R.id.id);
        EditText edit_pw=findViewById(R.id.pw);
        Button btn=findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=edit_id.getText().toString();
                String pw=edit_pw.getText().toString();

                if (id.equals("MAP")&&pw.equals("weloveprofessor")){
                    Intent intent=new Intent(MainActivity.this, callingSearchingActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"WRONG", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}