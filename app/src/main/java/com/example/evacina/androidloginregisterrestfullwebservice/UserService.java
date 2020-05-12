package com.example.evacina.androidloginregisterrestfullwebservice;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("api/login")
    Call<LoginResponseObjectModel> login(@Query("email") String email, @Query("password") String password);

    @POST("api/register")
    Call<RegisterResponseObjectModel> register(@Query("name") String fullName, @Query("email") String email, @Query("password") String password,
                                               @Query("dateBirth") String dateBirth, @Query("cpf") Long CPF,
                                               @Query("eggAllergy") Boolean eggAllergy, @Query("jelloAllergy") Boolean jelloAllergy,
                                               @Query("proteinAllergy") Boolean proteinAllergy, @Query("yeastAllergy") Boolean yeastAllergy);
}
