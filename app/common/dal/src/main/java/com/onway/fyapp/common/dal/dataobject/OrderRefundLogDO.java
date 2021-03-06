/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import com.onway.common.lang.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_order_refund_logs</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_order_refund_logs.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderRefundLogDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class OrderRefundLogDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>ORDER_NO</tt>.
	 */
	private String orderNo;

	/**
	 * This property corresponds to db column <tt>ORDER_ID</tt>.
	 */
	private String orderId;

	/**
	 * This property corresponds to db column <tt>REFUND_NO</tt>.
	 */
	private String refundNo;

	/**
	 * This property corresponds to db column <tt>REFUND_TYPE</tt>.
	 */
	private String refundType;

	/**
	 * This property corresponds to db column <tt>AMOUNT</tt>.
	 */
	private Money amount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

	/**
	 * This property corresponds to db column <tt>ERROR_MSG</tt>.
	 */
	private String errorMsg;

	/**
	 * This property corresponds to db column <tt>RESULT_MSG</tt>.
	 */
	private String resultMsg;

	/**
	 * This property corresponds to db column <tt>NOTIFY_MSG</tt>.
	 */
	private String notifyMsg;

	/**
	 * This property corresponds to db column <tt>RETURN_ID</tt>.
	 */
	private int returnId;

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
     * Getter method for property <tt>orderNo</tt>.
     *
     * @return property value of orderNo
     */
	public String getOrderNo() {
		return orderNo;
	}
	
	/**
	 * Setter method for property <tt>orderNo</tt>.
	 * 
	 * @param orderNo value to be assigned to property orderNo
     */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

    /**
     * Getter method for property <tt>orderId</tt>.
     *
     * @return property value of orderId
     */
	public String getOrderId() {
		return orderId;
	}
	
	/**
	 * Setter method for property <tt>orderId</tt>.
	 * 
	 * @param orderId value to be assigned to property orderId
     */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

    /**
     * Getter method for property <tt>refundNo</tt>.
     *
     * @return property value of refundNo
     */
	public String getRefundNo() {
		return refundNo;
	}
	
	/**
	 * Setter method for property <tt>refundNo</tt>.
	 * 
	 * @param refundNo value to be assigned to property refundNo
     */
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

    /**
     * Getter method for property <tt>refundType</tt>.
     *
     * @return property value of refundType
     */
	public String getRefundType() {
		return refundType;
	}
	
	/**
	 * Setter method for property <tt>refundType</tt>.
	 * 
	 * @param refundType value to be assigned to property refundType
     */
	public void setRefundType(String refundType) {
		this.refundType = refundType;
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
     * Getter method for property <tt>errorMsg</tt>.
     *
     * @return property value of errorMsg
     */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Setter method for property <tt>errorMsg</tt>.
	 * 
	 * @param errorMsg value to be assigned to property errorMsg
     */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

    /**
     * Getter method for property <tt>resultMsg</tt>.
     *
     * @return property value of resultMsg
     */
	public String getResultMsg() {
		return resultMsg;
	}
	
	/**
	 * Setter method for property <tt>resultMsg</tt>.
	 * 
	 * @param resultMsg value to be assigned to property resultMsg
     */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

    /**
     * Getter method for property <tt>notifyMsg</tt>.
     *
     * @return property value of notifyMsg
     */
	public String getNotifyMsg() {
		return notifyMsg;
	}
	
	/**
	 * Setter method for property <tt>notifyMsg</tt>.
	 * 
	 * @param notifyMsg value to be assigned to property notifyMsg
     */
	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}

    /**
     * Getter method for property <tt>returnId</tt>.
     *
     * @return property value of returnId
     */
	public int getReturnId() {
		return returnId;
	}
	
	/**
	 * Setter method for property <tt>returnId</tt>.
	 * 
	 * @param returnId value to be assigned to property returnId
     */
	public void setReturnId(int returnId) {
		this.returnId = returnId;
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
