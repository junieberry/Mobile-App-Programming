package edu.skku.map.week7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView tv;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button=findViewById(R.id.btn);
        search=findViewById(R.id.search);
        tv=findViewById(R.id.tv);


        button.setOnClickListener(new View.OnClickListener() {

            String title=search.getText().toString();

            @Override
            public void onClick(View v) {
                OkHttpClient client= new OkHttpClient();
//                HttpUrl.Builder urlBuilder=HttpUrl.parse("http://reqres.in/api/users").newBuilder();
//                urlBuilder.addQueryParameter("page","2");
                HttpUrl.Builder urlBuilder=HttpUrl.parse("http://www.omdbapi.com/").newBuilder();
                urlBuilder.addQueryParameter("t",search.getText().toString());
                urlBuilder.addQueryParameter("apikey","c16fd99c");

                String url=urlBuilder.build().toString();
                Request req=new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(req).enqueue(new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        final String myResponse =response.body().string();

                        Gson gson=new GsonBuilder().create();
                        DataModel data=gson.fromJson(myResponse, DataModel.class);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String txt;
                                txt= "\nTitle :"+data.getTitle()+ "\nYear : "+data.getYear()+"\nReleased Date : "+data.getReleased()+ "\nRuntime : "+data.getRuntime()+"\nGenre : "+data.getGenre()+"\nIMDB Rates : "+data.getImdbRating()+"\nMetadata : "+data.getMetascore();
                                tv.setText(txt);
                            }
                        });
                    }
                });
            }
        });
    }
}