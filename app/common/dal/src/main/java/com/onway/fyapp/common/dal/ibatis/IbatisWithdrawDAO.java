/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.WithdrawDAO;

import java.util.Date;
import com.onway.fyapp.common.dal.dataobject.WithdrawDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.WithdrawDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_withdraw.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisWithdrawDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisWithdrawDAO extends SqlMapClientDaoSupport implements WithdrawDAO {
	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_withdraw</tt>
	 *
	 *	@param cell
	 *	@param nickName
	 *	@param status
	 *	@param withDrawType
	 *	@param startDate
	 *	@param endDate
	 *	@param realName
	 *	@param offset
	 *	@param limit
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   queryAllUserWithdrawList(String cell, String nickName, String status, String withDrawType, Date startDate, Date endDate, String realName, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("cell", cell);
        param.put("nickName", nickName);
        param.put("status", status);
        param.put("withDrawType", withDrawType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("realName", realName);
        param.put("offset", offset);
        param.put("limit", limit);

	return  getSqlMapClientTemplate().queryForList("MS-WITHDRAW-QUERY-ALL-USER-WITHDRAW-LIST", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_withdraw</tt>
	 *
	 *	@param cell
	 *	@param nickName
	 *	@param status
	 *	@param withDrawType
	 *	@param startDate
	 *	@param endDate
	 *	@param realName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryAllUserWithdrawListCount(String cell, String nickName, String status, String withDrawType, Date startDate, Date endDate, String realName) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("cell", cell);
        param.put("nickName", nickName);
        param.put("status", status);
        param.put("withDrawType", withDrawType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("realName", realName);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-WITHDRAW-QUERY-ALL-USER-WITHDRAW-LIST-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_withdraw</tt>
	 *
	 *	@param id
	 *	@param withdrawNo
	 *	@return WithdrawDO
	 *	@throws DataAccessException
	 */	 
    public  WithdrawDO   selectWithdrawByIdAndNo(Integer id, String withdrawNo) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("id", id);
        param.put("withdrawNo", withdrawNo);

	        return (WithdrawDO) getSqlMapClientTemplate().queryForObject("MS-WITHDRAW-SELECT-WITHDRAW-BY-ID-AND-NO", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_withdraw where (WITHDRAW_NO = ?)</tt>
	 *
	 *	@param withdrawNo
	 *	@return WithdrawDO
	 *	@throws DataAccessException
	 */	 
    public  WithdrawDO   selectWithdrawByWithdrawNo(String withdrawNo) throws DataAccessException {

 
	        return (WithdrawDO) getSqlMapClientTemplate().queryForObject("MS-WITHDRAW-SELECT-WITHDRAW-BY-WITHDRAW-NO", withdrawNo);
		
    }

	/**
	 *  Update DB table <tt>hqyt_withdraw</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_withdraw set STATUS=?, FAIL_REASON=?, MODIFIER=?, GMT_FINISH=CURRENT_TIMESTAMP, GMT_MODIFIED=CURRENT_TIMESTAMP where ((ID = ?) AND (WITHDRAW_NO = ?))</tt>
	 *
	 *	@param withdraw
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   checkWithdraw(WithdrawDO withdraw) throws DataAccessException {
    	if (withdraw == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-WITHDRAW-CHECK-WITHDRAW", withdraw);
    }

	/**
	 *  Update DB table <tt>hqyt_withdraw</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_withdraw set STATUS=?, FAIL_REASON=?, MODIFIER=?, GMT_FINISH=CURRENT_TIMESTAMP, GMT_MODIFIED=CURRENT_TIMESTAMP where ((ID = ?) AND (WITHDRAW_NO = ?))</tt>
	 *
	 *	@param status
	 *	@param failReason
	 *	@param modifier
	 *	@param id
	 *	@param withdrawNo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   shenhe(String status, String failReason, String modifier, int id, String withdrawNo) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("status", status);
        param.put("failReason", failReason);
        param.put("modifier", modifier);
        param.put("id", new Integer(id));
        param.put("withdrawNo", withdrawNo);

        return getSqlMapClientTemplate().update("MS-WITHDRAW-SHENHE", param);
    }

	/**
	 *  Update DB table <tt>hqyt_withdraw</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_withdraw set STATUS=?, FAIL_REASON=?, MODIFIER=?, VOUCEHR_IMG=?, GMT_FINISH=CURRENT_TIMESTAMP, GMT_MODIFIED=CURRENT_TIMESTAMP where ((ID = ?) AND (WITHDRAW_NO = ?))</tt>
	 *
	 *	@param withdraw
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   shenheOnline(WithdrawDO withdraw) throws DataAccessException {
    	if (withdraw == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-WITHDRAW-SHENHE-ONLINE", withdraw);
    }

	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_withdraw</tt>
	 *
	 *	@param teamName
	 *	@param teamType
	 *	@param status
	 *	@param withDrawType
	 *	@param startDate
	 *	@param endDate
	 *	@param offset
	 *	@param limit
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   queryAllTeamWithdrawList(String teamName, String teamType, String status, String withDrawType, Date startDate, Date endDate, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamName", teamName);
        param.put("teamType", teamType);
        param.put("status", status);
        param.put("withDrawType", withDrawType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("offset", offset);
        param.put("limit", limit);

	return  getSqlMapClientTemplate().queryForList("MS-WITHDRAW-QUERY-ALL-TEAM-WITHDRAW-LIST", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_withdraw</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_withdraw</tt>
	 *
	 *	@param teamName
	 *	@param teamType
	 *	@param status
	 *	@param withDrawType
	 *	@param startDate
	 *	@param endDate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryAllTeamWithdrawListCount(String teamName, String teamType, String status, String withDrawType, Date startDate, Date endDate) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamName", teamName);
        param.put("teamType", teamType);
        param.put("status", status);
        param.put("withDrawType", withDrawType);
        param.put("startDate", startDate);
        param.put("endDate", endDate);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-WITHDRAW-QUERY-ALL-TEAM-WITHDRAW-LIST-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

}