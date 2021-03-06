/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;
import com.onway.common.lang.Money;

/**
 * A data object class directly models database table <tt>hqyt_voucher</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_voucher.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: VoucherDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class VoucherDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>VOUCHER_NO</tt>.
	 */
	private String voucherNo;

	/**
	 * This property corresponds to db column <tt>AMOUNT</tt>.
	 */
	private Money amount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>LEFT_AMOUNT</tt>.
	 */
	private Money leftAmount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>REAL_AMOUNT</tt>.
	 */
	private Money realAmount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>SEND_TEAM_ID</tt>.
	 */
	private String sendTeamId;

	/**
	 * This property corresponds to db column <tt>MEMO</tt>.
	 */
	private String memo;

	/**
	 * This property corresponds to db column <tt>CREATER</tt>.
	 */
	private String creater;

	/**
	 * This property corresponds to db column <tt>CHECK_STATUS</tt>.
	 */
	private String checkStatus;

	/**
	 * This property corresponds to db column <tt>CHECK_USER</tt>.
	 */
	private String checkUser;

	/**
	 * This property corresponds to db column <tt>CHECK_DATE</tt>.
	 */
	private Date checkDate;

	/**
	 * This property corresponds to db column <tt>CHECK_MEMO</tt>.
	 */
	private String checkMemo;

	/**
	 * This property corresponds to db column <tt>CHECK_STATUS_ACC</tt>.
	 */
	private String checkStatusAcc;

	/**
	 * This property corresponds to db column <tt>CHECK_USER_ACC</tt>.
	 */
	private String checkUserAcc;

	/**
	 * This property corresponds to db column <tt>CHECK_DATE_ACC</tt>.
	 */
	private Date checkDateAcc;

	/**
	 * This property corresponds to db column <tt>CHECK_MEMO_ACC</tt>.
	 */
	private String checkMemoAcc;

	/**
	 * This property corresponds to db column <tt>CER_NO</tt>.
	 */
	private String cerNo;

	/**
	 * This property corresponds to db column <tt>CER_IMG</tt>.
	 */
	private String cerImg;

	/**
	 * This property corresponds to db column <tt>FROM_WAY</tt>.
	 */
	private String fromWay;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>IS_SC</tt>.
	 */
	private boolean isSc;

	/**
	 * This property corresponds to db column <tt>GMT_SC</tt>.
	 */
	private Date gmtSc;

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
     * Getter method for property <tt>voucherNo</tt>.
     *
     * @return property value of voucherNo
     */
	public String getVoucherNo() {
		return voucherNo;
	}
	
	/**
	 * Setter method for property <tt>voucherNo</tt>.
	 * 
	 * @param voucherNo value to be assigned to property voucherNo
     */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
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
     * Getter method for property <tt>leftAmount</tt>.
     *
     * @return property value of leftAmount
     */
	public Money getLeftAmount() {
		return leftAmount;
	}
	
	/**
	 * Setter method for property <tt>leftAmount</tt>.
	 * 
	 * @param leftAmount value to be assigned to property leftAmount
     */
	public void setLeftAmount(Money leftAmount) {
		if (leftAmount == null) {
			this.leftAmount = new Money(0, 0);
		} else {
			this.leftAmount = leftAmount;
		}
	}

    /**
     * Getter method for property <tt>realAmount</tt>.
     *
     * @return property value of realAmount
     */
	public Money getRealAmount() {
		return realAmount;
	}
	
	/**
	 * Setter method for property <tt>realAmount</tt>.
	 * 
	 * @param realAmount value to be assigned to property realAmount
     */
	public void setRealAmount(Money realAmount) {
		if (realAmount == null) {
			this.realAmount = new Money(0, 0);
		} else {
			this.realAmount = realAmount;
		}
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
     * Getter method for property <tt>sendTeamId</tt>.
     *
     * @return property value of sendTeamId
     */
	public String getSendTeamId() {
		return sendTeamId;
	}
	
	/**
	 * Setter method for property <tt>sendTeamId</tt>.
	 * 
	 * @param sendTeamId value to be assigned to property sendTeamId
     */
	public void setSendTeamId(String sendTeamId) {
		this.sendTeamId = sendTeamId;
	}

    /**
     * Getter method for property <tt>memo</tt>.
     *
     * @return property value of memo
     */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * Setter method for property <tt>memo</tt>.
	 * 
	 * @param memo value to be assigned to property memo
     */
	public void setMemo(String memo) {
		this.memo = memo;
	}

    /**
     * Getter method for property <tt>creater</tt>.
     *
     * @return property value of creater
     */
	public String getCreater() {
		return creater;
	}
	
	/**
	 * Setter method for property <tt>creater</tt>.
	 * 
	 * @param creater value to be assigned to property creater
     */
	public void setCreater(String creater) {
		this.creater = creater;
	}

    /**
     * Getter method for property <tt>checkStatus</tt>.
     *
     * @return property value of checkStatus
     */
	public String getCheckStatus() {
		return checkStatus;
	}
	
	/**
	 * Setter method for property <tt>checkStatus</tt>.
	 * 
	 * @param checkStatus value to be assigned to property checkStatus
     */
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

    /**
     * Getter method for property <tt>checkUser</tt>.
     *
     * @return property value of checkUser
     */
	public String getCheckUser() {
		return checkUser;
	}
	
	/**
	 * Setter method for property <tt>checkUser</tt>.
	 * 
	 * @param checkUser value to be assigned to property checkUser
     */
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

    /**
     * Getter method for property <tt>checkDate</tt>.
     *
     * @return property value of checkDate
     */
	public Date getCheckDate() {
		return checkDate;
	}
	
	/**
	 * Setter method for property <tt>checkDate</tt>.
	 * 
	 * @param checkDate value to be assigned to property checkDate
     */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

    /**
     * Getter method for property <tt>checkMemo</tt>.
     *
     * @return property value of checkMemo
     */
	public String getCheckMemo() {
		return checkMemo;
	}
	
	/**
	 * Setter method for property <tt>checkMemo</tt>.
	 * 
	 * @param checkMemo value to be assigned to property checkMemo
     */
	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}

    /**
     * Getter method for property <tt>checkStatusAcc</tt>.
     *
     * @return property value of checkStatusAcc
     */
	public String getCheckStatusAcc() {
		return checkStatusAcc;
	}
	
	/**
	 * Setter method for property <tt>checkStatusAcc</tt>.
	 * 
	 * @param checkStatusAcc value to be assigned to property checkStatusAcc
     */
	public void setCheckStatusAcc(String checkStatusAcc) {
		this.checkStatusAcc = checkStatusAcc;
	}

    /**
     * Getter method for property <tt>checkUserAcc</tt>.
     *
     * @return property value of checkUserAcc
     */
	public String getCheckUserAcc() {
		return checkUserAcc;
	}
	
	/**
	 * Setter method for property <tt>checkUserAcc</tt>.
	 * 
	 * @param checkUserAcc value to be assigned to property checkUserAcc
     */
	public void setCheckUserAcc(String checkUserAcc) {
		this.checkUserAcc = checkUserAcc;
	}

    /**
     * Getter method for property <tt>checkDateAcc</tt>.
     *
     * @return property value of checkDateAcc
     */
	public Date getCheckDateAcc() {
		return checkDateAcc;
	}
	
	/**
	 * Setter method for property <tt>checkDateAcc</tt>.
	 * 
	 * @param checkDateAcc value to be assigned to property checkDateAcc
     */
	public void setCheckDateAcc(Date checkDateAcc) {
		this.checkDateAcc = checkDateAcc;
	}

    /**
     * Getter method for property <tt>checkMemoAcc</tt>.
     *
     * @return property value of checkMemoAcc
     */
	public String getCheckMemoAcc() {
		return checkMemoAcc;
	}
	
	/**
	 * Setter method for property <tt>checkMemoAcc</tt>.
	 * 
	 * @param checkMemoAcc value to be assigned to property checkMemoAcc
     */
	public void setCheckMemoAcc(String checkMemoAcc) {
		this.checkMemoAcc = checkMemoAcc;
	}

    /**
     * Getter method for property <tt>cerNo</tt>.
     *
     * @return property value of cerNo
     */
	public String getCerNo() {
		return cerNo;
	}
	
	/**
	 * Setter method for property <tt>cerNo</tt>.
	 * 
	 * @param cerNo value to be assigned to property cerNo
     */
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

    /**
     * Getter method for property <tt>cerImg</tt>.
     *
     * @return property value of cerImg
     */
	public String getCerImg() {
		return cerImg;
	}
	
	/**
	 * Setter method for property <tt>cerImg</tt>.
	 * 
	 * @param cerImg value to be assigned to property cerImg
     */
	public void setCerImg(String cerImg) {
		this.cerImg = cerImg;
	}

    /**
     * Getter method for property <tt>fromWay</tt>.
     *
     * @return property value of fromWay
     */
	public String getFromWay() {
		return fromWay;
	}
	
	/**
	 * Setter method for property <tt>fromWay</tt>.
	 * 
	 * @param fromWay value to be assigned to property fromWay
     */
	public void setFromWay(String fromWay) {
		this.fromWay = fromWay;
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

    /**
     * Getter method for property <tt>isSc</tt>.
     *
     * @return property value of isSc
     */
	public boolean isIsSc() {
		return isSc;
	}
	
	/**
	 * Setter method for property <tt>isSc</tt>.
	 * 
	 * @param isSc value to be assigned to property isSc
     */
	public void setIsSc(boolean isSc) {
		this.isSc = isSc;
	}

    /**
     * Getter method for property <tt>gmtSc</tt>.
     *
     * @return property value of gmtSc
     */
	public Date getGmtSc() {
		return gmtSc;
	}
	
	/**
	 * Setter method for property <tt>gmtSc</tt>.
	 * 
	 * @param gmtSc value to be assigned to property gmtSc
     */
	public void setGmtSc(Date gmtSc) {
		this.gmtSc = gmtSc;
	}
}
