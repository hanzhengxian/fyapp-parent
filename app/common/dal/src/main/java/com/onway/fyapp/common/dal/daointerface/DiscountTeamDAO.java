/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.daointerface;

import com.onway.fyapp.common.dal.dataobject.DiscountTeamDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.Map;

/**
 * A dao interface provides methods to access database table <tt>hqyt_discount_team</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/hqyt_discount_team.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: DiscountTeamDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public interface DiscountTeamDAO {
	/**
	 *  Query DB table <tt>hqyt_discount_team</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_discount_team</tt>
	 *
	 *	@param discountTeamId
	 *	@param discountTitle
	 *	@param offset
	 *	@param limit
	 *	@return List<Map<String,Object>>
	 *	@throws DataAccessException
	 */	 
    public  List<Map<String,Object>>   queryList(String discountTeamId, String discountTitle, String offset, String limit) throws DataAccessException;

	/**
	 *  Insert one <tt>DiscountTeamDO</tt> object to DB table <tt>hqyt_discount_team</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into hqyt_discount_team(DISCOUNT_TEAM_ID,DISCOUNT_TITLE,DISCOUNT_DESC,DISCOUNT_BACKGROUND,DISCOUNT_BACKGROUND_PC,DEL_FLG,CREATER,TITLE_COLOR,DESC_COLOR,GMT_CREATE,GMT_MODIFIED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)</tt>
	 *
	 *	@param discountTeam
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   insert(DiscountTeamDO discountTeam) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_discount_team</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_discount_team set DISCOUNT_TITLE=?, DISCOUNT_DESC=?, DISCOUNT_BACKGROUND=?, DISCOUNT_BACKGROUND_PC=?, MODIFIER=?, TITLE_COLOR=?, DESC_COLOR=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (DISCOUNT_TEAM_ID = ?)</tt>
	 *
	 *	@param discountTeam
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   update(DiscountTeamDO discountTeam) throws DataAccessException;

	/**
	 *  Update DB table <tt>hqyt_discount_team</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update hqyt_discount_team set DEL_FLG='1', MODIFIER=?, GMT_MODIFIED=CURRENT_TIMESTAMP where (DISCOUNT_TEAM_ID = ?)</tt>
	 *
	 *	@param modifier
	 *	@param discountTeamId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   delete(String modifier, String discountTeamId) throws DataAccessException;

	/**
	 *  Query DB table <tt>hqyt_discount_team</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from hqyt_discount_team</tt>
	 *
	 *	@param discountTeamId
	 *	@return DiscountTeamDO
	 *	@throws DataAccessException
	 */	 
    public  DiscountTeamDO   queryByDiscountTeamId(String discountTeamId) throws DataAccessException;

}