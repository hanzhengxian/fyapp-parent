/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SysReturnReasonDAO;

import com.onway.fyapp.common.dal.dataobject.SysReturnReasonDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SysReturnReasonDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sys_return_reason.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSysReturnReasonDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSysReturnReasonDAO extends SqlMapClientDaoSupport implements SysReturnReasonDAO {
	/**
	 *  Query DB table <tt>sys_return_reason</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_return_reason where (1 = 1)</tt>
	 *
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectAllReason(Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-SYS-RETURN-REASON-SELECT-ALL-REASON", param);

    }

	/**
	 *  Query DB table <tt>sys_return_reason</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from sys_return_reason where (1 = 1)</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectAllReasonCount() throws DataAccessException {


	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-SYS-RETURN-REASON-SELECT-ALL-REASON-COUNT", null);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>SysReturnReasonDO</tt> object to DB table <tt>sys_return_reason</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sys_return_reason(REASON,RANK,GMT_CREATE,GMT_MODIFIED) values (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param sysReturnReason
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertReason(SysReturnReasonDO sysReturnReason) throws DataAccessException {
    	if (sysReturnReason == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SYS-RETURN-REASON-INSERT-REASON", sysReturnReason);

        return sysReturnReason.getId();
    }

	/**
	 *  Update DB table <tt>sys_return_reason</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_return_reason set REASON=?, RANK=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param reason
	 *	@param rank
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateReason(String reason, int rank, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("reason", reason);
        param.put("rank", new Integer(rank));
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-RETURN-REASON-UPDATE-REASON", param);
    }

	/**
	 *  Delete records from DB table <tt>sys_return_reason</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sys_return_reason where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   delReason(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-SYS-RETURN-REASON-DEL-REASON", param);
    }

}