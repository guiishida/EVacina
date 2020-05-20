package com.example.evacina.androidloginregisterrestfullwebservice;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface VaccineService {

    @GET("vaccine/register/check")
    Call<VaccineRegisterResponseObjectModel> check_existence(@Query("code") Long code);

    @POST("vaccine/register/add")
    Call<Boolean> register_add(@Query("barcode") Long barcode, @Query("email") String email, @Query("locationName") String locationName);

    @GET("vaccine/list")
    Call<ArrayList<VaccineView>> vaccine_list(@Query("email")String email);
}
