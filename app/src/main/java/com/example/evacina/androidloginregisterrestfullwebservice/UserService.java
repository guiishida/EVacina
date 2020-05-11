package com.example.evacina.androidloginregisterrestfullwebservice;

import com.example.evacina.androidloginregisterrestfullwebservice.ResObjectModel;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("api/login/")
    Call<String> login(@Query("email") String email, @Query("password") String password);

    @POST("api/register")
    Call<String> register(@Query("name") String fullName, @Query("email") String email, @Query("password") String password,
                          @Query("dateBirth") String dateBirth, @Query("cpf") Long CPF,
                          @Query("eggAllergy") Boolean eggAllergy, @Query("jelloAllergy") Boolean jelloAllergy,
                          @Query("proteinAllergy") Boolean proteinAllergy, @Query("yeastAllergy") Boolean yeastAllergy);
}
