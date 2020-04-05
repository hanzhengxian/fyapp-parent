package com.onway.core.service;

import javax.servlet.http.HttpServletRequest;

import com.onway.common.lang.Money;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderReturnDO;
import com.onway.platform.common.base.BaseResult;

public interface AccountTransComponent {

    /**
     * 
     * 积分或胡币退款
     * 
     * @param userId
     * @param amt
     * @param type
     *            (2 胡币 3 积分)
     * @return
     */
    public BaseResult tuiKuanForAccount(String userId, Money amt, String type, String childOrderId,
                                        String user_id);

    /**
     * 支付宝退款
     * 
     * @param userId
     * @param amt
     * @param childOrderId
     * @return
     */
    public BaseResult tuiKuanForZFB(OrderDO orderDO, OrderReturnDO orderReturnDO);

    /**
     * 微信退款
     * 
     * @param userId
     * @param amt
     * @param childOrderId
     * @return
     */
    public BaseResult tuiKuanForWeChat(String userId, Money amt, String childOrderId,
                                       HttpServletRequest request, String user_id);

    /**
     * 微信退款
     * 
     * @param orderDO
     * @param refundMoney
     * @param linkReturnId
     * @return
     */
    BaseResult tuiKuanForWeChatS(OrderDO orderDO, Money refundMoney, int linkReturnId);
}
