package com.onway.shunfeng.service;

import java.util.List;

import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryResult;
import com.onway.result.JsonQueryResult;
import com.onway.result.express.ExpressQueryPojo;
import com.onway.shunfeng.model.ShunFengResult;
import com.onway.shunfeng.model.request.AddedService;
import com.onway.shunfeng.model.request.Cargo;
import com.onway.shunfeng.model.request.Order;

public interface ShunfengService {

    /**
     * 顺风下单  获取运单号
     * 
     * @param order
     * @param cargo
     * @param addedService
     * @return
     */
    JsonQueryResult<ShunFengResult> callExpressService(Order order, Cargo cargo,
                                                       AddedService addedService);

    /**
     * 批量订单下单获取运单号 （顺风）
     * 
     * @param childOrderIdArr
     * @param user_id
     * @return
     */
    BaseResult sfGetMaino(String[] chooseChildrenIdS, String user_id);

    /**
     * 相关快递单号的订单打印运单图片
     * 
     * @param childOrderIdArr
     * @param user_id
     * @param savePath
     * @return
     */
    BaseResult sfPrint(String[] chooseChildrenIdS, String user_id, String savePath);

    QueryResult<List<String>> sfGetMainoAndPrint(String[] chooseChildrenIdS, String user_id);

    /**
     * 
     * 
     * @param orderId
     * @return
     */
    JsonQueryResult<ExpressQueryPojo> routeRequest(String orderId);

    JsonQueryResult<ExpressQueryPojo> routeRequest(String comName, String mun);

}
