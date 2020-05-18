package com.example.evacina;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextLogin;
    TextInputLayout layout;
    Button buttonNewVaccine, buttonVisualize, buttonGeneratePDF, buttonGenerateCertificates, buttonMaps, buttonPendingVaccines;
    private String Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.user_main_menu);
        }
        Email = getIntent().getStringExtra("email");
        setContentView(R.layout.activity_main_menu);

        initViews();

        buttonNewVaccine.setOnClickListener(this);
        buttonVisualize.setOnClickListener(this);
        buttonGeneratePDF.setOnClickListener(this);
        buttonGenerateCertificates.setOnClickListener(this);
        buttonMaps.setOnClickListener(this);
        buttonPendingVaccines.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNewVaccine:
                Intent newVaccineIntent = new Intent(MainMenuActivity.this, ScanActivity.class);
                newVaccineIntent.putExtra("email", Email);
                startActivity(newVaccineIntent);
                break;
            case R.id.buttonVisualize:
                Intent visualizeIntent = new Intent(MainMenuActivity.this, VisualizeActivity.class);
                visualizeIntent.putExtra("email", Email);
                startActivity(visualizeIntent);
                break;
            case R.id.buttonGeneratePDF:
                break;
            case R.id.buttonGenerateCertificates:
                break;
            case R.id.buttonMaps:
                Intent mapsIntent = new Intent(MainMenuActivity.this, MapsActivity.class);
                mapsIntent.putExtra("email", Email);
                startActivity(mapsIntent);
                break;
            case R.id.buttonPendingVaccines:
                break;
        }
    }

    private void initViews() {
        buttonNewVaccine = findViewById(R.id.buttonNewVaccine);
        buttonVisualize = findViewById(R.id.buttonVisualize);
        buttonGeneratePDF = findViewById(R.id.buttonGeneratePDF);
        buttonGenerateCertificates = findViewById(R.id.buttonGenerateCertificates);
        buttonMaps = findViewById(R.id.buttonMaps);
        buttonPendingVaccines = findViewById(R.id.buttonPendingVaccines);
    }

}