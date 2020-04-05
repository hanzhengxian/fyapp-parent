/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.TeamUserDAO;

import com.onway.fyapp.common.dal.dataobject.TeamUserDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.TeamUserDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_team_user.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisTeamUserDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisTeamUserDAO extends SqlMapClientDaoSupport implements TeamUserDAO {
	/**
	 *  Insert one <tt>TeamUserDO</tt> object to DB table <tt>hqyt_team_user</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_team_user(TEAM_ID,TEAM_USER_ID,TEAM_LEDER,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param teamUser
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertteamuser(TeamUserDO teamUser) throws DataAccessException {
    	if (teamUser == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-TEAM-USER-INSERTTEAMUSER", teamUser);

        return teamUser.getId();
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, TEAM_ID, TEAM_USER_ID, TEAM_LEDER, GMT_CREATE, GMT_MODIFIED from hqyt_team_user where ((TEAM_ID = ?) AND (TEAM_LEDER = ?))</tt>
	 *
	 *	@param teamId
	 *	@param teamLeder
	 *	@return TeamUserDO
	 *	@throws DataAccessException
	 */	 
    public  TeamUserDO   selectLeaderByTeamId(String teamId, String teamLeder) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamId", teamId);
        param.put("teamLeder", teamLeder);

	        return (TeamUserDO) getSqlMapClientTemplate().queryForObject("MS-TEAM-USER-SELECT-LEADER-BY-TEAM-ID", param);
		
    }

	/**
	 *  Delete records from DB table <tt>hqyt_team_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_team_user where (TEAM_USER_ID = ?)</tt>
	 *
	 *	@param teamUserId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteteamuser(String teamUserId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-TEAM-USER-DELETETEAMUSER", teamUserId);
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_team_user where ((TEAM_ID = ?) AND (TEAM_LEDER = ?))</tt>
	 *
	 *	@param teamId
	 *	@param teamLeder
	 *	@return TeamUserDO
	 *	@throws DataAccessException
	 */	 
    public  TeamUserDO   checkld(String teamId, String teamLeder) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamId", teamId);
        param.put("teamLeder", teamLeder);

	        return (TeamUserDO) getSqlMapClientTemplate().queryForObject("MS-TEAM-USER-CHECKLD", param);
		
    }

	/**
	 *  Update DB table <tt>hqyt_team_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_team_user set TEAM_LEDER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (TEAM_USER_ID = ?)</tt>
	 *
	 *	@param teamLeder
	 *	@param teamUserId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   changeTeamRole(String teamLeder, String teamUserId) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamLeder", teamLeder);
        param.put("teamUserId", teamUserId);

        return getSqlMapClientTemplate().update("MS-TEAM-USER-CHANGE-TEAM-ROLE", param);
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select ID, TEAM_ID, TEAM_USER_ID, TEAM_LEDER, GMT_CREATE, GMT_MODIFIED from hqyt_team_user where (TEAM_USER_ID = ?)</tt>
	 *
	 *	@param teamUserId
	 *	@return TeamUserDO
	 *	@throws DataAccessException
	 */	 
    public  TeamUserDO   getUserTeamInfo(String teamUserId) throws DataAccessException {

 
	        return (TeamUserDO) getSqlMapClientTemplate().queryForObject("MS-TEAM-USER-GET-USER-TEAM-INFO", teamUserId);
		
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_team_user where ((TEAM_ID = ?) AND (TEAM_USER_ID = ?))</tt>
	 *
	 *	@param teamId
	 *	@param teamUserId
	 *	@return TeamUserDO
	 *	@throws DataAccessException
	 */	 
    public  TeamUserDO   checkOnlyUserTeam(String teamId, String teamUserId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamId", teamId);
        param.put("teamUserId", teamUserId);

	        return (TeamUserDO) getSqlMapClientTemplate().queryForObject("MS-TEAM-USER-CHECK-ONLY-USER-TEAM", param);
		
    }

	/**
	 *  Delete records from DB table <tt>hqyt_team_user</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from hqyt_team_user where (TEAM_ID = ?)</tt>
	 *
	 *	@param teamId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   cleanALlTeamLink(String teamId) throws DataAccessException {

        return getSqlMapClientTemplate().delete("MS-TEAM-USER-CLEAN-A-LL-TEAM-LINK", teamId);
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_team_user</tt>
	 *
	 *	@param userId
	 *	@return TeamUserDO
	 *	@throws DataAccessException
	 */	 
    public  TeamUserDO   searchMyTeam(String userId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("userId", userId);

	        return (TeamUserDO) getSqlMapClientTemplate().queryForObject("MS-TEAM-USER-SEARCH-MY-TEAM", param);
		
    }

	/**
	 *  Query DB table <tt>hqyt_team_user</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_team_user</tt>
	 *
	 *	@param teamId
	 *	@return List<String>
	 *	@throws DataAccessException
	 */	 
    public  List<String>   searchAllUserId(String teamId) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("teamId", teamId);

        return getSqlMapClientTemplate().queryForList("MS-TEAM-USER-SEARCH-ALL-USER-ID", param);

    }

}