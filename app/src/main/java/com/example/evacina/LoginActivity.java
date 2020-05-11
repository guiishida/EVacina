package com.example.evacina;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.UserService;
import com.example.evacina.androidloginregisterrestfullwebservice.loginResponse;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSignIn, buttonSignUp;
    EditText editTextLoginEmail, editTextLoginPassword;
    TextInputLayout textInputLayoutLoginEmail, textInputLayoutPassword;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        buttonSignUp.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);
        userService = ApiUtils.getUserService();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonLogin:
                if(validate()){
                    String Email = editTextLoginEmail.getText().toString();
                    String Password = editTextLoginPassword.getText().toString();
                    doLogin(Email, Password);
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

    private void doLogin(final String email, String password) {
        Call<loginResponse> call = userService.login(email, password);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                if (response.isSuccessful()){
                    loginResponse resObj = response.body();
                    if(resObj.getOk()){
                        Intent intent = new Intent (LoginActivity.this, MainMenuActivity.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

