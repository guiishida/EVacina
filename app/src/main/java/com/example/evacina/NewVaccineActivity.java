package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NewVaccineActivity extends AppCompatActivity {

    String name_vaccine, producer, disease;
    Long barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.registrar_vacina);
        }
        setContentView(R.layout.activity_new_vaccine);

        Intent intent = getIntent();
        getVaccineData(intent);

        //TODO dependendo do layout criar e setar o valor dos elementos com os valores definidos em getVaccineData()


    }

    private void getVaccineData(Intent intent){
        name_vaccine = intent.getStringExtra("nameVaccine");
        barcode = intent.getLongExtra("barcode",0L);
        disease = intent.getStringExtra("disease");
        producer = intent.getStringExtra("producer");
    }

}