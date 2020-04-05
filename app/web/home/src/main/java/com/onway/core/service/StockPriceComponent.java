/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import java.util.List;

import com.onway.fyapp.common.dal.dataobject.StockPriceDO;
import com.onway.model.excel.ImportOrderSend;
import com.onway.platform.common.base.BaseResult;


/**
 * ��������
 * 
 * @author yuhang.ni
 * @version $Id: StockPriceComponent.java, v 0.1 2018��12��10�� ����6:00:07 Administrator Exp $
 */
public interface StockPriceComponent {

    /**
     * ���Ե��봦��
     * 
     * @param goodNo
     * @param importStockPriceDO
     * @return
     */
    BaseResult importStockPriceForGoods(String goodNo, String userId, List<StockPriceDO> importStockPriceDO);

    /**
     * excel����
     * 
     * @param importOrderSendDO
     * @param userId
     * @return
     */
    BaseResult excelForOrderSend(List<ImportOrderSend> importOrderSendDO, String userId);

    BaseResult newProCategory(String productId, String[] addFirstCate, String[] addSecondCate,
                              String[] addThirdCate);
    
}
