/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.WorkCheckDAO;

import java.util.Date;
import com.onway.fyapp.common.dal.dataobject.WorkCheckDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.WorkCheckDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_work_check.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisWorkCheckDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisWorkCheckDAO extends SqlMapClientDaoSupport implements WorkCheckDAO {
	/**
	 *  Query DB table <tt>hqyt_work_check</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_work_check</tt>
	 *
	 *	@param userMsg
	 *	@param teamMsg
	 *	@param type
	 *	@param startDate
	 *	@param endDate
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   queryAllWorkCheck(String userMsg, String teamMsg, String type, Date startDate, Date endDate, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userMsg", userMsg);
        param.put("teamMsg", teamMsg);
        param.put("type", type);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-WORK-CHECK-QUERY-ALL-WORK-CHECK", param);

    }

	/**
	 *  Query DB table <tt>hqyt_work_check</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_work_check</tt>
	 *
	 *	@param userMsg
	 *	@param teamMsg
	 *	@param type
	 *	@param startDate
	 *	@param endDate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryAllWorkCheckCount(String userMsg, String teamMsg, String type, Date startDate, Date endDate) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userMsg", userMsg);
        param.put("teamMsg", teamMsg);
        param.put("type", type);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-WORK-CHECK-QUERY-ALL-WORK-CHECK-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

}