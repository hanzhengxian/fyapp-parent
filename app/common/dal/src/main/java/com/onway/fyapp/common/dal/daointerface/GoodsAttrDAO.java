/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.GoodsAttrDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_goods_attr</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_goods_attr.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: GoodsAttrDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface GoodsAttrDAO {
	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, CAT_NO, ATTR_NAME, SORT_NUM, NORMAL, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr where ((NORMAL = 1) AND (CAT_NO = ?))</tt>
	 *
	 *	@param catNo
	 *	@return List<GoodsAttrDO>
	 *	@throws DataAccessException
	 */	 
    public  List<GoodsAttrDO>   selectByCatNo(String catNo) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, CAT_NO, ATTR_NAME, SORT_NUM, NORMAL, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return GoodsAttrDO
	 *	@throws DataAccessException
	 */	 
    public  GoodsAttrDO   selectById(int id) throws DataAccessException;

	/**
	 *  Insert one <tt>GoodsAttrDO</tt> object to DB table <tt>hqyt_goods_attr</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_goods_attr(CAT_NO,ATTR_NAME,NORMAL,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param goodsAttr
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(GoodsAttrDO goodsAttr) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from hqyt_goods_attr where ((CAT_NO = ?) AND (ATTR_NAME = ?))</tt>
	 *
	 *	@param catNo
	 *	@param attrName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectByCatNoAttrName(String catNo, String attrName) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>hqyt_goods_attr</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_goods_attr where (CAT_NO = ?)</tt>
	 *
	 *	@param catNo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteByCatNo(String catNo) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>hqyt_goods_attr</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_goods_attr where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteByAttrNo(int id) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_goods_attr</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_goods_attr set GMT_MODIFIED=CURRENT_TIMESTAMP</tt>
	 *
	 *	@param queryInfo
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(GoodsAttrDO queryInfo, String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_goods_attr where (status = 'ENABLED')</tt>
	 *
	 *	@return List<GoodsAttrDO>
	 *	@throws DataAccessException
	 */	 
    public  List<GoodsAttrDO>   selectAll() throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_goods_attr</tt>
	 *
	 *	@param attrName
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectPage(String attrName, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from hqyt_goods_attr</tt>
	 *
	 *	@param attrName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectPageCount(String attrName) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_goods_attr</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_goods_attr where (ATTR_NAME = ?)</tt>
	 *
	 *	@param attrName
	 *	@return GoodsAttrDO
	 *	@throws DataAccessException
	 */	 
    public  GoodsAttrDO   selectByAttrNameObj(String attrName) throws DataAccessException;

}