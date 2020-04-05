/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_work_check</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_work_check.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: WorkCheckDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class WorkCheckDO extends BaseDO {
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
	 * This property corresponds to db column <tt>TEAM_ID</tt>.
	 */
	private String teamId;

	/**
	 * This property corresponds to db column <tt>TEAM_GPS</tt>.
	 */
	private String teamGps;

	/**
	 * This property corresponds to db column <tt>USER_GPS</tt>.
	 */
	private String userGps;

	/**
	 * This property corresponds to db column <tt>DISTANCE</tt>.
	 */
	private double distance;

	/**
	 * This property corresponds to db column <tt>WORK_DAY</tt>.
	 */
	private Date workDay;

	/**
	 * This property corresponds to db column <tt>TYPE</tt>.
	 */
	private String type;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

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
     * Getter method for property <tt>teamGps</tt>.
     *
     * @return property value of teamGps
     */
	public String getTeamGps() {
		return teamGps;
	}
	
	/**
	 * Setter method for property <tt>teamGps</tt>.
	 * 
	 * @param teamGps value to be assigned to property teamGps
     */
	public void setTeamGps(String teamGps) {
		this.teamGps = teamGps;
	}

    /**
     * Getter method for property <tt>userGps</tt>.
     *
     * @return property value of userGps
     */
	public String getUserGps() {
		return userGps;
	}
	
	/**
	 * Setter method for property <tt>userGps</tt>.
	 * 
	 * @param userGps value to be assigned to property userGps
     */
	public void setUserGps(String userGps) {
		this.userGps = userGps;
	}

    /**
     * Getter method for property <tt>distance</tt>.
     *
     * @return property value of distance
     */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Setter method for property <tt>distance</tt>.
	 * 
	 * @param distance value to be assigned to property distance
     */
	public void setDistance(double distance) {
		this.distance = distance;
	}

    /**
     * Getter method for property <tt>workDay</tt>.
     *
     * @return property value of workDay
     */
	public Date getWorkDay() {
		return workDay;
	}
	
	/**
	 * Setter method for property <tt>workDay</tt>.
	 * 
	 * @param workDay value to be assigned to property workDay
     */
	public void setWorkDay(Date workDay) {
		this.workDay = workDay;
	}

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type value to be assigned to property type
     */
	public void setType(String type) {
		this.type = type;
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
