/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.StockPriceTeamMiddleDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_stock_price_team_middle</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_stock_price_team_middle.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: StockPriceTeamMiddleDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface StockPriceTeamMiddleDAO {
	/**
	 *  Query DB table <tt>hqyt_stock_price_team_middle</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_stock_price_team_middle where (STATUS = "0")</tt>
	 *
	 *	@return List<StockPriceTeamMiddleDO>
	 *	@throws DataAccessException
	 */	 
    public  List<StockPriceTeamMiddleDO>   queryAll() throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_stock_price_team_middle</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_stock_price_team_middle set STATUS="1", GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateStatus(int id) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_stock_price_team_middle</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_stock_price_team_middle set STATUS="1", GMT_MODIFIED=CURRENT_TIMESTAMP</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateStatusAll() throws DataAccessException;

}