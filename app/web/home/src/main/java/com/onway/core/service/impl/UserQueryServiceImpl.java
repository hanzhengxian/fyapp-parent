/**
 * 
 */
package com.onway.core.service.impl;

import com.onway.core.service.UserQueryService;
import com.onway.fyapp.common.dal.daointerface.TeamDAO;
import com.onway.fyapp.common.dal.daointerface.TeamUserDAO;
import com.onway.fyapp.common.dal.daointerface.UserDAO;
import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.TeamUserDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.platform.common.service.util.AssertUtil;

public class UserQueryServiceImpl extends AbstractServiceImpl implements UserQueryService {

    private TeamUserDAO teamUserDAO;
    private TeamDAO     teamDAO;
    private UserDAO     userDAO;

    public void setTeamUserDAO(TeamUserDAO teamUserDAO) {
        this.teamUserDAO = teamUserDAO;
    }

    public void setTeamDAO(TeamDAO teamDAO) {
        this.teamDAO = teamDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public QueryResult<TeamDO> searchUserTeam(final String userId) {
        final QueryResult<TeamDO> result = new QueryResult<TeamDO>(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "用户编号为空");
            }

            @Override
            public void executeService() {
                TeamUserDO searchMyTeam = teamUserDAO.searchMyTeam(userId);
                if (null == searchMyTeam) {
                    result.setMessage("没有权限");
                    return;
                }

                TeamDO teamDO = teamDAO.queryTeamByTeamId(searchMyTeam.getTeamId());
                if (null == teamDO) {
                    result.setMessage("没有权限");
                    return;
                }
                result.setResultObject(teamDO);
                result.setSuccess(true);
            }
        });
        return result;
    }

    @Override
    public QueryResult<UserDO> searchByUserIdOrOpenId(final String userId, final String openId) {
        final QueryResult<UserDO> result = new QueryResult<UserDO>(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
            }

            @Override
            public void executeService() {
                UserDO userDO = userDAO.searchByUserIdOrOpenId(userId, openId);
                if (null != userDO) {
                    result.setSuccess(true);
                    result.setResultObject(userDO);
                }
            }
        });
        return result;
    }
    
    /**
     * 手机号查询用户信息
     */
    @Override
    public QueryResult<UserDO> getUserInfo(final String cell) {
        final QueryResult<UserDO> result = new QueryResult<UserDO>(false);
        serviceTemplate.execute(result, new ServiceCheckCallback() {

            @Override
            public void check() {
                AssertUtil.notBlank(cell, "手机号为空");
            }

            @Override
            public void executeService() {
                UserDO userDO = userDAO.selectByUserCell(cell);
                if (null != userDO) {
                    result.setSuccess(true);
                    result.setResultObject(userDO);
                } else {
                    result.setMessage("手机号未注册");
                }
            }
        });
        return result;
    }
}
