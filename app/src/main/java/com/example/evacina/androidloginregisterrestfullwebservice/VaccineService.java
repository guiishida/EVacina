package com.example.evacina.androidloginregisterrestfullwebservice;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VaccineService {

    @POST("vaccine/register")
    Call<VaccineRegisterResponseObjectModel> register(@Query("code") Long code, @Query("email") String email);
}
