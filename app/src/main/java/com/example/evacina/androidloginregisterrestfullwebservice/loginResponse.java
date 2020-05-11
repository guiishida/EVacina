package com.example.evacina.androidloginregisterrestfullwebservice;

public class loginResponse {
    private Boolean email_notfound;
    private Boolean ok;
    public Boolean getOk() {
        return ok;
    }

    public Boolean getEmail_notfound() {
        return email_notfound;
    }

    public void setEmail_notfound(Boolean email_notfound) {
        this.email_notfound = email_notfound;
    }

    public void setOk(final Boolean ok) {
        this.ok = ok;
    }

    public loginResponse() {
        this.ok = false;
    }
}