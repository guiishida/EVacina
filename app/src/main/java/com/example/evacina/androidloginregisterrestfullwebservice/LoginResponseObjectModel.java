package com.example.evacina.androidloginregisterrestfullwebservice;

public class LoginResponseObjectModel {

    private Boolean email_notfound;
    private Boolean ok;

    public LoginResponseObjectModel() {
        this.ok = false;
    }

    public Boolean getEmail_notfound() {
        return email_notfound;
    }

    public void setEmail_notfound(Boolean email_notfound) {
        this.email_notfound = email_notfound;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }
}