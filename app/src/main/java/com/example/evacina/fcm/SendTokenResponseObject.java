package com.example.evacina.fcm;

public class SendTokenResponseObject {

    private Boolean success;

    public SendTokenResponseObject(){this.success = false;}

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
