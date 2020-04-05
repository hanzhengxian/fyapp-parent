/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import java.util.Date;
import java.util.List;
import com.onway.fyapp.common.dal.dataobject.DiscountUserDO;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_discount_user</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_discount_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: DiscountUserDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface DiscountUserDAO {
	/**
	 *  Query DB table <tt>hqyt_discount_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_discount_user</tt>
	 *
	 *	@param discountId
	 *	@param startDate
	 *	@param endDate
	 *	@param useTypes
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   queryList(String discountId, Date startDate, Date endDate, List useTypes, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_discount_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_discount_user</tt>
	 *
	 *	@param discountId
	 *	@param startDate
	 *	@param endDate
	 *	@param useTypes
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryListCount(String discountId, Date startDate, Date endDate, List useTypes) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_discount_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_discount_user set STATUS='2' where (ID = 0)</tt>
	 *
	 *	@param status
	 *	@param dateTime
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   discountOutTime(String status, Date dateTime) throws DataAccessException;

}