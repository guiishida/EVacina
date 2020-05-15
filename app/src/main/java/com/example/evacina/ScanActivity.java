package com.example.evacina;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineRegisterResponseObjectModel;

public class ScanActivity extends AppCompatActivity {

    TextView textViewInstructions;
    VaccineService vaccineService;
    IntentIntegrator intentIntegrator;
    Button buttonScan;

    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.registrar_vacina);
        }

        Email = getIntent().getStringExtra("email");
        setContentView(R.layout.activity_scan);

        final Activity activity = this;
        initViews();

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });


    }

    private void initViews() {
        textViewInstructions = findViewById(R.id.textViewRegisterNewVaccine);
        buttonScan = findViewById(R.id.buttonScan);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(ScanActivity.this, "Scan Error", Toast.LENGTH_SHORT).show();
            } else {
                String barCode = intentResult.getContents();
                vaccineService = ApiUtils.getVaccineService();
                doRegister(Long.parseLong(barCode), Email);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void doRegister(final Long code, String email) {
        Call<VaccineRegisterResponseObjectModel> call = vaccineService.register(code, email);
        call.enqueue(new Callback<VaccineRegisterResponseObjectModel>() {
            @Override
            public void onResponse(Call<VaccineRegisterResponseObjectModel> call, Response<VaccineRegisterResponseObjectModel> response) {
                if (response.isSuccessful()){
                    VaccineRegisterResponseObjectModel resObject = response.body();
                    if(resObject.getOk()){
                        Intent intent = new Intent (ScanActivity.this, NewVaccineActivity.class);
                        intent.putExtra("barcode", code);
                        intent.putExtra("nameVaccine", resObject.getName_vaccine());
                        intent.putExtra("disease", resObject.getDisease());
                        intent.putExtra("producer", resObject.getProducer());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ScanActivity.this,"Código de barras inválido !", Toast.LENGTH_SHORT).show();
                        intentIntegrator.initiateScan();
                    }
                }
                else{
                    Toast.makeText(ScanActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    intentIntegrator.initiateScan();
                }
            }

            @Override
            public void onFailure(Call<VaccineRegisterResponseObjectModel> call, Throwable t) {
                Toast.makeText(ScanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}