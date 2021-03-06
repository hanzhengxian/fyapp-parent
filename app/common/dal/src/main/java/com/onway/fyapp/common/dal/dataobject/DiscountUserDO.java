/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import com.onway.common.lang.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_discount_user</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_discount_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: DiscountUserDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class DiscountUserDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_TEAM_ID</tt>.
	 */
	private String discountTeamId;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_ID</tt>.
	 */
	private String discountId;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_NAME</tt>.
	 */
	private String discountName;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_TYPE</tt>.
	 */
	private String discountType;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_BACKGROUND</tt>.
	 */
	private String discountBackground;

	/**
	 * This property corresponds to db column <tt>AMOUNT</tt>.
	 */
	private Money amount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>LIMIT_AMOUNT</tt>.
	 */
	private Money limitAmount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>DISCOUNT</tt>.
	 */
	private double discount;

	/**
	 * This property corresponds to db column <tt>TIME_END</tt>.
	 */
	private Date timeEnd;

	/**
	 * This property corresponds to db column <tt>LINK_TEAM</tt>.
	 */
	private String linkTeam;

	/**
	 * This property corresponds to db column <tt>LINK_TEAM_CHILD</tt>.
	 */
	private String linkTeamChild;

	/**
	 * This property corresponds to db column <tt>LINK_PRO</tt>.
	 */
	private String linkPro;

	/**
	 * This property corresponds to db column <tt>DISCOUNT_SUB_TYPE</tt>.
	 */
	private String discountSubType;

	/**
	 * This property corresponds to db column <tt>CAN_OVER_USE</tt>.
	 */
	private String canOverUse;

	/**
	 * This property corresponds to db column <tt>CAN_OVER_NUMS</tt>.
	 */
	private int canOverNums;

	/**
	 * This property corresponds to db column <tt>CAN_TOGE_USE</tt>.
	 */
	private String canTogeUse;

	/**
	 * This property corresponds to db column <tt>CAN_TOGE_DISID</tt>.
	 */
	private String canTogeDisid;

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

	/**
	 * This property corresponds to db column <tt>CAN_DISCOUNT_USE</tt>.
	 */
	private String canDiscountUse;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
     */
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Setter method for property <tt>userId</tt>.
	 * 
	 * @param userId value to be assigned to property userId
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * Getter method for property <tt>discountTeamId</tt>.
     *
     * @return property value of discountTeamId
     */
	public String getDiscountTeamId() {
		return discountTeamId;
	}
	
	/**
	 * Setter method for property <tt>discountTeamId</tt>.
	 * 
	 * @param discountTeamId value to be assigned to property discountTeamId
     */
	public void setDiscountTeamId(String discountTeamId) {
		this.discountTeamId = discountTeamId;
	}

    /**
     * Getter method for property <tt>discountId</tt>.
     *
     * @return property value of discountId
     */
	public String getDiscountId() {
		return discountId;
	}
	
	/**
	 * Setter method for property <tt>discountId</tt>.
	 * 
	 * @param discountId value to be assigned to property discountId
     */
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

    /**
     * Getter method for property <tt>discountName</tt>.
     *
     * @return property value of discountName
     */
	public String getDiscountName() {
		return discountName;
	}
	
	/**
	 * Setter method for property <tt>discountName</tt>.
	 * 
	 * @param discountName value to be assigned to property discountName
     */
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

    /**
     * Getter method for property <tt>discountType</tt>.
     *
     * @return property value of discountType
     */
	public String getDiscountType() {
		return discountType;
	}
	
	/**
	 * Setter method for property <tt>discountType</tt>.
	 * 
	 * @param discountType value to be assigned to property discountType
     */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

    /**
     * Getter method for property <tt>discountBackground</tt>.
     *
     * @return property value of discountBackground
     */
	public String getDiscountBackground() {
		return discountBackground;
	}
	
	/**
	 * Setter method for property <tt>discountBackground</tt>.
	 * 
	 * @param discountBackground value to be assigned to property discountBackground
     */
	public void setDiscountBackground(String discountBackground) {
		this.discountBackground = discountBackground;
	}

    /**
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */
	public Money getAmount() {
		return amount;
	}
	
	/**
	 * Setter method for property <tt>amount</tt>.
	 * 
	 * @param amount value to be assigned to property amount
     */
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
	}

    /**
     * Getter method for property <tt>limitAmount</tt>.
     *
     * @return property value of limitAmount
     */
	public Money getLimitAmount() {
		return limitAmount;
	}
	
	/**
	 * Setter method for property <tt>limitAmount</tt>.
	 * 
	 * @param limitAmount value to be assigned to property limitAmount
     */
	public void setLimitAmount(Money limitAmount) {
		if (limitAmount == null) {
			this.limitAmount = new Money(0, 0);
		} else {
			this.limitAmount = limitAmount;
		}
	}

    /**
     * Getter method for property <tt>discount</tt>.
     *
     * @return property value of discount
     */
	public double getDiscount() {
		return discount;
	}
	
	/**
	 * Setter method for property <tt>discount</tt>.
	 * 
	 * @param discount value to be assigned to property discount
     */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

    /**
     * Getter method for property <tt>timeEnd</tt>.
     *
     * @return property value of timeEnd
     */
	public Date getTimeEnd() {
		return timeEnd;
	}
	
	/**
	 * Setter method for property <tt>timeEnd</tt>.
	 * 
	 * @param timeEnd value to be assigned to property timeEnd
     */
	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

    /**
     * Getter method for property <tt>linkTeam</tt>.
     *
     * @return property value of linkTeam
     */
	public String getLinkTeam() {
		return linkTeam;
	}
	
	/**
	 * Setter method for property <tt>linkTeam</tt>.
	 * 
	 * @param linkTeam value to be assigned to property linkTeam
     */
	public void setLinkTeam(String linkTeam) {
		this.linkTeam = linkTeam;
	}

    /**
     * Getter method for property <tt>linkTeamChild</tt>.
     *
     * @return property value of linkTeamChild
     */
	public String getLinkTeamChild() {
		return linkTeamChild;
	}
	
	/**
	 * Setter method for property <tt>linkTeamChild</tt>.
	 * 
	 * @param linkTeamChild value to be assigned to property linkTeamChild
     */
	public void setLinkTeamChild(String linkTeamChild) {
		this.linkTeamChild = linkTeamChild;
	}

    /**
     * Getter method for property <tt>linkPro</tt>.
     *
     * @return property value of linkPro
     */
	public String getLinkPro() {
		return linkPro;
	}
	
	/**
	 * Setter method for property <tt>linkPro</tt>.
	 * 
	 * @param linkPro value to be assigned to property linkPro
     */
	public void setLinkPro(String linkPro) {
		this.linkPro = linkPro;
	}

    /**
     * Getter method for property <tt>discountSubType</tt>.
     *
     * @return property value of discountSubType
     */
	public String getDiscountSubType() {
		return discountSubType;
	}
	
	/**
	 * Setter method for property <tt>discountSubType</tt>.
	 * 
	 * @param discountSubType value to be assigned to property discountSubType
     */
	public void setDiscountSubType(String discountSubType) {
		this.discountSubType = discountSubType;
	}

    /**
     * Getter method for property <tt>canOverUse</tt>.
     *
     * @return property value of canOverUse
     */
	public String getCanOverUse() {
		return canOverUse;
	}
	
	/**
	 * Setter method for property <tt>canOverUse</tt>.
	 * 
	 * @param canOverUse value to be assigned to property canOverUse
     */
	public void setCanOverUse(String canOverUse) {
		this.canOverUse = canOverUse;
	}

    /**
     * Getter method for property <tt>canOverNums</tt>.
     *
     * @return property value of canOverNums
     */
	public int getCanOverNums() {
		return canOverNums;
	}
	
	/**
	 * Setter method for property <tt>canOverNums</tt>.
	 * 
	 * @param canOverNums value to be assigned to property canOverNums
     */
	public void setCanOverNums(int canOverNums) {
		this.canOverNums = canOverNums;
	}

    /**
     * Getter method for property <tt>canTogeUse</tt>.
     *
     * @return property value of canTogeUse
     */
	public String getCanTogeUse() {
		return canTogeUse;
	}
	
	/**
	 * Setter method for property <tt>canTogeUse</tt>.
	 * 
	 * @param canTogeUse value to be assigned to property canTogeUse
     */
	public void setCanTogeUse(String canTogeUse) {
		this.canTogeUse = canTogeUse;
	}

    /**
     * Getter method for property <tt>canTogeDisid</tt>.
     *
     * @return property value of canTogeDisid
     */
	public String getCanTogeDisid() {
		return canTogeDisid;
	}
	
	/**
	 * Setter method for property <tt>canTogeDisid</tt>.
	 * 
	 * @param canTogeDisid value to be assigned to property canTogeDisid
     */
	public void setCanTogeDisid(String canTogeDisid) {
		this.canTogeDisid = canTogeDisid;
	}

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Setter method for property <tt>status</tt>.
	 * 
	 * @param status value to be assigned to property status
     */
	public void setStatus(String status) {
		this.status = status;
	}

    /**
     * Getter method for property <tt>canDiscountUse</tt>.
     *
     * @return property value of canDiscountUse
     */
	public String getCanDiscountUse() {
		return canDiscountUse;
	}
	
	/**
	 * Setter method for property <tt>canDiscountUse</tt>.
	 * 
	 * @param canDiscountUse value to be assigned to property canDiscountUse
     */
	public void setCanDiscountUse(String canDiscountUse) {
		this.canDiscountUse = canDiscountUse;
	}

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
	public Date getGmtModified() {
		return gmtModified;
	}
	
	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
