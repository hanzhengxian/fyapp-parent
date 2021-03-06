/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_order_rebate</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_order_rebate.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderRebateDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface OrderRebateDAO {
	/**
	 *  Query DB table <tt>hqyt_order_rebate</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_order_rebate where ((CHILD_ORDER_ID = ?) AND (REBATE_TYPE = "2"))</tt>
	 *
	 *	@param childOrderId
	 *	@return OrderRebateDO
	 *	@throws DataAccessException
	 */	 
    public  OrderRebateDO   selectByChildOrderId(String childOrderId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_order_rebate</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_order_rebate</tt>
	 *
	 *	@param childOrderId
	 *	@param offset
	 *	@param limit
	 *	@return List<OrderRebateDO>
	 *	@throws DataAccessException
	 */	 
    public  List<OrderRebateDO>   getOrderRebateRecord(String childOrderId, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_order_rebate</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_order_rebate</tt>
	 *
	 *	@param childOrderId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   getOrderRebateRecordCount(String childOrderId) throws DataAccessException;

	/**
	 *  Insert one <tt>OrderRebateDO</tt> object to DB table <tt>hqyt_order_rebate</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_order_rebate(CHILD_ORDER_ID,REBATE_TYPE,HU_AMOUNT,HU_TEMP_AMOUNT,POINT_AMOUNT,POINT_TEMP_AMOUNT,END_DATE,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param orderRebate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   newOrderRebate(OrderRebateDO orderRebate) throws DataAccessException;

}