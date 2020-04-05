/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.fyapp.common.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.onway.fyapp.common.dal.daointerface.SysMenuDAO;

import com.onway.fyapp.common.dal.dataobject.SysMenuDO;
import java.util.List;
import org.springframework.dao.DataAccessException;
import java.util.Date;

import java.util.Map;
import java.util.HashMap;

/**
 * An ibatis based implementation of dao interface <tt>com.onway.fyapp.common.dal.daointerface.SysMenuDAO</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/sys_menu.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: IbatisSysMenuDAO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class IbatisSysMenuDAO extends SqlMapClientDaoSupport implements SysMenuDAO {
	/**
	 *  Query DB table <tt>sys_menu</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_menu</tt>
	 *
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   selectallmenu() throws DataAccessException {


	return  getSqlMapClientTemplate().queryForList("MS-SYS-MENU-SELECTALLMENU", null);
		
    }

	/**
	 *  Update DB table <tt>sys_menu</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update sys_menu set name=?, url=?, rank=?, MODIFIER=?, GMT_MODIFIED=? where (id = ?)</tt>
	 *
	 *	@param name
	 *	@param url
	 *	@param rank
	 *	@param modifier
	 *	@param gmtModified
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   updatemenu(String name, String url, int rank, String modifier, Date gmtModified, int id) throws DataAccessException {
        Map<String,Object> param = new HashMap<String,Object>();

        param.put("name", name);
        param.put("url", url);
        param.put("rank", new Integer(rank));
        param.put("modifier", modifier);
        param.put("gmtModified", gmtModified);
        param.put("id", new Integer(id));

        return getSqlMapClientTemplate().update("MS-SYS-MENU-UPDATEMENU", param);
    }

	/**
	 *  Query DB table <tt>sys_menu</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select max(id) from sys_menu</tt>
	 *
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   selectmaxid() throws DataAccessException {


	    Integer retObj = (Integer) getSqlMapClientTemplate().queryForObject("MS-SYS-MENU-SELECTMAXID", null);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.intValue();
		}

    }

	/**
	 *  Insert one <tt>SysMenuDO</tt> object to DB table <tt>sys_menu</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into sys_menu(PID,NAME,URL,RANK,CREATER,GMT_CREATE) values (?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param sysMenu
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   addmenu(SysMenuDO sysMenu) throws DataAccessException {
    	if (sysMenu == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-SYS-MENU-ADDMENU", sysMenu);

        return sysMenu.getId();
    }

	/**
	 *  Delete records from DB table <tt>sys_menu</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sys_menu where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deletemenubyid(int id) throws DataAccessException {
        Integer param = new Integer(id);

        return getSqlMapClientTemplate().delete("MS-SYS-MENU-DELETEMENUBYID", param);
    }

	/**
	 *  Delete records from DB table <tt>sys_menu</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from sys_menu where (pid = ?)</tt>
	 *
	 *	@param pid
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public  int   deletemenubypid(int pid) throws DataAccessException {
        Integer param = new Integer(pid);

        return getSqlMapClientTemplate().delete("MS-SYS-MENU-DELETEMENUBYPID", param);
    }

	/**
	 *  Query DB table <tt>sys_menu</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from sys_menu where (pid = ?)</tt>
	 *
	 *	@param pid
	 *	@return List< Map<String,Object> >
	 *	@throws DataAccessException
	 */	 
    public  List< Map<String,Object> >   selectmenubypid(int pid) throws DataAccessException {

        Integer param = new Integer(pid);

	return  getSqlMapClientTemplate().queryForList("MS-SYS-MENU-SELECTMENUBYPID", param);
		
    }

}