/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_team</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_team.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: TeamDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class TeamDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>TEAM_ID</tt>.
	 */
	private String teamId;

	/**
	 * This property corresponds to db column <tt>TEAM_NAME</tt>.
	 */
	private String teamName;

	/**
	 * This property corresponds to db column <tt>TEAM_TYPE</tt>.
	 */
	private String teamType;

	/**
	 * This property corresponds to db column <tt>TOP_TEAM_ID</tt>.
	 */
	private String topTeamId;

	/**
	 * This property corresponds to db column <tt>IS_TOP</tt>.
	 */
	private String isTop;

	/**
	 * This property corresponds to db column <tt>CELL</tt>.
	 */
	private String cell;

	/**
	 * This property corresponds to db column <tt>ADDRESS</tt>.
	 */
	private String address;

	/**
	 * This property corresponds to db column <tt>LONGITUDE</tt>.
	 */
	private double longitude;

	/**
	 * This property corresponds to db column <tt>LATITUDE</tt>.
	 */
	private double latitude;

	/**
	 * This property corresponds to db column <tt>FAVOURABLE_ACTIVE</tt>.
	 */
	private String favourableActive;

	/**
	 * This property corresponds to db column <tt>DEL_FLG</tt>.
	 */
	private String delFlg;

	/**
	 * This property corresponds to db column <tt>ERP_NO</tt>.
	 */
	private String erpNo;

	/**
	 * This property corresponds to db column <tt>TEAM_LEVEL</tt>.
	 */
	private int teamLevel;

	/**
	 * This property corresponds to db column <tt>CITY</tt>.
	 */
	private String city;

	/**
	 * This property corresponds to db column <tt>VOUCHER_LIMIT</tt>.
	 */
	private long voucherLimit;

	/**
	 * This property corresponds to db column <tt>TEAM_ALL_NAME</tt>.
	 */
	private String teamAllName;

	/**
	 * This property corresponds to db column <tt>CHARGE_PERSON_NAME</tt>.
	 */
	private String chargePersonName;

	/**
	 * This property corresponds to db column <tt>CHARGE_PERSON_CELL</tt>.
	 */
	private String chargePersonCell;

	/**
	 * This property corresponds to db column <tt>TAX_NO</tt>.
	 */
	private String taxNo;

	/**
	 * This property corresponds to db column <tt>BANK_NAME</tt>.
	 */
	private String bankName;

	/**
	 * This property corresponds to db column <tt>BANK_NO</tt>.
	 */
	private String bankNo;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>CREATER</tt>.
	 */
	private String creater;

	/**
	 * This property corresponds to db column <tt>MODIFIER</tt>.
	 */
	private String modifier;

	/**
	 * This property corresponds to db column <tt>BUSINESS_CATEGORY</tt>.
	 */
	private String businessCategory;

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
     * Getter method for property <tt>teamId</tt>.
     *
     * @return property value of teamId
     */
	public String getTeamId() {
		return teamId;
	}
	
	/**
	 * Setter method for property <tt>teamId</tt>.
	 * 
	 * @param teamId value to be assigned to property teamId
     */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

    /**
     * Getter method for property <tt>teamName</tt>.
     *
     * @return property value of teamName
     */
	public String getTeamName() {
		return teamName;
	}
	
	/**
	 * Setter method for property <tt>teamName</tt>.
	 * 
	 * @param teamName value to be assigned to property teamName
     */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

    /**
     * Getter method for property <tt>teamType</tt>.
     *
     * @return property value of teamType
     */
	public String getTeamType() {
		return teamType;
	}
	
	/**
	 * Setter method for property <tt>teamType</tt>.
	 * 
	 * @param teamType value to be assigned to property teamType
     */
	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

    /**
     * Getter method for property <tt>topTeamId</tt>.
     *
     * @return property value of topTeamId
     */
	public String getTopTeamId() {
		return topTeamId;
	}
	
	/**
	 * Setter method for property <tt>topTeamId</tt>.
	 * 
	 * @param topTeamId value to be assigned to property topTeamId
     */
	public void setTopTeamId(String topTeamId) {
		this.topTeamId = topTeamId;
	}

    /**
     * Getter method for property <tt>isTop</tt>.
     *
     * @return property value of isTop
     */
	public String getIsTop() {
		return isTop;
	}
	
	/**
	 * Setter method for property <tt>isTop</tt>.
	 * 
	 * @param isTop value to be assigned to property isTop
     */
	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

    /**
     * Getter method for property <tt>cell</tt>.
     *
     * @return property value of cell
     */
	public String getCell() {
		return cell;
	}
	
	/**
	 * Setter method for property <tt>cell</tt>.
	 * 
	 * @param cell value to be assigned to property cell
     */
	public void setCell(String cell) {
		this.cell = cell;
	}

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Setter method for property <tt>address</tt>.
	 * 
	 * @param address value to be assigned to property address
     */
	public void setAddress(String address) {
		this.address = address;
	}

    /**
     * Getter method for property <tt>longitude</tt>.
     *
     * @return property value of longitude
     */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Setter method for property <tt>longitude</tt>.
	 * 
	 * @param longitude value to be assigned to property longitude
     */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    /**
     * Getter method for property <tt>latitude</tt>.
     *
     * @return property value of latitude
     */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * Setter method for property <tt>latitude</tt>.
	 * 
	 * @param latitude value to be assigned to property latitude
     */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

    /**
     * Getter method for property <tt>favourableActive</tt>.
     *
     * @return property value of favourableActive
     */
	public String getFavourableActive() {
		return favourableActive;
	}
	
	/**
	 * Setter method for property <tt>favourableActive</tt>.
	 * 
	 * @param favourableActive value to be assigned to property favourableActive
     */
	public void setFavourableActive(String favourableActive) {
		this.favourableActive = favourableActive;
	}

    /**
     * Getter method for property <tt>delFlg</tt>.
     *
     * @return property value of delFlg
     */
	public String getDelFlg() {
		return delFlg;
	}
	
	/**
	 * Setter method for property <tt>delFlg</tt>.
	 * 
	 * @param delFlg value to be assigned to property delFlg
     */
	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

    /**
     * Getter method for property <tt>erpNo</tt>.
     *
     * @return property value of erpNo
     */
	public String getErpNo() {
		return erpNo;
	}
	
	/**
	 * Setter method for property <tt>erpNo</tt>.
	 * 
	 * @param erpNo value to be assigned to property erpNo
     */
	public void setErpNo(String erpNo) {
		this.erpNo = erpNo;
	}

    /**
     * Getter method for property <tt>teamLevel</tt>.
     *
     * @return property value of teamLevel
     */
	public int getTeamLevel() {
		return teamLevel;
	}
	
	/**
	 * Setter method for property <tt>teamLevel</tt>.
	 * 
	 * @param teamLevel value to be assigned to property teamLevel
     */
	public void setTeamLevel(int teamLevel) {
		this.teamLevel = teamLevel;
	}

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return property value of city
     */
	public String getCity() {
		return city;
	}
	
	/**
	 * Setter method for property <tt>city</tt>.
	 * 
	 * @param city value to be assigned to property city
     */
	public void setCity(String city) {
		this.city = city;
	}

    /**
     * Getter method for property <tt>voucherLimit</tt>.
     *
     * @return property value of voucherLimit
     */
	public long getVoucherLimit() {
		return voucherLimit;
	}
	
	/**
	 * Setter method for property <tt>voucherLimit</tt>.
	 * 
	 * @param voucherLimit value to be assigned to property voucherLimit
     */
	public void setVoucherLimit(long voucherLimit) {
		this.voucherLimit = voucherLimit;
	}

    /**
     * Getter method for property <tt>teamAllName</tt>.
     *
     * @return property value of teamAllName
     */
	public String getTeamAllName() {
		return teamAllName;
	}
	
	/**
	 * Setter method for property <tt>teamAllName</tt>.
	 * 
	 * @param teamAllName value to be assigned to property teamAllName
     */
	public void setTeamAllName(String teamAllName) {
		this.teamAllName = teamAllName;
	}

    /**
     * Getter method for property <tt>chargePersonName</tt>.
     *
     * @return property value of chargePersonName
     */
	public String getChargePersonName() {
		return chargePersonName;
	}
	
	/**
	 * Setter method for property <tt>chargePersonName</tt>.
	 * 
	 * @param chargePersonName value to be assigned to property chargePersonName
     */
	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}

    /**
     * Getter method for property <tt>chargePersonCell</tt>.
     *
     * @return property value of chargePersonCell
     */
	public String getChargePersonCell() {
		return chargePersonCell;
	}
	
	/**
	 * Setter method for property <tt>chargePersonCell</tt>.
	 * 
	 * @param chargePersonCell value to be assigned to property chargePersonCell
     */
	public void setChargePersonCell(String chargePersonCell) {
		this.chargePersonCell = chargePersonCell;
	}

    /**
     * Getter method for property <tt>taxNo</tt>.
     *
     * @return property value of taxNo
     */
	public String getTaxNo() {
		return taxNo;
	}
	
	/**
	 * Setter method for property <tt>taxNo</tt>.
	 * 
	 * @param taxNo value to be assigned to property taxNo
     */
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

    /**
     * Getter method for property <tt>bankName</tt>.
     *
     * @return property value of bankName
     */
	public String getBankName() {
		return bankName;
	}
	
	/**
	 * Setter method for property <tt>bankName</tt>.
	 * 
	 * @param bankName value to be assigned to property bankName
     */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

    /**
     * Getter method for property <tt>bankNo</tt>.
     *
     * @return property value of bankNo
     */
	public String getBankNo() {
		return bankNo;
	}
	
	/**
	 * Setter method for property <tt>bankNo</tt>.
	 * 
	 * @param bankNo value to be assigned to property bankNo
     */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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
     * Getter method for property <tt>modifier</tt>.
     *
     * @return property value of modifier
     */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * Setter method for property <tt>modifier</tt>.
	 * 
	 * @param modifier value to be assigned to property modifier
     */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

    /**
     * Getter method for property <tt>businessCategory</tt>.
     *
     * @return property value of businessCategory
     */
	public String getBusinessCategory() {
		return businessCategory;
	}
	
	/**
	 * Setter method for property <tt>businessCategory</tt>.
	 * 
	 * @param businessCategory value to be assigned to property businessCategory
     */
	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}
}
