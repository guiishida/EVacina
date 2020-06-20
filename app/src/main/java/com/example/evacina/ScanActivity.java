package com.example.evacina;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.PermissionUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineRegisterResponseObjectModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "ScanActivity";

    TextView textViewInstructions;
    VaccineService vaccineService;
    IntentIntegrator intentIntegrator;
    Button buttonScan;
    Spinner spinnerPlaces;

    private String Email;
    private String locationName;

    PlacesClient placesClient;
    List<String> placesList;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.registrar_vacina);
        }

        // Initialize the SDK
        Places.initialize(getApplicationContext(), "AIzaSyCVtiE5Ft1BhsZ5yccABuhEY8YUhIIFr3I");

        // Create a new Places client instance
        placesClient = Places.createClient(this);
        placesList = new ArrayList<>();

        Email = getIntent().getStringExtra("email");

        setContentView(R.layout.activity_scan);

        initViews();
        getCurrentLocation(this);

    }

    private void getCurrentLocation(Activity activity) {
        List<Place.Field> placeFields = new ArrayList<>();

        // Add fields to define the data types to return.
        placeFields.add(Place.Field.NAME);
        placeFields.add(Place.Field.TYPES);
        placeFields.add(Place.Field.ADDRESS);
        placeFields.add(Place.Field.LAT_LNG);


        // Use the builder to create a FindCurrentPlaceRequest.
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);

        // Call findCurrentPlace and handle the response (first check that the user has granted permission).
        Log.d(TAG, "Request para a Places API");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);
            placeResponse.addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.d(TAG, "Places API Request Response is OK");
                    FindCurrentPlaceResponse response = task.getResult();
                    assert response != null;
                    List<PlaceLikelihood> responseList = response.getPlaceLikelihoods();
                    //Collections.sort(responseList, (o1, o2) -> Double.compare(o2.getLikelihood(), o1.getLikelihood()));
                    for (PlaceLikelihood placeLikelihood : responseList) {
                        if (Objects.requireNonNull(placeLikelihood.getPlace().getTypes()).contains(Place.Type.HEALTH)) {
                            placesList.add(placeLikelihood.getPlace().getName());
                        }
                    }
                    initScanButton(activity);
                    initSpinner();
                } else {
                    Exception exception = task.getException();
                    if (exception instanceof ApiException) {
                        ApiException apiException = (ApiException) exception;
                        Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                    }
                }
            });
        } else {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    private void initViews() {
        textViewInstructions = findViewById(R.id.textViewRegisterNewVaccine);
        buttonScan = findViewById(R.id.buttonScan);
    }

    private void initSpinner(){
        spinnerPlaces = findViewById(R.id.spinnerPlaces);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, placesList);
        spinnerPlaces.setAdapter(dataAdapter);

        spinnerPlaces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nothing Happens
            }
        });
    }


    private void initScanButton(Activity activity) {
        buttonScan.setOnClickListener(v -> {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
            Log.d(TAG, "Lançamento do Scan");
            intentIntegrator = new IntentIntegrator(activity);
            intentIntegrator.setBeepEnabled(false);
            intentIntegrator.initiateScan();
        });
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
                Log.d(TAG, "Checagem da existência da vacina na database");
                doCheckVaccineExistence(Long.parseLong(barCode));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void doCheckVaccineExistence(final Long code) {
        Call<VaccineRegisterResponseObjectModel> call = vaccineService.check_existence(code);
        call.enqueue(new Callback<VaccineRegisterResponseObjectModel>() {
            @Override
            public void onResponse(Call<VaccineRegisterResponseObjectModel> call, Response<VaccineRegisterResponseObjectModel> response) {
                if (response.isSuccessful()){
                    VaccineRegisterResponseObjectModel resObject = response.body();
                    if(resObject.getOk()){
                        Log.d(TAG, "Request Response is OK");
                        Intent intent = new Intent (ScanActivity.this, NewVaccineActivity.class);
                        intent.putExtra("email", Email);
                        intent.putExtra("barcode", code);
                        intent.putExtra("nameVaccine", resObject.getName_vaccine());
                        intent.putExtra("disease", resObject.getDisease());
                        intent.putExtra("producer", resObject.getProducer());
                        intent.putExtra("locationName", locationName);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(ScanActivity.this,"Código de barras inválido !", Toast.LENGTH_SHORT).show();
                        intentIntegrator.initiateScan();
                    }
                }
                else{
                    Log.e(TAG, "The request response was not successful: " + response.code());
                    Toast.makeText(ScanActivity.this, "A requisição não obteve sucesso. Tente novamente", Toast.LENGTH_SHORT).show();
                    intentIntegrator.initiateScan();
                }
            }

            @Override
            public void onFailure(Call<VaccineRegisterResponseObjectModel> call, Throwable t) {
                Log.e(TAG, "The request to the server failed: " + t.getMessage());
                Toast.makeText(ScanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}