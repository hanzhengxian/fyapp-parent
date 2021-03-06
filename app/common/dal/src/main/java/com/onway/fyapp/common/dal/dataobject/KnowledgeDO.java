/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.dataobject;

import com.onway.fyapp.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>hqyt_knowledge</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_knowledge.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: KnowledgeDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class KnowledgeDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>KNOW_ID</tt>.
	 */
	private String knowId;

	/**
	 * This property corresponds to db column <tt>TYPE</tt>.
	 */
	private String type;

	/**
	 * This property corresponds to db column <tt>TITLE</tt>.
	 */
	private String title;

	/**
	 * This property corresponds to db column <tt>SUB_TITLE</tt>.
	 */
	private String subTitle;

	/**
	 * This property corresponds to db column <tt>CONTENT</tt>.
	 */
	private String content;

	/**
	 * This property corresponds to db column <tt>HEAD_IMG_URL</tt>.
	 */
	private String headImgUrl;

	/**
	 * This property corresponds to db column <tt>PRAISE_COUNT</tt>.
	 */
	private int praiseCount;

	/**
	 * This property corresponds to db column <tt>COMMENT_COUNT</tt>.
	 */
	private int commentCount;

	/**
	 * This property corresponds to db column <tt>DEL_FLG</tt>.
	 */
	private String delFlg;

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
     * Getter method for property <tt>knowId</tt>.
     *
     * @return property value of knowId
     */
	public String getKnowId() {
		return knowId;
	}
	
	/**
	 * Setter method for property <tt>knowId</tt>.
	 * 
	 * @param knowId value to be assigned to property knowId
     */
	public void setKnowId(String knowId) {
		this.knowId = knowId;
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
     * Getter method for property <tt>title</tt>.
     *
     * @return property value of title
     */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter method for property <tt>title</tt>.
	 * 
	 * @param title value to be assigned to property title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Getter method for property <tt>subTitle</tt>.
     *
     * @return property value of subTitle
     */
	public String getSubTitle() {
		return subTitle;
	}
	
	/**
	 * Setter method for property <tt>subTitle</tt>.
	 * 
	 * @param subTitle value to be assigned to property subTitle
     */
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

    /**
     * Getter method for property <tt>content</tt>.
     *
     * @return property value of content
     */
	public String getContent() {
		return content;
	}
	
	/**
	 * Setter method for property <tt>content</tt>.
	 * 
	 * @param content value to be assigned to property content
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * Getter method for property <tt>headImgUrl</tt>.
     *
     * @return property value of headImgUrl
     */
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	
	/**
	 * Setter method for property <tt>headImgUrl</tt>.
	 * 
	 * @param headImgUrl value to be assigned to property headImgUrl
     */
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

    /**
     * Getter method for property <tt>praiseCount</tt>.
     *
     * @return property value of praiseCount
     */
	public int getPraiseCount() {
		return praiseCount;
	}
	
	/**
	 * Setter method for property <tt>praiseCount</tt>.
	 * 
	 * @param praiseCount value to be assigned to property praiseCount
     */
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

    /**
     * Getter method for property <tt>commentCount</tt>.
     *
     * @return property value of commentCount
     */
	public int getCommentCount() {
		return commentCount;
	}
	
	/**
	 * Setter method for property <tt>commentCount</tt>.
	 * 
	 * @param commentCount value to be assigned to property commentCount
     */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
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
}
