/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.PlanTeamCopyDAO;

import com.onway.fyapp.common.dal.dataobject.PlanTeamCopyDO;
import java.util.List;
import org.springframework.dao.DataAccessException;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.PlanTeamCopyDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_plan_team_copy.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisPlanTeamCopyDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisPlanTeamCopyDAO extends SqlMapClientDaoSupport implements PlanTeamCopyDAO {
	/**
	 *  Query DB table <tt>hqyt_plan_team_copy</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_plan_team_copy</tt>
	 *
	 *	@param planTeamName
	 *	@param planTeamId
	 *	@param type
	 *	@param linkType
	 *	@param linkId
	 *	@param offset
	 *	@param limit
	 *	@return List<PlanTeamCopyDO>
	 *	@throws DataAccessException
	 */	 
    public  List<PlanTeamCopyDO>   selectPlanTeamPage(String planTeamName, String planTeamId, String type, String linkType, String linkId, Integer offset, Integer limit) throws DataAccessException {

	Map<String,Object> param = new HashMap<String,Object>();

        param.put("planTeamName", planTeamName);
        param.put("planTeamId", planTeamId);
        param.put("type", type);
        param.put("linkType", linkType);
        param.put("linkId", linkId);
        param.put("offset", offset);
        param.put("limit", limit);

        return getSqlMapClientTemplate().queryForList("MS-PLAN-TEAM-COPY-SELECT-PLAN-TEAM-PAGE", param);

    }

	/**
	 *  Insert one <tt>PlanTeamCopyDO</tt> object to DB table <tt>hqyt_plan_team_copy</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_plan_team_copy(PLAN_TEAM_NAME,PLAN_TEAM_ID,TYPE,LINK_TYPE,LINK_ID,DEL_FLG,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param planTeamCopy
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insertPlanTeam(PlanTeamCopyDO planTeamCopy) throws DataAccessException {
    	if (planTeamCopy == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PLAN-TEAM-COPY-INSERT-PLAN-TEAM", planTeamCopy);

        return planTeamCopy.getId();
    }

	/**
	 *  Update DB table <tt>hqyt_plan_team_copy</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_team_copy set PLAN_TEAM_NAME=?, TYPE=?, LINK_TYPE=?, LINK_ID=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (ID = ?)</tt>
	 *
	 *	@param planTeamName
	 *	@param type
	 *	@param linkType
	 *	@param linkId
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updatePlanTeamById(String planTeamName, String type, String linkType, String linkId, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("planTeamName", planTeamName);
        param.put("type", type);
        param.put("linkType", linkType);
        param.put("linkId", linkId);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-PLAN-TEAM-COPY-UPDATE-PLAN-TEAM-BY-ID", param);
    }

	/**
	 *  Update DB table <tt>hqyt_plan_team_copy</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_plan_team_copy set DEL_FLG='1' where (ID = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deleteById(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().update("MS-PLAN-TEAM-COPY-DELETE-BY-ID", param);
    }

}