package com.example.evacina.androidloginregisterrestfullwebservice;

public class ApiUtils {

    private static final String BASE_URL = "http://192.168.0.3:5000/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static VaccineService getVaccineService(){
        return RetrofitClient.getClient(BASE_URL).create(VaccineService.class);
    }
}