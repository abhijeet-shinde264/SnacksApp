package com.auapp.snacksapp;

class BuyerData {

    String na,quan,ite,email;

    public BuyerData(String na, String quan, String ite, String email) {
        this.na = na;
        this.quan = quan;
        this.ite = ite;
        this.email = email;
    }

    public String getIte() {
        return ite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIte(String ite) {
        this.ite = ite;
    }

    public BuyerData() {
    }

    public String getNa() {
        return na;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
