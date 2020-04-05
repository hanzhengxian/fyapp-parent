/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.PlanReturnDAO;

import java.util.Date;
import com.onway.fyapp.common.dal.dataobject.PlanReturnDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.PlanReturnDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_plan_return.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisPlanReturnDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisPlanReturnDAO extends SqlMapClientDaoSupport implements PlanReturnDAO {
	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_return</tt>
	 *
	 *	@param returnId
	 *	@param returnName
	 *	@param returnType
	 *	@param recommendLevel
	 *	@param teamId
	 *	@param startDate
	 *	@param endDate
	 *	@param status
	 *	@param now
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   queryList(String returnId, String returnName, String returnType, String recommendLevel, String teamId, Date startDate, Date endDate, String status, Date now, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnId", returnId);
        param.put("returnName", returnName);
        param.put("returnType", returnType);
        param.put("recommendLevel", recommendLevel);
        param.put("teamId", teamId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("status", status);
        param.put("now", now);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-PLAN-RETURN-QUERY-LIST", param);

    }

	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_plan_return</tt>
	 *
	 *	@param returnId
	 *	@param returnName
	 *	@param returnType
	 *	@param recommendLevel
	 *	@param teamId
	 *	@param startDate
	 *	@param endDate
	 *	@param status
	 *	@param now
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryListCount(String returnId, String returnName, String returnType, String recommendLevel, String teamId, Date startDate, Date endDate, String status, Date now) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnId", returnId);
        param.put("returnName", returnName);
        param.put("returnType", returnType);
        param.put("recommendLevel", recommendLevel);
        param.put("teamId", teamId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("status", status);
        param.put("now", now);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-PLAN-RETURN-QUERY-LIST-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>PlanReturnDO</tt> object to DB table <tt>hqyt_plan_return</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_plan_return(RETURN_ID,RETURN_NAME,RETURN_TYPE,RECOMMEND_LEVEL,RETURN_RATE,TEAM_ID,DEL_FLG,CREATER,GMT_CREATE,GMT_MODIFIED,RETURN_SUB_TYPE,RETURN_VALUE,LINK_PRO,END_TIME,START_TIME) values (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param planReturn
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(PlanReturnDO planReturn) throws DataAccessException {
    	if (planReturn == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PLAN-RETURN-INSERT", planReturn);

        return planReturn.getId();
    }

	/**
	 *  Update DB table <tt>hqyt_plan_return</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_return set RETURN_NAME=?, RETURN_RATE=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (RETURN_ID = ?)</tt>
	 *
	 *	@param returnName
	 *	@param returnRate
	 *	@param modifier
	 *	@param returnId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(String returnName, double returnRate, String modifier, String returnId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnName", returnName);
        param.put("returnRate", new Double(returnRate));
        param.put("modifier", modifier);
        param.put("returnId", returnId);

        return getSqlMapClientTemplate().update("MS-PLAN-RETURN-UPDATE", param);
    }

	/**
	 *  Update DB table <tt>hqyt_plan_return</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_return set DEL_FLG='1', MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (RETURN_ID = ?)</tt>
	 *
	 *	@param modifier
	 *	@param returnId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   delete(String modifier, String returnId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("modifier", modifier);
        param.put("returnId", returnId);

        return getSqlMapClientTemplate().update("MS-PLAN-RETURN-DELETE", param);
    }

	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_plan_return</tt>
	 *
	 *	@param returnType
	 *	@param recommendLevel
	 *	@param teamId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryCount(String returnType, String recommendLevel, String teamId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnType", returnType);
        param.put("recommendLevel", recommendLevel);
        param.put("teamId", teamId);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-PLAN-RETURN-QUERY-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Update DB table <tt>hqyt_plan_return</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_return set RETURN_NAME=?, RETURN_SUB_TYPE=?, RETURN_VALUE=?, LINK_PRO=?, START_TIME=?, END_TIME=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (RETURN_ID = ?)</tt>
	 *
	 *	@param planReturn
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateReturnSelf(PlanReturnDO planReturn) throws DataAccessException {
    	if (planReturn == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-PLAN-RETURN-UPDATE-RETURN-SELF", planReturn);
    }

	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_return</tt>
	 *
	 *	@param returnId
	 *	@return PlanReturnDO
	 *	@throws DataAccessException
	 */	 
    public  PlanReturnDO   queryByReturnId(String returnId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnId", returnId);

	        return (PlanReturnDO) getSqlMapClientTemplate().queryForObject("MS-PLAN-RETURN-QUERY-BY-RETURN-ID", param);
		
    }

	/**
	 *  Update DB table <tt>hqyt_plan_return</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_return set LINK_PRO=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (RETURN_ID = ?)</tt>
	 *
	 *	@param linkPro
	 *	@param modifier
	 *	@param returnId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateReturnSelfLinkPro(String linkPro, String modifier, String returnId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("linkPro", linkPro);
        param.put("modifier", modifier);
        param.put("returnId", returnId);

        return getSqlMapClientTemplate().update("MS-PLAN-RETURN-UPDATE-RETURN-SELF-LINK-PRO", param);
    }

	/**
	 *  Update DB table <tt>hqyt_plan_return</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_return set LINK_PRO=?, LINK_PRO_STOCK=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (RETURN_ID = ?)</tt>
	 *
	 *	@param linkPro
	 *	@param linkProStock
	 *	@param modifier
	 *	@param returnId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateReturnSelfLinkProAttr(String linkPro, String linkProStock, String modifier, String returnId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("linkPro", linkPro);
        param.put("linkProStock", linkProStock);
        param.put("modifier", modifier);
        param.put("returnId", returnId);

        return getSqlMapClientTemplate().update("MS-PLAN-RETURN-UPDATE-RETURN-SELF-LINK-PRO-ATTR", param);
    }

	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_return</tt>
	 *
	 *	@param returnType
	 *	@param productId
	 *	@param now
	 *	@param delFlg
	 *	@return PlanReturnDO
	 *	@throws DataAccessException
	 */	 
    public  PlanReturnDO   queryForReturnSelf(String returnType, String productId, Date now, String delFlg) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnType", returnType);
        param.put("productId", productId);
        param.put("now", now);
        param.put("delFlg", delFlg);

	        return (PlanReturnDO) getSqlMapClientTemplate().queryForObject("MS-PLAN-RETURN-QUERY-FOR-RETURN-SELF", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_plan_return</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_return</tt>
	 *
	 *	@param returnType
	 *	@param recommendLevel
	 *	@param teamId
	 *	@param delFlg
	 *	@return PlanReturnDO
	 *	@throws DataAccessException
	 */	 
    public  PlanReturnDO   query(String returnType, Integer recommendLevel, String teamId, String delFlg) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("returnType", returnType);
        param.put("recommendLevel", recommendLevel);
        param.put("teamId", teamId);
        param.put("delFlg", delFlg);

	        return (PlanReturnDO) getSqlMapClientTemplate().queryForObject("MS-PLAN-RETURN-QUERY", param);
		
    }

}