/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import com.onway.fyapp.common.dal.dataobject.TeamDO;
import com.onway.fyapp.common.dal.dataobject.UserDO;
import com.onway.platform.common.base.QueryResult;



public interface UserQueryService {

    QueryResult<TeamDO> searchUserTeam(String userId);

    QueryResult<UserDO> searchByUserIdOrOpenId(String userId, String openId);

    QueryResult<UserDO> getUserInfo(String cell);}
