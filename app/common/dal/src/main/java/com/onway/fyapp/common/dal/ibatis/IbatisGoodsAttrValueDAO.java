/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.GoodsAttrValueDAO;

import com.onway.fyapp.common.dal.dataobject.GoodsAttrValueDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.GoodsAttrValueDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_goods_attr_value.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisGoodsAttrValueDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisGoodsAttrValueDAO extends SqlMapClientDaoSupport implements GoodsAttrValueDAO {
	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ATTR_ID, VALUE, SORT_NUM, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr_value where (ATTR_ID = ?)</tt>
	 *
	 *	@param attrId
	 *	@return List<GoodsAttrValueDO>
	 *	@throws DataAccessException
	 */	 
    public  List<GoodsAttrValueDO>   selectAttrByAttrNo(String attrId) throws DataAccessException {

 
        return getSqlMapClientTemplate().queryForList("MS-GOODS-ATTR-VALUE-SELECT-ATTR-BY-ATTR-NO", attrId);

    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ATTR_ID, VALUE, SORT_NUM, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr_value where ((1 = 1) AND (ATTR_ID = ?) AND (STATUS = ?))</tt>
	 *
	 *	@param attrId
	 *	@param status
	 *	@return List<GoodsAttrValueDO>
	 *	@throws DataAccessException
	 */	 
    public  List<GoodsAttrValueDO>   selectAttrByAttrNoAndSta(String attrId, String status) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("attrId", attrId);
        param.put("status", status);

        return getSqlMapClientTemplate().queryForList("MS-GOODS-ATTR-VALUE-SELECT-ATTR-BY-ATTR-NO-AND-STA", param);

    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from hqyt_goods_attr_value where ((ATTR_ID = ?) AND (VALUE = ?))</tt>
	 *
	 *	@param attrId
	 *	@param value
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public  long   selectCountByAttrNoValue(String attrId, String value) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("attrId", attrId);
        param.put("value", value);

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-COUNT-BY-ATTR-NO-VALUE", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_goods_attr_value where ((ATTR_ID = ?) AND (VALUE = ?))</tt>
	 *
	 *	@param attrId
	 *	@param value
	 *	@return GoodsAttrValueDO
	 *	@throws DataAccessException
	 */	 
    public  GoodsAttrValueDO   selectByAttrNoValue(String attrId, String value) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("attrId", attrId);
        param.put("value", value);

	        return (GoodsAttrValueDO) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-BY-ATTR-NO-VALUE", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ATTR_ID, VALUE, SORT_NUM, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr_value where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return GoodsAttrValueDO
	 *	@throws DataAccessException
	 */	 
    public  GoodsAttrValueDO   selectAttrById(int id) throws DataAccessException {

        Integer param = new Integer(id);

	        return (GoodsAttrValueDO) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-ATTR-BY-ID", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ATTR_ID, VALUE, SORT_NUM, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr_value where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return HashMap
	 *	@throws DataAccessException
	 */	 
    public  Map<String,Object>  selectById(int id) throws DataAccessException {

        Integer param = new Integer(id);

	        return (HashMap) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-BY-ID", param);
		
    }

	/**
	 *  Update DB table <tt>hqyt_goods_attr_value</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_goods_attr_value set GMT_MODIFIED=CURRENT_TIMESTAMP</tt>
	 *
	 *	@param queryInfo
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(GoodsAttrValueDO queryInfo, String userId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("queryInfo", queryInfo);
        param.put("userId", userId);

        return getSqlMapClientTemplate().update("MS-GOODS-ATTR-VALUE-UPDATE", param);
    }

	/**
	 *  Delete records from DB table <tt>hqyt_goods_attr_value</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_goods_attr_value where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteById(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-GOODS-ATTR-VALUE-DELETE-BY-ID", param);
    }

	/**
	 *  Insert one <tt>GoodsAttrValueDO</tt> object to DB table <tt>hqyt_goods_attr_value</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_goods_attr_value(ATTR_ID,VALUE,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param goodsAttrValue
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(GoodsAttrValueDO goodsAttrValue) throws DataAccessException {
    	if (goodsAttrValue == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-GOODS-ATTR-VALUE-INSERT", goodsAttrValue);

        return goodsAttrValue.getId();
    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_goods_attr_value</tt>
	 *
	 *	@param attrId
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectPage(String attrId, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("attrId", attrId);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-GOODS-ATTR-VALUE-SELECT-PAGE", param);

    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from hqyt_goods_attr_value</tt>
	 *
	 *	@param attrId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectPageCount(String attrId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("attrId", attrId);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-PAGE-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Query DB table <tt>hqyt_goods_attr_value</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, ATTR_ID, VALUE, SORT_NUM, STATUS, MEMO, GMT_CREATE, CREATER, GMT_MODIFIED, MODIFIER from hqyt_goods_attr_value where ((VALUE = ?) AND (ATTR_ID = ?))</tt>
	 *
	 *	@param value
	 *	@param attrId
	 *	@return GoodsAttrValueDO
	 *	@throws DataAccessException
	 */	 
    public  GoodsAttrValueDO   selectAttrByValueObj(String value, String attrId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("value", value);
        param.put("attrId", attrId);

	        return (GoodsAttrValueDO) getSqlMapClientTemplate().queryForObject("MS-GOODS-ATTR-VALUE-SELECT-ATTR-BY-VALUE-OBJ", param);
		
    }

}