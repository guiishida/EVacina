package com.example.evacina.androidloginregisterrestfullwebservice;

import java.sql.Date;

public class VaccineRegisterResponseObjectModel {

    private Boolean ok;

    private Long barcode;

    private String name_vaccine;

    private String disease;

    private String producer;

    //private Date date;

    public VaccineRegisterResponseObjectModel() {
        this.ok = false;
    }

    public Long getBarcode() {
        return barcode;
    }

    public void setBarcode(Long barcode) {
        this.barcode = barcode;
    }

    public String getName_vaccine() {
        return name_vaccine;
    }

    public void setName_vaccine(String name_vaccine) {
        this.name_vaccine = name_vaccine;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}
