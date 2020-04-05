/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.ProductCategoryDAO;

import com.onway.fyapp.common.dal.dataobject.ProductCategoryDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.ProductCategoryDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_product_category.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisProductCategoryDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisProductCategoryDAO extends SqlMapClientDaoSupport implements ProductCategoryDAO {
	/**
	 *  Query DB table <tt>hqyt_product_category</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_product_category</tt>
	 *
	 *	@param proId
	 *	@param groupId
	 *	@return List<ProductCategoryDO>
	 *	@throws DataAccessException
	 */	 
    public  List<ProductCategoryDO>   selectProCate(String proId, String groupId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("proId", proId);
        param.put("groupId", groupId);

        return getSqlMapClientTemplate().queryForList("MS-PRODUCT-CATEGORY-SELECT-PRO-CATE", param);

    }

	/**
	 *  Delete records from DB table <tt>hqyt_product_category</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_product_category where (PRODUCT_ID = ?)</tt>
	 *
	 *	@param productId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteProCate(String productId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-PRODUCT-CATEGORY-DELETE-PRO-CATE", productId);
    }

	/**
	 *  Insert one <tt>ProductCategoryDO</tt> object to DB table <tt>hqyt_product_category</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_product_category(PRODUCT_ID,CATEGORY_ID,GROUP_ID,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param productCategory
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   newProCate(ProductCategoryDO productCategory) throws DataAccessException {
    	if (productCategory == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PRODUCT-CATEGORY-NEW-PRO-CATE", productCategory);

        return productCategory.getId();
    }

	/**
	 *  Query DB table <tt>hqyt_product_category</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_product_category</tt>
	 *
	 *	@param proId
	 *	@return List<String>
	 *	@throws DataAccessException
	 */	 
    public  List<String>   selectProCateGroupByGroupId(String proId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("proId", proId);

        return getSqlMapClientTemplate().queryForList("MS-PRODUCT-CATEGORY-SELECT-PRO-CATE-GROUP-BY-GROUP-ID", param);

    }

}