package com.auapp.snacksapp;

class BuyerData {

    String na,quan,ite;

    public BuyerData(String na, String quan, String ite) {
        this.na = na;
        this.quan = quan;
        this.ite = ite;
    }

    public String getIte() {
        return ite;
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
