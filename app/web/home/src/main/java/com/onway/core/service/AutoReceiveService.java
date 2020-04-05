/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.OrderRebateDO;
import com.onway.fyapp.common.dal.dataobject.PlanReturnDO;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;

public interface AutoReceiveService {

    BaseResult payOrderSuccForDevote(String userId, String userType, Money addDevote,
                                     String childOrderId);

    BaseResult payOrderSuccForDevoteForTeam(String teamId, Money addDevote, String childOrderId);

    BaseResult payOrderSuccForReturnTeam(Money returnHu, String returnTeamId,
                                         String returnTeamType, String linkNo);

    BaseResult payOrderSuccForReturnStock(String returnUserId, String returnUserType,
                                          Money returnPoint, String linkNo);

    BaseResult newOrderRebate(OrderRebateDO orderRebateDO);

    QueryResult<PlanReturnDO> getPlanReturn(String returnType, int recommendLevel, String teamId);
    
}
