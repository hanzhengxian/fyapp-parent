/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.AddressDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_address</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_address.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: AddressDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface AddressDAO {
	/**
	 *  Query DB table <tt>hqyt_address</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_address</tt>
	 *
	 *	@param userId
	 *	@param delFlg
	 *	@param startRow
	 *	@param pageSize
	 *	@return List<AddressDO>
	 *	@throws DataAccessException
	 */	 
    public  List<AddressDO>   queryAddressList(String userId, String delFlg, Integer startRow, Integer pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_address</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_address</tt>
	 *
	 *	@param userId
	 *	@param delFlg
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryAddressListCount(String userId, String delFlg) throws DataAccessException;

}