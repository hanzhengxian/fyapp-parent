/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SysRoleUserDAO;

import com.onway.fyapp.common.dal.dataobject.SysRoleUserDO;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import java.util.List;

import java.util.Map;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SysRoleUserDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sys_role_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSysRoleUserDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSysRoleUserDAO extends SqlMapClientDaoSupport implements SysRoleUserDAO {
	/**
	 *  Query DB table <tt>sys_role_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_role_user</tt>
	 *
	 *	@param username
	 *	@param password
	 *	@return HashMap
	 *	@throws DataAccessException
	 */	 
    public  Map<String,Object>  login(String username, String password) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("username", username);
        param.put("password", password);

	        return (HashMap) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-USER-LOGIN", param);
		
    }

	/**
	 *  Query DB table <tt>sys_role_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_role_user</tt>
	 *
	 *	@param username
	 *	@param offset
	 *	@param limit
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   selectalladminuser(String username, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("username", username);
        param.put("offset", offset);
        param.put("limit", limit);

	return  getSqlMapClientTemplate().queryForList("MS-SYS-ROLE-USER-SELECTALLADMINUSER", param);
		
    }

	/**
	 *  Query DB table <tt>sys_role_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from sys_role_user</tt>
	 *
	 *	@param username
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectalladminusercount(String username) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("username", username);

	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-USER-SELECTALLADMINUSERCOUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>SysRoleUserDO</tt> object to DB table <tt>sys_role_user</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sys_role_user(USER_ID,USERNAME,PASSWORD,ROLE_ID,REAL_NAME,CREATER,GMT_CREATE) values (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param sysRoleUser
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   addadminuser(SysRoleUserDO sysRoleUser) throws DataAccessException {
    	if (sysRoleUser == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SYS-ROLE-USER-ADDADMINUSER", sysRoleUser);

        return sysRoleUser.getId();
    }

	/**
	 *  Update DB table <tt>sys_role_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_role_user set USERNAME=?, REAL_NAME=?, ROLE_ID=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (id = ?)</tt>
	 *
	 *	@param username
	 *	@param realName
	 *	@param roleId
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updateadminuser(String username, String realName, String roleId, String modifier, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("username", username);
        param.put("realName", realName);
        param.put("roleId", roleId);
        param.put("modifier", modifier);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-ROLE-USER-UPDATEADMINUSER", param);
    }

	/**
	 *  Update DB table <tt>sys_role_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_role_user set PASSWORD=?, MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (USER_ID = ?)</tt>
	 *
	 *	@param password
	 *	@param modifier
	 *	@param userId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updatepassword(String password, String modifier, String userId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("password", password);
        param.put("modifier", modifier);
        param.put("userId", userId);

        return getSqlMapClientTemplate().update("MS-SYS-ROLE-USER-UPDATEPASSWORD", param);
    }

	/**
	 *  Query DB table <tt>sys_role_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select password from sys_role_user where (USER_ID = ?)</tt>
	 *
	 *	@param userId
	 *	@return String
	 *	@throws DataAccessException
	 */	 
    public  String   selectpassword(String userId) throws DataAccessException {

 
	        return (String) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-USER-SELECTPASSWORD", userId);
		
    }

	/**
	 *  Update DB table <tt>sys_role_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_role_user set DEL_FLG='1', MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param modifier
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteSysUser(String modifier, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("modifier", modifier);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-ROLE-USER-DELETE-SYS-USER", param);
    }

	/**
	 *  Query DB table <tt>sys_role_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select count(1) from sys_role_user where (USERNAME = ?)</tt>
	 *
	 *	@param username
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   queryCountSysUserByUserName(String username) throws DataAccessException {

 
	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-SYS-ROLE-USER-QUERY-COUNT-SYS-USER-BY-USER-NAME", username);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

}