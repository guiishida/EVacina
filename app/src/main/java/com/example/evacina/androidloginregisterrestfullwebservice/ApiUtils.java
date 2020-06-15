package com.example.evacina.androidloginregisterrestfullwebservice;

public class ApiUtils {

    private static final String BASE_URL = "http://evacinasappserver-env.eba-h5npqmqj.sa-east-1.elasticbeanstalk.com//";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static VaccineService getVaccineService(){
        return RetrofitClient.getClient(BASE_URL).create(VaccineService.class);
    }
}