package com.onway.core.service;

import com.onway.platform.common.base.QueryResult;
import com.onway.result.express.ExpressQueryPojo;

/**
 * 快递查询  （快递100）
 */
public interface ExpressComponent {

    /**
     * 查询快递信息
     * 
     * @param com
     * @param num
     * @return
     */
    QueryResult<ExpressQueryPojo> getKuaiDiInfo4Firm(String com, String num);
}
