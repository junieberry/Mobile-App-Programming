package edu.skku.map.week5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class callingSearchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_searching);

        EditText edit_search=findViewById(R.id.search);
        EditText edit_calling=findViewById(R.id.calling);
        Button btn_search=findViewById(R.id.search_btn);
        Button btn_calling=findViewById(R.id.calling_btn);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search=edit_search.getText().toString();
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, search);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        btn_calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=edit_calling.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }
}