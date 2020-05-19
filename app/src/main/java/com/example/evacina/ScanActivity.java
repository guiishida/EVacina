package com.example.evacina;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.PermissionUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineRegisterResponseObjectModel;


public class ScanActivity extends AppCompatActivity implements LocationListener {

    TextView textViewInstructions;
    VaccineService vaccineService;
    IntentIntegrator intentIntegrator;
    Button buttonScan;

    private String Email;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
    protected LocationManager locationManager;
    private Location location;
    private double latitude; // latitude
    private double longitude; // longitude

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
        getLocation();

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                intentIntegrator = new IntentIntegrator(activity);
                intentIntegrator.setBeepEnabled(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    private void getLocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                } else {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
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
                doRegister(Long.parseLong(barCode));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void doRegister(final Long code) {
        Call<VaccineRegisterResponseObjectModel> call = vaccineService.register(code, Email);
        call.enqueue(new Callback<VaccineRegisterResponseObjectModel>() {
            @Override
            public void onResponse(Call<VaccineRegisterResponseObjectModel> call, Response<VaccineRegisterResponseObjectModel> response) {
                if (response.isSuccessful()){
                    VaccineRegisterResponseObjectModel resObject = response.body();
                    if(resObject.getOk()){
                        Intent intent = new Intent (ScanActivity.this, NewVaccineActivity.class);
                        intent.putExtra("email", Email);
                        intent.putExtra("barcode", code);
                        intent.putExtra("nameVaccine", resObject.getName_vaccine());
                        intent.putExtra("disease", resObject.getDisease());
                        intent.putExtra("producer", resObject.getProducer());
                        intent.putExtra("vaccine_id", resObject.getVaccine_id());
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

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
}