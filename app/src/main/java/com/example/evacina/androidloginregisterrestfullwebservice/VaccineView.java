package com.example.evacina.androidloginregisterrestfullwebservice;

public class VaccineView {
    private String name;
    private String date;
    private String location;
    private String status;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public VaccineView(String name, String date, String location, String status) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.status = status;
    }
}
