/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;


/**
 * A data object class directly models database table <tt>sys_zjm</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sys_zjm.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: SysZjmDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class SysZjmDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>id</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>hanzi</tt>.
	 */
	private String hanzi;

	/**
	 * This property corresponds to db column <tt>quanpin</tt>.
	 */
	private String quanpin;

	/**
	 * This property corresponds to db column <tt>zjm</tt>.
	 */
	private String zjm;

	/**
	 * This property corresponds to db column <tt>wbzjm</tt>.
	 */
	private String wbzjm;

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
     * Getter method for property <tt>hanzi</tt>.
     *
     * @return property value of hanzi
     */
	public String getHanzi() {
		return hanzi;
	}
	
	/**
	 * Setter method for property <tt>hanzi</tt>.
	 * 
	 * @param hanzi value to be assigned to property hanzi
     */
	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}

    /**
     * Getter method for property <tt>quanpin</tt>.
     *
     * @return property value of quanpin
     */
	public String getQuanpin() {
		return quanpin;
	}
	
	/**
	 * Setter method for property <tt>quanpin</tt>.
	 * 
	 * @param quanpin value to be assigned to property quanpin
     */
	public void setQuanpin(String quanpin) {
		this.quanpin = quanpin;
	}

    /**
     * Getter method for property <tt>zjm</tt>.
     *
     * @return property value of zjm
     */
	public String getZjm() {
		return zjm;
	}
	
	/**
	 * Setter method for property <tt>zjm</tt>.
	 * 
	 * @param zjm value to be assigned to property zjm
     */
	public void setZjm(String zjm) {
		this.zjm = zjm;
	}

    /**
     * Getter method for property <tt>wbzjm</tt>.
     *
     * @return property value of wbzjm
     */
	public String getWbzjm() {
		return wbzjm;
	}
	
	/**
	 * Setter method for property <tt>wbzjm</tt>.
	 * 
	 * @param wbzjm value to be assigned to property wbzjm
     */
	public void setWbzjm(String wbzjm) {
		this.wbzjm = wbzjm;
	}
}
