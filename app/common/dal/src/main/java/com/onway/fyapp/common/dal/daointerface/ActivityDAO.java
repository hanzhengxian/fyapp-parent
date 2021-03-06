/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.ActivityDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_activity</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_activity.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ActivityDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface ActivityDAO {
	/**
	 *  Insert one <tt>ActivityDO</tt> object to DB table <tt>hqyt_activity</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_activity(TITLE,BACK_IMG,INFO,TYPE,URL,STATE,NUM,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param activity
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(ActivityDO activity) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_activity</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_activity set TITLE=?, BACK_IMG=?, INFO=?, TYPE=?, URL=?, STATE=?, NUM=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param activity
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(ActivityDO activity) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_activity</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, TITLE, BACK_IMG, INFO, TYPE, URL, STATE, NUM, GMT_CREATE, GMT_MODIFIED, CREATER, MODIFIER from hqyt_activity where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return ActivityDO
	 *	@throws DataAccessException
	 */	 
    public  ActivityDO   selectById(int id) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_activity</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_activity</tt>
	 *
	 *	@param title
	 *	@param type
	 *	@param state
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectByQuery(String title, String type, String state, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_activity</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from hqyt_activity</tt>
	 *
	 *	@param title
	 *	@param type
	 *	@param state
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectCountByQuery(String title, String type, String state) throws DataAccessException;

}