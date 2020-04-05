/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.BannerDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_banner</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_banner.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: BannerDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface BannerDAO {
	/**
	 *  Query DB table <tt>hqyt_banner</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_banner where (1 = 1)</tt>
	 *
	 *	@param bannerType
	 *	@param bannerPosition
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectBanner(String bannerType, String bannerPosition, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Insert one <tt>BannerDO</tt> object to DB table <tt>hqyt_banner</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_banner(BANNER_TYPE,BANNER_IMG,BANNER_DESC,BANNER_CONTENT,BANNER_POSITION,RANK,STATUS,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param banner
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertBanner(BannerDO banner) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_banner</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_banner set BANNER_TYPE=?, BANNER_IMG=?, BANNER_DESC=?, BANNER_CONTENT=?, BANNER_POSITION=?, RANK=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param banner
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateBanner(BannerDO banner) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_banner</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_banner set STATUS='UNABLED', MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteBanner(String modifier, int id) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_banner</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_banner</tt>
	 *
	 *	@param bannerType
	 *	@param bannerPosition
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryLists(String bannerType, String bannerPosition) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_banner</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_banner</tt>
	 *
	 *	@param bannerId
	 *	@return BannerDO
	 *	@throws DataAccessException
	 */	 
    public  BannerDO   bannerDetailsQuery(Integer bannerId) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_banner</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_banner set BANNER_CONTENT=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param bannerContent
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   modifyLinkGoodsOrShop(String bannerContent, String modifier, int id) throws DataAccessException;

}