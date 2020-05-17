package com.example.evacina.androidloginregisterrestfullwebservice;

public class VaccineView {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    private String date;
    private String disease;


    public VaccineView(String name, String date, String disease) {
        this.name = name;
        this.date = date;
        this.disease = disease;
    }
}
