package com.onway.core.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.onway.common.lang.Money;
import com.onway.platform.common.base.BaseResult;

/**
 * 微信企业转账到用户
 * 
 * @author jiaming.zhu
 * @version $Id: WechatComponentImpl.java, v 0.1 2017年3月6日 上午11:23:19 zjm Exp $
 */
public interface WechatComponent {

    /**
     * 
     * 
     * @param orderNo
     * @param money
     * @param openId
     * @param request
     * @return
     */
    public BaseResult withdrawByWechat(String orderNo, Money money, String openId,
                                       HttpServletRequest request);

    /**
     * 微信退款
     * 
     * @param orderNo
     * @param orderId
     * @param refundNo
     * @param money
     * @param refundMoney
     * @param linkReturnId
     * @return
     */
    BaseResult refundByWechat(String orderNo, String orderId, String refundNo, Money money,
                              Money refundMoney, int linkReturnId);

}
