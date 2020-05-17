package com.example.evacina.androidloginregisterrestfullwebservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VaccineService {

    @POST("vaccine/register")
    Call<VaccineRegisterResponseObjectModel> register(@Query("code") Long code, @Query("email") String email);

    @POST("vaccine/register/cancel")
    Call<Boolean> register_cancel(@Query("vaccine_id") Long vaccine_id);

    @GET("vaccine/list")
    Call<ArrayList<VaccineView>> vaccine_list(@Query("email")String email);
}
