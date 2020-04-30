package com.example.evacina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSignIn, buttonSignUp;
    EditText editTextLogin, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignIn = findViewById(R.id.button_SignIn);
        buttonSignUp = findViewById(R.id.button_SignUp);

        buttonSignUp.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_SignIn:
                if(validate()){
                    editTextLogin = findViewById(R.id.editText_Login);
                    editTextPassword = findViewById(R.id.editText_Password);
                }

                break;
            case R.id.button_SignUp:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean validate() {
        boolean valid = true;
        //TODO write validation function to check if information on the login respect the guidelines
        return true;
    }
}

