package com.onway.shunfeng.model;

import java.util.List;

import com.onway.fyapp.common.dal.dataobject.OrderDO;

public class OrderMailnoResult {

    private String reUserName;
    private String cell;
    private String province;
    private String city;
    private String area;
    private String reAddr;
    
    private String mailNo;
    
    private List<OrderDO> childOrderS;

    public String getReUserName() {
        return reUserName;
    }

    public void setReUserName(String reUserName) {
        this.reUserName = reUserName;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReAddr() {
        return reAddr;
    }

    public void setReAddr(String reAddr) {
        this.reAddr = reAddr;
    }

    public List<OrderDO> getChildOrderS() {
        return childOrderS;
    }

    public void setChildOrderS(List<OrderDO> childOrderS) {
        this.childOrderS = childOrderS;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }
   
}
