/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_product_type</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_product_type.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ProductTypeDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class ProductTypeDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>TYPE_ID</tt>.
	 */
	private String typeId;

	/**
	 * This property corresponds to db column <tt>TYPE_NAME</tt>.
	 */
	private String typeName;

	/**
	 * This property corresponds to db column <tt>DEL_FLG</tt>.
	 */
	private String delFlg;

	/**
	 * This property corresponds to db column <tt>RANK</tt>.
	 */
	private int rank;

	/**
	 * This property corresponds to db column <tt>SHOW_PC</tt>.
	 */
	private String showPc;

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
     * Getter method for property <tt>typeId</tt>.
     *
     * @return property value of typeId
     */
	public String getTypeId() {
		return typeId;
	}
	
	/**
	 * Setter method for property <tt>typeId</tt>.
	 * 
	 * @param typeId value to be assigned to property typeId
     */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

    /**
     * Getter method for property <tt>typeName</tt>.
     *
     * @return property value of typeName
     */
	public String getTypeName() {
		return typeName;
	}
	
	/**
	 * Setter method for property <tt>typeName</tt>.
	 * 
	 * @param typeName value to be assigned to property typeName
     */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
     * Getter method for property <tt>rank</tt>.
     *
     * @return property value of rank
     */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Setter method for property <tt>rank</tt>.
	 * 
	 * @param rank value to be assigned to property rank
     */
	public void setRank(int rank) {
		this.rank = rank;
	}

    /**
     * Getter method for property <tt>showPc</tt>.
     *
     * @return property value of showPc
     */
	public String getShowPc() {
		return showPc;
	}
	
	/**
	 * Setter method for property <tt>showPc</tt>.
	 * 
	 * @param showPc value to be assigned to property showPc
     */
	public void setShowPc(String showPc) {
		this.showPc = showPc;
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
