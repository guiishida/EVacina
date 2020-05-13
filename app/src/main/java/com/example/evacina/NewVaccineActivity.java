package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewVaccineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vaccine);
        TextView textv = findViewById(R.id.tv2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null){
            String code = (String) bundle.get("code");
            textv.setText(code);
        }
    }

}