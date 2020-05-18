package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineAdapter;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizeActivity extends AppCompatActivity {

    GridView grid_view;
    VaccineAdapter vaccineAdapter;
    ArrayList<VaccineView> vaccineViewArrayList = new ArrayList<VaccineView>();
    VaccineService vaccineService;
    Button buttonMenu;


    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.carteira);
        }

        setContentView(R.layout.activity_visualize);
        Email = getIntent().getStringExtra("email");

        vaccineService = ApiUtils.getVaccineService();
        getData();
        initView();
    }


    public void getData(){
        Call<ArrayList<VaccineView>> call = vaccineService.vaccine_list(Email);
        call.enqueue(new Callback<ArrayList<VaccineView>>() {
            @Override
            public void onResponse(Call<ArrayList<VaccineView>> call, Response<ArrayList<VaccineView>> response) {
                if (response.isSuccessful()){
                    vaccineViewArrayList = response.body();
                    initGrid();
                }
                else{
                    Toast.makeText(VisualizeActivity.this, response.code(), Toast.LENGTH_LONG).show();
                    Intent mainMenuIntent = new Intent (VisualizeActivity.this, MainMenuActivity.class);
                    mainMenuIntent.putExtra("email", Email);
                    startActivity(mainMenuIntent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VaccineView>> call, Throwable t) {
                Toast.makeText(VisualizeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Intent mainMenuIntent = new Intent (VisualizeActivity.this, MainMenuActivity.class);
                mainMenuIntent.putExtra("email", Email);
                startActivity(mainMenuIntent);
            }
        });
    }

    public void initView(){
        buttonMenu = findViewById(R.id.buttonMenu);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent (VisualizeActivity.this, MainMenuActivity.class);
                mainMenuIntent.putExtra("email", Email);
                startActivity(mainMenuIntent);
            }
        });
    }

    private void initGrid() {
        grid_view = findViewById(R.id.grid_view);
        vaccineAdapter = new VaccineAdapter(this, vaccineViewArrayList);
        grid_view.setAdapter(vaccineAdapter);
    }
}
