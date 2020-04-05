package com.onway.model.excel;

public class ImportOrderSend {

    private String childOrderId;
    
    private String expressNo;//物流公司编号
    
    private String kuaidiNo;//快递单号

    public String getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(String childOrderId) {
        this.childOrderId = childOrderId;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getKuaidiNo() {
        return kuaidiNo;
    }

    public void setKuaidiNo(String kuaidiNo) {
        this.kuaidiNo = kuaidiNo;
    }
}
