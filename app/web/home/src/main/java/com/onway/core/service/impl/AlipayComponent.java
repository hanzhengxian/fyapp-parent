package com.onway.core.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderReturnDO;
import com.onway.platform.common.base.QueryResult;

/**
 * 
 * <p>
 * Title: AlipayComponent
 * </p>
 * <p>
 * Description: 支付宝支付接口
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月13日 上午11:26:15
 */
public interface AlipayComponent {

    /**
     * 支付宝网页支付
     * 
     * @param orderNo
     *            订单号
     * @param totalAmount
     *            总金额 （单位元）
     * @param orderTitle
     *            订单标题
     * @param params
     *            额外参数（参数必须进行UrlEncode）
     * @return 返回页面表单
     */
    QueryResult<String> webPageAliPay(String orderNo, String totalAmount, String orderTitle,
                                             String params);

    /**
     * 支付宝转账
     * 
     * @param orderNo
     * @param amount
     * @param account
     * @param realName
     * @return
     * @throws AlipayApiException
     */
    AlipayFundTransToaccountTransferResponse pay(String orderNo, String amount, String account,
                                                 String realName) throws AlipayApiException;

    /**
     * 统一收单交易退款接口
     * 
     * @param orderDO
     * @param orderReturnDO
     * @return
     * @throws AlipayApiException
     */
    AlipayTradeRefundResponse orderRefund(OrderDO orderDO, OrderReturnDO orderReturnDO)
                                                                                       throws AlipayApiException;

}
