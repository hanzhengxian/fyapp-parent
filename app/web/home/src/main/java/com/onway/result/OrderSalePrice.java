package com.onway.result;

import java.math.BigDecimal;

public class OrderSalePrice {

    private String dateA;

    private String dateB;

    private String priceA;

    private String priceB;

    private double link_ratio_money;

    private BigDecimal numA;

    private BigDecimal numB;

    private double link_ratio_num;

    public String getDateA() {
        return dateA;
    }

    public void setDateA(String dateA) {
        this.dateA = dateA;
    }

    public String getDateB() {
        return dateB;
    }

    public void setDateB(String dateB) {
        this.dateB = dateB;
    }

    public String getPriceA() {
        return priceA;
    }

    public void setPriceA(String priceA) {
        this.priceA = priceA;
    }

    public String getPriceB() {
        return priceB;
    }

    public void setPriceB(String priceB) {
        this.priceB = priceB;
    }

    public double getLink_ratio_money() {
        return link_ratio_money;
    }

    public void setLink_ratio_money(double link_ratio_money) {
        this.link_ratio_money = link_ratio_money;
    }

    public BigDecimal getNumA() {
        return numA;
    }

    public void setNumA(BigDecimal numA) {
        this.numA = numA;
    }

    public BigDecimal getNumB() {
        return numB;
    }

    public void setNumB(BigDecimal numB) {
        this.numB = numB;
    }

    public double getLink_ratio_num() {
        return link_ratio_num;
    }

    public void setLink_ratio_num(double link_ratio_num) {
        this.link_ratio_num = link_ratio_num;
    }

}
