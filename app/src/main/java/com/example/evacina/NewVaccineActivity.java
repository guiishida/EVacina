package com.example.evacina;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewVaccineActivity extends AppCompatActivity implements View.OnClickListener {

    private String name_vaccine, producer, disease, Email;
    private Long barcode;
    VaccineService vaccineService;
    TextView name_vaccineTv, producerTv, diseaseTv, barcodeTv;
    Button buttonConfirma, buttonCancela;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.registrar_vacina);
        }
        setContentView(R.layout.activity_new_vaccine);

        initViews();

        final Intent intent = getIntent();
        getVaccineData(intent);

        buttonConfirma.setOnClickListener(this);
        buttonCancela.setOnClickListener(this);

        name_vaccineTv.setText(name_vaccine);
        producerTv.setText(producer);
        diseaseTv.setText(disease);
        barcodeTv.setText(Long.toString(barcode));
    }

    private void getVaccineData(Intent intent) {
        name_vaccine = intent.getStringExtra("nameVaccine");
        barcode = intent.getLongExtra("barcode", 0L);
        disease = intent.getStringExtra("disease");
        producer = intent.getStringExtra("producer");
        Email = getIntent().getStringExtra("email");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonConfirma:
                vaccineService = ApiUtils.getVaccineService();
                doConfirmRegister(barcode, Email);
                Intent intent = new Intent (NewVaccineActivity.this, MainMenuActivity.class);
                intent.putExtra("email", Email);
                startActivity(intent);
                break;
            case R.id.buttonCancela:
                Intent scanIntent = new Intent(NewVaccineActivity.this, ScanActivity.class);
                startActivity(scanIntent);
        }
    }

    public void doConfirmRegister(final Long code, final String Email){
        Call<Boolean> call = vaccineService.register_ok(code, Email);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    Boolean resObject = response.body();
                    if(resObject){
                        Toast.makeText(NewVaccineActivity.this,"Vacina registrada com sucesso", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(NewVaccineActivity.this,"Não foi possível registrar vacina", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(NewVaccineActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(NewVaccineActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        name_vaccineTv = findViewById(R.id.name_vaccine_text);
        producerTv = findViewById(R.id.producer_text);
        diseaseTv = findViewById(R.id.disease_text);
        barcodeTv = findViewById(R.id.barcode_text);
        buttonConfirma = findViewById(R.id.buttonConfirma);
        buttonCancela = findViewById(R.id.buttonCancela);
    }
}