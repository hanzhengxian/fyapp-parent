/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_chat_record</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_chat_record.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ChatRecordDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class ChatRecordDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>CHAT_ID</tt>.
	 */
	private String chatId;

	/**
	 * This property corresponds to db column <tt>CHAT_COMMNET</tt>.
	 */
	private String chatCommnet;

	/**
	 * This property corresponds to db column <tt>CHAT_OBJ</tt>.
	 */
	private String chatObj;

	/**
	 * This property corresponds to db column <tt>CHAT_TYPE</tt>.
	 */
	private String chatType;

	/**
	 * This property corresponds to db column <tt>CHAT_TIME</tt>.
	 */
	private Date chatTime;

	/**
	 * This property corresponds to db column <tt>CHAT_STATUS</tt>.
	 */
	private String chatStatus;

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
     * Getter method for property <tt>chatId</tt>.
     *
     * @return property value of chatId
     */
	public String getChatId() {
		return chatId;
	}
	
	/**
	 * Setter method for property <tt>chatId</tt>.
	 * 
	 * @param chatId value to be assigned to property chatId
     */
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

    /**
     * Getter method for property <tt>chatCommnet</tt>.
     *
     * @return property value of chatCommnet
     */
	public String getChatCommnet() {
		return chatCommnet;
	}
	
	/**
	 * Setter method for property <tt>chatCommnet</tt>.
	 * 
	 * @param chatCommnet value to be assigned to property chatCommnet
     */
	public void setChatCommnet(String chatCommnet) {
		this.chatCommnet = chatCommnet;
	}

    /**
     * Getter method for property <tt>chatObj</tt>.
     *
     * @return property value of chatObj
     */
	public String getChatObj() {
		return chatObj;
	}
	
	/**
	 * Setter method for property <tt>chatObj</tt>.
	 * 
	 * @param chatObj value to be assigned to property chatObj
     */
	public void setChatObj(String chatObj) {
		this.chatObj = chatObj;
	}

    /**
     * Getter method for property <tt>chatType</tt>.
     *
     * @return property value of chatType
     */
	public String getChatType() {
		return chatType;
	}
	
	/**
	 * Setter method for property <tt>chatType</tt>.
	 * 
	 * @param chatType value to be assigned to property chatType
     */
	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

    /**
     * Getter method for property <tt>chatTime</tt>.
     *
     * @return property value of chatTime
     */
	public Date getChatTime() {
		return chatTime;
	}
	
	/**
	 * Setter method for property <tt>chatTime</tt>.
	 * 
	 * @param chatTime value to be assigned to property chatTime
     */
	public void setChatTime(Date chatTime) {
		this.chatTime = chatTime;
	}

    /**
     * Getter method for property <tt>chatStatus</tt>.
     *
     * @return property value of chatStatus
     */
	public String getChatStatus() {
		return chatStatus;
	}
	
	/**
	 * Setter method for property <tt>chatStatus</tt>.
	 * 
	 * @param chatStatus value to be assigned to property chatStatus
     */
	public void setChatStatus(String chatStatus) {
		this.chatStatus = chatStatus;
	}
}
