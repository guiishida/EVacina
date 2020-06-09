package com.example.evacina;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.fcm.FirebaseMessagingReceiver;
import com.example.evacina.fcm.SendTokenResponseObject;
import com.example.evacina.fcm.TokenService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.PendingIntent.getActivity;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainMenuActivity";
    TokenService tokenService;

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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        //Send Token and Email to server
                        Log.d(TAG, token);
                        tokenService = ApiUtils.getTokenService();
                        sendTokenToServer(token, Email);


                    }
                });
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

    private void sendTokenToServer(String token, String email) {
        if(token != null && !token.isEmpty()){
            Call<SendTokenResponseObject> call = tokenService.sendToken(token, email);
            call.enqueue(new Callback<SendTokenResponseObject>() {
                @Override
                public void onResponse(Call<SendTokenResponseObject> call, Response<SendTokenResponseObject> response) {
                    SendTokenResponseObject resObject = response.body();
                    if (resObject.getSuccess()){
                        Log.d(TAG, "Token was successfully sent to the server");
                    }
                    else{
                        Log.d(TAG, "Token is already saved on the database");
                    }

                }
                @Override
                public void onFailure(Call<SendTokenResponseObject> call, Throwable t) {
                    Log.e(TAG, "The request to the server failed: " + t.getMessage());
                }
            });
        }
    }
}