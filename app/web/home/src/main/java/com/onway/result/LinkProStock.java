package com.onway.result;

import java.util.List;

public class LinkProStock {

    private String productId;
    
    private List<Integer> stockId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<Integer> getStockId() {
        return stockId;
    }

    public void setStockId(List<Integer> stockId) {
        this.stockId = stockId;
    }
    
}
