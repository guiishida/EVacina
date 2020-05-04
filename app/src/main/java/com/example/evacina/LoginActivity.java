package com.example.evacina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSignIn, buttonSignUp;
    EditText editTextLoginEmail, editTextLoginPassword;
    TextInputLayout textInputLayoutLoginEmail, textInputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        buttonSignUp.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonLogin:
                if(validate()){
                    String Email = editTextLoginEmail.getText().toString();
                    String Password = editTextLoginPassword.getText().toString();

                    //TODO Authenticate user is in the database
                    // if user is in the db --> call homepage activity
                    // if user is not in the db --> error message

                    Intent intent = new Intent (LoginActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.buttonSignUp:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initViews() {
        buttonSignIn = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textInputLayoutLoginEmail = findViewById(R.id.textInputLayoutLoginEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutLoginPassword);
        editTextLoginEmail = findViewById(R.id.textInputLayoutEditLoginEmail);
        editTextLoginPassword = findViewById(R.id.textInputLayoutEditLoginPassword);
    }

    private boolean validate() {
        boolean valid = true;

        String Email = editTextLoginEmail.getText().toString();
        String Password = editTextLoginPassword.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid = false;
            textInputLayoutLoginEmail.setError("Por favor inserir um email valido");
        }
        else{
            textInputLayoutLoginEmail.setError(null);
        }

        if(Password.isEmpty()){
            valid = false;
            textInputLayoutPassword.setError("Por favor inserir uma senha valida");
        }
        else {
            if (Password.length() < 8) {
                valid = false;
                textInputLayoutPassword.setError("A senha deve ter, no mínimo, 8 caracteres");
            } else {
                textInputLayoutPassword.setError(null);
            }
        }
        return valid;
    }
}

