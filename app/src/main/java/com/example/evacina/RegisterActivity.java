package com.example.evacina;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evacina.androidloginregisterrestfullwebservice.ApiUtils;
import com.example.evacina.androidloginregisterrestfullwebservice.ResObjectModel;
import com.example.evacina.androidloginregisterrestfullwebservice.UserService;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button buttonSignIn, buttonSignUp;
    EditText editTextFullName, editTextCPF, editTextBirthDate, editTextEmail, editTextPassword, editTextConfirmedPassword;
    TextInputLayout textInputLayoutFullName, textInputLayoutCPF, textInputLayoutBirthDate, textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword, textInputLayoutConfirmedPassword;
    CheckBox checkBoxAllergyEggs, checkBoxAllergyProtein, checkboxAllergyYeast, checkboxAllergyJello;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        userService = ApiUtils.getUserService();
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String FullName = editTextFullName.getText().toString();
                    Long CPF = Long.valueOf(editTextCPF.getText().toString());
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    String BirthDate = editTextBirthDate.getText().toString(); //change to date later
                    Boolean AllergyEgg = checkBoxAllergyEggs.isChecked();
                    Boolean AllergyProtein = checkBoxAllergyProtein.isChecked();
                    Boolean AllergyYeast = checkboxAllergyYeast.isChecked();
                    Boolean AllergyJello = checkboxAllergyJello.isChecked();

//                    Date BirthDate= null;
//                    try {
//                        BirthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    registerUser(Email, Password, BirthDate, CPF, FullName, AllergyEgg, AllergyProtein, AllergyJello, AllergyYeast);
                }
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                //startActivity(intent);
            }
        });

    }

    private void registerUser(final String email, final String password, String birthDate, Long cpf, String fullName, Boolean allergyEgg,
                              Boolean allergyProtein, Boolean allergyJello, Boolean allergyYeast) {

        Call<String> call = userService.register(fullName, email, password, birthDate, cpf, allergyEgg, allergyJello, allergyProtein, allergyYeast);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String resObj = response.body();
                    if (resObj.equals("Saved")) {

                        Toast.makeText(RegisterActivity.this, response.code(), Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("password", password);
                                startActivity(intent);
                            }
                        }, 3000);

                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonRegister);

        textInputLayoutFullName = findViewById(R.id.textInputLayoutRegisterCompleteName);
        textInputLayoutCPF = findViewById(R.id.textInputLayoutRegisterCPF);
        textInputLayoutBirthDate = findViewById(R.id.textInputLayoutRegisterBirthDate);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutRegisterEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutRegisterPassword);
        textInputLayoutConfirmedPassword = findViewById(R.id.textInputLayoutRegisterPasswordConfirmation);

        editTextFullName = findViewById(R.id.textInputLayoutEditRegisterCompleteName);
        editTextCPF = findViewById(R.id.textInputLayoutEditRegisterCPF);
        editTextBirthDate = findViewById(R.id.textInputLayoutEditRegisterBirthDate);
        editTextEmail = findViewById(R.id.textInputLayoutEditRegisterEmail);
        editTextPassword = findViewById(R.id.textInputLayoutEditRegisterPassword);
        editTextConfirmedPassword = findViewById(R.id.textInputLayoutEditRegisterPasswordConfirmation);

        checkBoxAllergyEggs = findViewById(R.id.checkboxAllergyEgg);
        checkBoxAllergyProtein = findViewById(R.id.checkboxAllergyProteinCowMilk);
        checkboxAllergyYeast = findViewById(R.id.checkboxAllergyYeast);
        checkboxAllergyJello = findViewById(R.id.checkboxAllergyJello);
    }

    private boolean validate() {
        boolean valid = true;

        //TODO add check to birth date
        String FullName = editTextFullName.getText().toString();
        String CPF = editTextCPF.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();
        String ConfirmedPassword = editTextConfirmedPassword.getText().toString();

        if(FullName.isEmpty()){
            valid = false;
            textInputLayoutFullName.setError("Nome Completo não pode ser vazio");
        }
        if(CPF.isEmpty()){
            valid = false;
            textInputLayoutCPF.setError("CPF não pode ser vazio");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid = false;
            textInputLayoutEmail.setError("Por favor inserir um email valido");
        }
        else{
            textInputLayoutEmail.setError(null);
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

        if(!ConfirmedPassword.equals(Password) && !ConfirmedPassword.isEmpty() || ConfirmedPassword.length()<8){
            valid = false;
            textInputLayoutConfirmedPassword.setError("As senhas devem ser iguais e ter mais de 8 digitos!");
        }
        else{
            textInputLayoutConfirmedPassword.setError(null);
        }
        return valid;
    }


}

