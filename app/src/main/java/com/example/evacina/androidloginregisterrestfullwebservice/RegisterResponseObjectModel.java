package com.example.evacina.androidloginregisterrestfullwebservice;

public class RegisterResponseObjectModel {

    private Boolean email_used;
    private Boolean cpf_used;
    private Boolean ok;

    public RegisterResponseObjectModel() {
        this.ok = false;
    }

    public Boolean getEmail_used() {
        return email_used;
    }

    public Boolean getCpf_used() {
        return cpf_used;
    }

    public void setCpf_used(Boolean cpf_used) {
        this.cpf_used = cpf_used;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(final Boolean ok) {
        this.ok = ok;
    }

    public void setEmail_used(final Boolean email_used) {
        this.email_used = email_used;
    }
}
