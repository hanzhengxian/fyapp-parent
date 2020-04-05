/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.OrderRefundLogDO;
import org.springframework.dao.DataAccessException;
import com.onway.common.lang.Money;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_order_refund_logs</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_order_refund_logs.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: OrderRefundLogDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface OrderRefundLogDAO {
	/**
	 *  Query DB table <tt>hqyt_order_refund_logs</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ORDER_NO, ORDER_ID, REFUND_NO, REFUND_TYPE, AMOUNT, STATUS, ERROR_MSG, RESULT_MSG, NOTIFY_MSG, RETURN_ID, GMT_CREATE, GMT_MODIFIED from hqyt_order_refund_logs where (REFUND_NO = ?)</tt>
	 *
	 *	@param refundNo
	 *	@return OrderRefundLogDO
	 *	@throws DataAccessException
	 */	 
    public  OrderRefundLogDO   selectByRefundNo(String refundNo) throws DataAccessException;

	/**
	 *  Insert one <tt>OrderRefundLogDO</tt> object to DB table <tt>hqyt_order_refund_logs</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_order_refund_logs(ORDER_NO,ORDER_ID,REFUND_NO,REFUND_TYPE,AMOUNT,STATUS,ERROR_MSG,RESULT_MSG,NOTIFY_MSG,RETURN_ID,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param orderRefundLog
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   newOrderRefund(OrderRefundLogDO orderRefundLog) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_order_refund_logs</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_order_refund_logs set STATUS=?, ERROR_MSG=?, RESULT_MSG=?, NOTIFY_MSG=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (REFUND_NO = ?)</tt>
	 *
	 *	@param orderRefundLog
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateOrderRefund(OrderRefundLogDO orderRefundLog) throws DataAccessException;

}