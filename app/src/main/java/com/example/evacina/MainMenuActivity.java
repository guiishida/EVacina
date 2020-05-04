package com.example.evacina;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainMenuActivity extends AppCompatActivity {

    EditText editTextLogin;
    TextInputLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.user_main_menu);
        }
        setContentView(R.layout.activity_main_menu);
    }
}
