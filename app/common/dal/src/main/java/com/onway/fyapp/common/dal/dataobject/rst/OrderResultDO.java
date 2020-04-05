package com.onway.fyapp.common.dal.dataobject.rst;

import java.math.BigDecimal;
import java.util.Date;

public class OrderResultDO {

	private int id;

	private String userId;

	private String orderId;

	private String childOrderId;

	private String recommendId;

	private BigDecimal realOrderPrice;

	private BigDecimal orderPrice;

	private BigDecimal childOrderPrice;

	private String dpriceId;

	private BigDecimal realPointPrice;

	private BigDecimal pointPrice;

	private int orderCount;

	private BigDecimal productPrice;

	private String productId;

	private String productName;

	private String teamId;

	private BigDecimal luggage;

	private String payWay;

	private String payState;

	private String payNo;

	private String orderState;

	private String productType;

	private Date orderDate;

	private Date payDate;

	private String reUserName;

	private String cell;

	private String province;

	private String city;

	private String area;

	private String reAddr;

	private String transferType;

	private String carriCom;

	private String carriNo;

	private String reduceType;

	private BigDecimal reduceAmount;

	private String invoiceNo;

	private String voucherNo;

	private String voucherCode;

	private Date sendDate;

	private Date completeDate;

	private String reasion;

	private int stockId;

	private String attrIds;

	private String attrNames;

	private String valueIds;

	private String valuees;

	private String imgSrc;

	private int serviceTimes;

	private String reducePlat;

	private String reducePro;

	private String reduceTeam;

	private String voucher;

	private Date gmtCreate;

	private Date gmtModified;
	
	private String teamName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(String childOrderId) {
        this.childOrderId = childOrderId;
    }

    public String getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(String recommendId) {
        this.recommendId = recommendId;
    }

    public BigDecimal getRealOrderPrice() {
        return realOrderPrice;
    }

    public void setRealOrderPrice(BigDecimal realOrderPrice) {
        this.realOrderPrice = realOrderPrice;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public BigDecimal getChildOrderPrice() {
        return childOrderPrice;
    }

    public void setChildOrderPrice(BigDecimal childOrderPrice) {
        this.childOrderPrice = childOrderPrice;
    }

    public String getDpriceId() {
        return dpriceId;
    }

    public void setDpriceId(String dpriceId) {
        this.dpriceId = dpriceId;
    }

    public BigDecimal getRealPointPrice() {
        return realPointPrice;
    }

    public void setRealPointPrice(BigDecimal realPointPrice) {
        this.realPointPrice = realPointPrice;
    }

    public BigDecimal getPointPrice() {
        return pointPrice;
    }

    public void setPointPrice(BigDecimal pointPrice) {
        this.pointPrice = pointPrice;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public BigDecimal getLuggage() {
        return luggage;
    }

    public void setLuggage(BigDecimal luggage) {
        this.luggage = luggage;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

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

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getCarriCom() {
        return carriCom;
    }

    public void setCarriCom(String carriCom) {
        this.carriCom = carriCom;
    }

    public String getCarriNo() {
        return carriNo;
    }

    public void setCarriNo(String carriNo) {
        this.carriNo = carriNo;
    }

    public String getReduceType() {
        return reduceType;
    }

    public void setReduceType(String reduceType) {
        this.reduceType = reduceType;
    }

    public BigDecimal getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(BigDecimal reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getReasion() {
        return reasion;
    }

    public void setReasion(String reasion) {
        this.reasion = reasion;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getAttrIds() {
        return attrIds;
    }

    public void setAttrIds(String attrIds) {
        this.attrIds = attrIds;
    }

    public String getAttrNames() {
        return attrNames;
    }

    public void setAttrNames(String attrNames) {
        this.attrNames = attrNames;
    }

    public String getValueIds() {
        return valueIds;
    }

    public void setValueIds(String valueIds) {
        this.valueIds = valueIds;
    }

    public String getValuees() {
        return valuees;
    }

    public void setValuees(String valuees) {
        this.valuees = valuees;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getServiceTimes() {
        return serviceTimes;
    }

    public void setServiceTimes(int serviceTimes) {
        this.serviceTimes = serviceTimes;
    }

    public String getReducePlat() {
        return reducePlat;
    }

    public void setReducePlat(String reducePlat) {
        this.reducePlat = reducePlat;
    }

    public String getReducePro() {
        return reducePro;
    }

    public void setReducePro(String reducePro) {
        this.reducePro = reducePro;
    }

    public String getReduceTeam() {
        return reduceTeam;
    }

    public void setReduceTeam(String reduceTeam) {
        this.reduceTeam = reduceTeam;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

 
	
}
