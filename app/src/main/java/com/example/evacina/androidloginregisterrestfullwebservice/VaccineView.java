package com.example.evacina.androidloginregisterrestfullwebservice;

public class VaccineView {
    private String name;
    private String date;
    private String disease;
    private String location;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public VaccineView(String name, String date, String disease, String location) {
        this.name = name;
        this.date = date;
        this.disease = disease;
        this.location = location;
    }
}
