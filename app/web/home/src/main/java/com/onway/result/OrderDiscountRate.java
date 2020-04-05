package com.onway.result;

public class OrderDiscountRate {

    private String date;

    private String realPayPrice;

    private String oldPayPrice;
    
    private String dicountPrice;

    private double rate;
    
    private double rateAll;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRealPayPrice() {
        return realPayPrice;
    }

    public void setRealPayPrice(String realPayPrice) {
        this.realPayPrice = realPayPrice;
    }

    public String getOldPayPrice() {
        return oldPayPrice;
    }

    public void setOldPayPrice(String oldPayPrice) {
        this.oldPayPrice = oldPayPrice;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDicountPrice() {
        return dicountPrice;
    }

    public void setDicountPrice(String dicountPrice) {
        this.dicountPrice = dicountPrice;
    }

    public double getRateAll() {
        return rateAll;
    }

    public void setRateAll(double rateAll) {
        this.rateAll = rateAll;
    }

}
