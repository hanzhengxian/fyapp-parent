package com.onway.fyapp.common.dal.dataobject.rst;

import java.math.BigDecimal;
import java.util.Date;

public class DicountQuery {

    private int    id;

    private String discountTeamId;

    private String discountId;

    private String discountName;

    private String discountType;

    private String discountBackground;

    private BigDecimal amount;

    private BigDecimal   limitAmount;

    private double   discount;

    private String timeType;

    private String timeEnd;

    private Date timeEndReceive;

    private String linkTeam;
    
    private String linkPro;

    private String discountSubType;
    
    private String canOverUse;
    
    private int canOverNums;

    private String canTogeUse;
    
    private String canTogeDisid;
    
    private int limitUserLevel;
    
    private String delFlg;
    
    private int rank;
    
    private Date gmtCreate;
    
    private Date gmtModified;
    
    private String creater;
    
    private String modifier;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscountTeamId() {
        return discountTeamId;
    }

    public void setDiscountTeamId(String discountTeamId) {
        this.discountTeamId = discountTeamId;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountBackground() {
        return discountBackground;
    }

    public void setDiscountBackground(String discountBackground) {
        this.discountBackground = discountBackground;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getTimeEndReceive() {
        return timeEndReceive;
    }

    public void setTimeEndReceive(Date timeEndReceive) {
        this.timeEndReceive = timeEndReceive;
    }

    public String getLinkTeam() {
        return linkTeam;
    }

    public void setLinkTeam(String linkTeam) {
        this.linkTeam = linkTeam;
    }

    public String getLinkPro() {
        return linkPro;
    }

    public void setLinkPro(String linkPro) {
        this.linkPro = linkPro;
    }

    public String getDiscountSubType() {
        return discountSubType;
    }

    public void setDiscountSubType(String discountSubType) {
        this.discountSubType = discountSubType;
    }

    public String getCanOverUse() {
        return canOverUse;
    }

    public void setCanOverUse(String canOverUse) {
        this.canOverUse = canOverUse;
    }

    public int getCanOverNums() {
        return canOverNums;
    }

    public void setCanOverNums(int canOverNums) {
        this.canOverNums = canOverNums;
    }

    public String getCanTogeUse() {
        return canTogeUse;
    }

    public void setCanTogeUse(String canTogeUse) {
        this.canTogeUse = canTogeUse;
    }

    public String getCanTogeDisid() {
        return canTogeDisid;
    }

    public void setCanTogeDisid(String canTogeDisid) {
        this.canTogeDisid = canTogeDisid;
    }

    public int getLimitUserLevel() {
        return limitUserLevel;
    }

    public void setLimitUserLevel(int limitUserLevel) {
        this.limitUserLevel = limitUserLevel;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
    
}
