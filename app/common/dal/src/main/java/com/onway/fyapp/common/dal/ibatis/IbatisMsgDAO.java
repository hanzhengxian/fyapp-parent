/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.MsgDAO;

import java.util.Date;
import com.onway.fyapp.common.dal.dataobject.MsgDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.HashMap;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.MsgDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_msg.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisMsgDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisMsgDAO extends SqlMapClientDaoSupport implements MsgDAO {
	/**
	 *  Query DB table <tt>hqyt_msg</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_msg</tt>
	 *
	 *	@param userId
	 *	@param msgType
	 *	@param msgTitle
	 *	@param isRead
	 *	@param startDate
	 *	@param endDate
	 *	@param userCell
	 *	@param userNickName
	 *	@param userRealName
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectMsg(String userId, String msgType, String msgTitle, String isRead, Date startDate, Date endDate, String userCell, String userNickName, String userRealName, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("msgType", msgType);
        param.put("msgTitle", msgTitle);
        param.put("isRead", isRead);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("userCell", userCell);
        param.put("userNickName", userNickName);
        param.put("userRealName", userRealName);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-MSG-SELECT-MSG", param);

    }

	/**
	 *  Query DB table <tt>hqyt_msg</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_msg</tt>
	 *
	 *	@param userId
	 *	@param msgType
	 *	@param msgTitle
	 *	@param isRead
	 *	@param startDate
	 *	@param endDate
	 *	@param userCell
	 *	@param userNickName
	 *	@param userRealName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectMsgCount(String userId, String msgType, String msgTitle, String isRead, Date startDate, Date endDate, String userCell, String userNickName, String userRealName) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("msgType", msgType);
        param.put("msgTitle", msgTitle);
        param.put("isRead", isRead);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("userCell", userCell);
        param.put("userNickName", userNickName);
        param.put("userRealName", userRealName);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-MSG-SELECT-MSG-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>MsgDO</tt> object to DB table <tt>hqyt_msg</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_msg(USER_ID,MSG_TYPE,MSG_TITLE,MSG_CONTENT,IS_READ,CREATER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param msg
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertMsg(MsgDO msg) throws DataAccessException {
    	if (msg == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-MSG-INSERT-MSG", msg);

        return msg.getId();
    }

	/**
	 *  Update DB table <tt>hqyt_msg</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_msg set USER_ID=?, MSG_TYPE=?, MSG_TITLE=?, MSG_CONTENT=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param userId
	 *	@param msgType
	 *	@param msgTitle
	 *	@param msgContent
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateMsg(String userId, String msgType, String msgTitle, String msgContent, String modifier, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);
        param.put("msgType", msgType);
        param.put("msgTitle", msgTitle);
        param.put("msgContent", msgContent);
        param.put("modifier", modifier);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-MSG-UPDATE-MSG", param);
    }

	/**
	 *  Update DB table <tt>hqyt_msg</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_msg set IS_DEL='1', MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteMsg(String modifier, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("modifier", modifier);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-MSG-DELETE-MSG", param);
    }

}