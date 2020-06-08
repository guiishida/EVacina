package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineService;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineAdapter;
import com.example.evacina.androidloginregisterrestfullwebservice.VaccineView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizeActivity extends AppCompatActivity {

    private static final String TAG = "VisualizeActivity";

    LinearLayout linearLayout;
    VaccineAdapter vaccineAdapter;
    ArrayList<ArrayList<VaccineView>> vaccineTable = new ArrayList<ArrayList<VaccineView>>();
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
        Log.d(TAG, "Start of Request to get list of vaccines for user");
        getData();
        initView();
    }


    public void getData(){
        Call<ArrayList<ArrayList<VaccineView>>> call = vaccineService.vaccine_list(Email);
        call.enqueue(new Callback<ArrayList<ArrayList<VaccineView>>>() {
            @Override
            public void onResponse(Call<ArrayList<ArrayList<VaccineView>>> call, Response<ArrayList<ArrayList<VaccineView>>> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "Request Response is OK");
                    vaccineTable = response.body();
                    initGrid();
                }
                else{
                    Log.e(TAG, "The request response was not successful: " + response.code());
                    Toast.makeText(VisualizeActivity.this, "A requisição não obteve sucesso. Tente novamente", Toast.LENGTH_LONG).show();
                    Intent mainMenuIntent = new Intent (VisualizeActivity.this, MainMenuActivity.class);
                    mainMenuIntent.putExtra("email", Email);
                    startActivity(mainMenuIntent);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ArrayList<VaccineView>>> call, Throwable t) {
                Log.e(TAG, "The request to the server failed: " + t.getMessage());
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
        linearLayout = findViewById(R.id.visualizeLayout);

        for (ArrayList<VaccineView> vaccineViewArrayList:vaccineTable) {
            // Creating vaccine rows
            RecyclerView recyclerView = new RecyclerView(this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            vaccineAdapter = new VaccineAdapter(vaccineViewArrayList);
            recyclerView.setAdapter(vaccineAdapter);

            // Adding Row to Table
            linearLayout.addView(recyclerView,0);
        }
    }
}
