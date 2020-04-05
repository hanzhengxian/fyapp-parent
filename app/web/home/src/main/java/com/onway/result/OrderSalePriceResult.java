package com.onway.result;

import java.util.List;

public class OrderSalePriceResult {

    private List<OrderSalePrice> saleYear;

    private List<OrderSalePrice> saleMounth;

    public List<OrderSalePrice> getSaleYear() {
        return saleYear;
    }

    public void setSaleYear(List<OrderSalePrice> saleYear) {
        this.saleYear = saleYear;
    }

    public List<OrderSalePrice> getSaleMounth() {
        return saleMounth;
    }

    public void setSaleMounth(List<OrderSalePrice> saleMounth) {
        this.saleMounth = saleMounth;
    }

}
