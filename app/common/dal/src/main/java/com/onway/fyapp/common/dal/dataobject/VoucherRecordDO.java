/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;
import com.onway.common.lang.Money;

/**
 * A data object class directly models database table <tt>hqyt_voucher_record</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_voucher_record.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: VoucherRecordDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class VoucherRecordDO extends BaseDO {
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
	 * This property corresponds to db column <tt>RECORD_TYPE</tt>.
	 */
	private String recordType;

	/**
	 * This property corresponds to db column <tt>RECORD_AMOUNT</tt>.
	 */
	private Money recordAmount = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>RECORD_LINK</tt>.
	 */
	private String recordLink;

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
     * Getter method for property <tt>recordType</tt>.
     *
     * @return property value of recordType
     */
	public String getRecordType() {
		return recordType;
	}
	
	/**
	 * Setter method for property <tt>recordType</tt>.
	 * 
	 * @param recordType value to be assigned to property recordType
     */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

    /**
     * Getter method for property <tt>recordAmount</tt>.
     *
     * @return property value of recordAmount
     */
	public Money getRecordAmount() {
		return recordAmount;
	}
	
	/**
	 * Setter method for property <tt>recordAmount</tt>.
	 * 
	 * @param recordAmount value to be assigned to property recordAmount
     */
	public void setRecordAmount(Money recordAmount) {
		if (recordAmount == null) {
			this.recordAmount = new Money(0, 0);
		} else {
			this.recordAmount = recordAmount;
		}
	}

    /**
     * Getter method for property <tt>recordLink</tt>.
     *
     * @return property value of recordLink
     */
	public String getRecordLink() {
		return recordLink;
	}
	
	/**
	 * Setter method for property <tt>recordLink</tt>.
	 * 
	 * @param recordLink value to be assigned to property recordLink
     */
	public void setRecordLink(String recordLink) {
		this.recordLink = recordLink;
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
