/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.PlanBirthDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.Date;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_plan_birth</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_plan_birth.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: PlanBirthDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface PlanBirthDAO {
	/**
	 *  Query DB table <tt>hqyt_plan_birth</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_birth</tt>
	 *
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   queryList() throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_plan_birth</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_plan_birth</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryListCount() throws DataAccessException;

	/**
	 *  Insert one <tt>PlanBirthDO</tt> object to DB table <tt>hqyt_plan_birth</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_plan_birth(BIRTH_ID,TYPE,PLAN_TYPE,AMOUNT_TYPE,AMOUNT,RATE,END_DATE,STATUS,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param planBirth
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(PlanBirthDO planBirth) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_plan_birth</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_birth set PLAN_TYPE=?, AMOUNT_TYPE=?, AMOUNT=?, RATE=?, END_DATE=?, STATUS=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (BIRTH_ID = ?)</tt>
	 *
	 *	@param planType
	 *	@param amountType
	 *	@param amount
	 *	@param rate
	 *	@param endDate
	 *	@param status
	 *	@param modifier
	 *	@param birthId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(String planType, String amountType, Money amount, double rate, Date endDate, String status, String modifier, String birthId) throws DataAccessException;

}