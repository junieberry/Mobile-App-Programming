package edu.skku.map.week6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textview;
    Button button;
    int switch_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=findViewById(R.id.button);
        textview=findViewById(R.id.textView);
        Context context=this;

        AsyncTask<Integer, Double, String> asyncTask=new AsyncTask<Integer, Double, String>() {
            @Override
            protected void onPreExecute() {
                // 스레드가 수행하기 전에 수행할 작업
                super.onPreExecute();
                Toast.makeText(context, "Start AsyncTask", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                // 스레드 작업이 모두 끝난 후에 수행할 작업(메인 스레드)
                super.onPostExecute(s);
                Toast.makeText(context, "End AsyncTask", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onProgressUpdate(Double... values) {
                // 스레드 수행 도중
                //doInBackground()에서 publichProgress()를 호출해 중간 작업 가능
                super.onProgressUpdate(values);
                textview.setText(Double.toString(values[0]));
            }

            @Override
            protected String doInBackground(Integer... integers) {
                // 스레드가 수행할 작업
                // publishprogress()를 사용해 메인 스레드 접근 가능
                Double in=0.0;
                Double pi;
                Random r=new Random();

                for(int i=0; i<integers[0];i++){
                    try{
                        Thread.sleep(100);
                        float x=r.nextFloat();
                        float y=r.nextFloat();
                        if (x*x+y*y<=1){
                            in++;
                        }
                        pi=4*in/(i+1);

                        publishProgress(pi);
                        if ((Math.PI-0.000001<pi)&(pi<Math.PI+0.000001)){
                            break;
                        }
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                }


                return null;
            }

        };
        asyncTask.execute(1000);
    }
}