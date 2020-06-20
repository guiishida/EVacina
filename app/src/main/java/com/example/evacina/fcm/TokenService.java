package com.example.evacina.fcm;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TokenService {

    @POST("user/sendToken")
    Call<SendTokenResponseObject> sendToken(@Query("token") String token, @Query("email") String email);
}
