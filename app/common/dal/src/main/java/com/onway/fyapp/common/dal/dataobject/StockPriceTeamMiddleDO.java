/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_stock_price_team_middle</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_stock_price_team_middle.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: StockPriceTeamMiddleDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class StockPriceTeamMiddleDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>ERP_NO_ATTR</tt>.
	 */
	private String erpNoAttr;

	/**
	 * This property corresponds to db column <tt>ERP_NO_TEAM</tt>.
	 */
	private String erpNoTeam;

	/**
	 * This property corresponds to db column <tt>STOCK</tt>.
	 */
	private double stock;

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

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
     * Getter method for property <tt>erpNoAttr</tt>.
     *
     * @return property value of erpNoAttr
     */
	public String getErpNoAttr() {
		return erpNoAttr;
	}
	
	/**
	 * Setter method for property <tt>erpNoAttr</tt>.
	 * 
	 * @param erpNoAttr value to be assigned to property erpNoAttr
     */
	public void setErpNoAttr(String erpNoAttr) {
		this.erpNoAttr = erpNoAttr;
	}

    /**
     * Getter method for property <tt>erpNoTeam</tt>.
     *
     * @return property value of erpNoTeam
     */
	public String getErpNoTeam() {
		return erpNoTeam;
	}
	
	/**
	 * Setter method for property <tt>erpNoTeam</tt>.
	 * 
	 * @param erpNoTeam value to be assigned to property erpNoTeam
     */
	public void setErpNoTeam(String erpNoTeam) {
		this.erpNoTeam = erpNoTeam;
	}

    /**
     * Getter method for property <tt>stock</tt>.
     *
     * @return property value of stock
     */
	public double getStock() {
		return stock;
	}
	
	/**
	 * Setter method for property <tt>stock</tt>.
	 * 
	 * @param stock value to be assigned to property stock
     */
	public void setStock(double stock) {
		this.stock = stock;
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
