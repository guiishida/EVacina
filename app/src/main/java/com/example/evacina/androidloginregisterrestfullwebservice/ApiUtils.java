package com.example.evacina.androidloginregisterrestfullwebservice;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.100.182:5000/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}