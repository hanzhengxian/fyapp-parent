/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import java.util.Date;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_user</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: UserDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface UserDAO {
	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (1 = 1)</tt>
	 *
	 *	@param nickName
	 *	@param realName
	 *	@param cell
	 *	@param sex
	 *	@param userLevel
	 *	@param delFlg
	 *	@param startDate
	 *	@param endDate
	 *	@param recommendUserCell
	 *	@param userId
	 *	@param recommendUserId
	 *	@param recommendNickName
	 *	@param recommendRealName
	 *	@param erpNo
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   selectUserList(String nickName, String realName, String cell, String sex, String userLevel, String delFlg, Date startDate, Date endDate, String recommendUserCell, String userId, String recommendUserId, String recommendNickName, String recommendRealName, String erpNo, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_user</tt>
	 *
	 *	@param nickName
	 *	@param realName
	 *	@param cell
	 *	@param sex
	 *	@param userLevel
	 *	@param delFlg
	 *	@param startDate
	 *	@param endDate
	 *	@param recommendUserCell
	 *	@param userId
	 *	@param recommendUserId
	 *	@param recommendNickName
	 *	@param recommendRealName
	 *	@param erpNo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryListCount(String nickName, String realName, String cell, String sex, String userLevel, String delFlg, Date startDate, Date endDate, String recommendUserCell, String userId, String recommendUserId, String recommendNickName, String recommendRealName, String erpNo) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (USER_ID = ?)</tt>
	 *
	 *	@param userId
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public  UserDO   selectByUserId(String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (USER_ID = ?)</tt>
	 *
	 *	@param userId
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public  UserDO   finduserbyid(String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (1 = 1)</tt>
	 *
	 *	@param fnickName
	 *	@param fcell
	 *	@param frealName
	 *	@param offset
	 *	@param limit
	 *	@return List<UserDO>
	 *	@throws DataAccessException
	 */	 
    public  List<UserDO>   findteamuser(String fnickName, String fcell, String frealName, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_user where (1 = 1)</tt>
	 *
	 *	@param fnickName
	 *	@param fcell
	 *	@param frealName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   findteamuserCount(String fnickName, String fcell, String frealName) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user</tt>
	 *
	 *	@param teamId
	 *	@param fnickName
	 *	@param fcell
	 *	@param froleType
	 *	@param frealName
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   finduserbyteamid(String teamId, String fnickName, String fcell, String froleType, String frealName, Integer offset, Integer limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_user</tt>
	 *
	 *	@param teamId
	 *	@param fnickName
	 *	@param fcell
	 *	@param froleType
	 *	@param frealName
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   finduserbyteamidCount(String teamId, String fnickName, String fcell, String froleType, String frealName) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (1 = 1)</tt>
	 *
	 *	@param offset
	 *	@param limit
	 *	@return List<UserDO>
	 *	@throws DataAccessException
	 */	 
    public  List<UserDO>   queryAllUser(int offset, int limit) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(1) from hqyt_user</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryAllUserCount() throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_user set USER_LEVEL=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (USER_ID = ?)</tt>
	 *
	 *	@param userLevel
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateUserLevel(int userLevel, String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user where (CELL = ?)</tt>
	 *
	 *	@param cell
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public  UserDO   selectByUserCell(String cell) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_user set DEL_FLG=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (USER_ID = ?)</tt>
	 *
	 *	@param delFlg
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   modifyUserStatus(String delFlg, String userId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_user</tt>
	 *
	 *	@param userId
	 *	@param openId
	 *	@return UserDO
	 *	@throws DataAccessException
	 */	 
    public  UserDO   searchByUserIdOrOpenId(String userId, String openId) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_user set REAL_NAME=?, ERP_NO=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (USER_ID = ?)</tt>
	 *
	 *	@param user
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateUserRealName(UserDO user) throws DataAccessException;

}