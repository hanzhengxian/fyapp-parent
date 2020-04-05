package com.onway.core.service.impl;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.code.CodeGenerateComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.fyapp.common.dal.daointerface.OrderRefundLogDAO;
import com.onway.fyapp.common.dal.dataobject.OrderDO;
import com.onway.fyapp.common.dal.dataobject.OrderRefundLogDO;
import com.onway.fyapp.common.dal.dataobject.OrderReturnDO;
import com.onway.model.enums.BizTypeEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.refund.RefundStatusEnum;
import com.onway.model.enums.refund.RefundTypeEnum;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.utils.LogUtil;

/**
 * 
 * <p>
 * Title: AlipayComponentImpl
 * </p>
 * <p>
 * Description: 支付宝支付
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月13日 上午11:27:28
 */
public class AlipayComponentImpl implements AlipayComponent {

    private static final Logger   logger = Logger.getLogger(AlipayComponentImpl.class);

    private SysConfigCacheManager sysConfigCacheManager;

    private OrderRefundLogDAO     orderRefundLogDAO;

    private CodeGenerateComponent codeGenerateComponent;

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    public void setOrderRefundLogDAO(OrderRefundLogDAO orderRefundLogDAO) {
        this.orderRefundLogDAO = orderRefundLogDAO;
    }

    public void setCodeGenerateComponent(CodeGenerateComponent codeGenerateComponent) {
        this.codeGenerateComponent = codeGenerateComponent;
    }

    private static final String FORMAT     = "json";
    private static final String CHARSET    = "UTF-8";
    private static final String SIGN_TYPE  = "RSA2";

    private static final String PAYEE_TYPE = "ALIPAY_LOGONID";

    /**
     * 支付宝网页支付
     */
    @Override
    public QueryResult<String> webPageAliPay(String orderNo, String totalAmount, String orderTitle,
                                             String params) {
        QueryResult<String> result = new QueryResult<String>(true);
        AlipayClient alipayClient = new DefaultAlipayClient(
            sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ALIPAY_URL),
            sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ALIPAY_APPID),
            sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PRIVATE_KEY), FORMAT,
            CHARSET, sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PUBLIC_KEY),
            SIGN_TYPE); // 获得初始化的AlipayClient

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PAGE_RETURN_URL));
        alipayRequest.setNotifyUrl(sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PAGE_NOTIFY_URL));// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" + "\"out_trade_no\":\"" + orderNo + "\","
                                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
                                    + "\"total_amount\":" + totalAmount + "," + "\"subject\":\""
                                    + orderTitle + "\"," + "\"passback_params\":\"" + params + "\""
                                    + "}");// 填充业务参数
        String form = "";
        try {
            AlipayTradePagePayResponse pageExecute = alipayClient.pageExecute(alipayRequest);
            form = pageExecute.getBody(); // 调用SDK生成表单
            LogUtil.info(logger, MessageFormat.format("支付宝网页支付,表单数据：{0}", new Object[] { form }));
        } catch (AlipayApiException e) {
            e.printStackTrace();
            LogUtil.error(logger,
                MessageFormat.format("支付宝网页支付,表单异常：{0}", new Object[] { e.getMessage() }));
        }
        result.setResultObject(form);
        return result;
    }

    /**
     * 单笔转账
     * 
     * @param orderNo
     * @param amount
     * @param account
     * @return
     * @throws AlipayApiException
     */
    @Override
    public AlipayFundTransToaccountTransferResponse pay(String orderNo, String amount,
                                                        String account, String realName)
                                                                                        throws AlipayApiException {
        String freebuyAlipayUrl = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_URL);
        String alipayAppid = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_APPID);
        String alipayPrivateKey = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PRIVATE_KEY);
        String alipayPublicKey = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PUBLIC_KEY);
        AlipayClient alipayClient = new DefaultAlipayClient(freebuyAlipayUrl, alipayAppid,
            alipayPrivateKey, FORMAT, CHARSET, alipayPublicKey, SIGN_TYPE);
        //        if (!SystemHelper.isProd()) {
        //            amount = "1";
        //        }
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" + "\"out_biz_no\":\"" + orderNo + "\"," + "\"payee_type\":\""
                              + PAYEE_TYPE + "\"," + "\"payee_account\":\"" + account + "\","
                              + "\"payee_real_name\":\"" + realName + "\"," + "\"amount\":\""
                              + amount + "\"," + "\"remark\":\"退款\"" + "}");
        AlipayFundTransToaccountTransferResponse response = null;
        response = alipayClient.execute(request);
        return response;
    }

    @Override
    public AlipayTradeRefundResponse orderRefund(OrderDO orderDO, OrderReturnDO orderReturnDO)
                                                                                              throws AlipayApiException {

        String alipayUrl = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ALIPAY_URL);
        String alipayAppid = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_APPID);
        String alipayPrivateKey = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PRIVATE_KEY);
        String alipayPublicKey = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.ALIPAY_PUBLIC_KEY);

        AlipayClient alipayClient = new DefaultAlipayClient(alipayUrl, alipayAppid,
            alipayPrivateKey, FORMAT, CHARSET, alipayPublicKey, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //原支付时订单号 参考  OrderComponentImpl.webPageAliPay  所有都是统一父订单支付  所以在原订单号后 加字符 "00"
        String out_trade_no = orderDO.getOrderId() + "00";
        String refund_amount = orderReturnDO.getShouldReturnMoney().toSimpleString();
        String refund_currency = "CNY";
        //退款唯一单号  由 ChildOrderId + Return_ID  保持唯一
        String out_request_no = orderReturnDO.getChildOrderId() + orderReturnDO.getId();
        String goods_id = orderDO.getProductId();
        String goods_name = orderDO.getProductName();
        double quantity = orderReturnDO.getChooseNum();
        double price = Double.parseDouble(orderDO.getProductPrice().toSimpleString());
        request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\","
                              + "\"refund_amount\":" + refund_amount + ","
                              + "\"refund_currency\":\"" + refund_currency + "\","
                              + "\"out_request_no\":\"" + out_request_no + "\","
                              + "\"goods_detail\":[{" + "\"goods_id\":\"" + goods_id + "\","
                              + "\"goods_name\":\"" + goods_name + "\"," + "\"quantity\":"
                              + quantity + "," + "\"price\":" + price + "}]" + "}");
        AlipayTradeRefundResponse response = alipayClient.execute(request);

        try {
            String refundNo = codeGenerateComponent.nextCodeByType(BizTypeEnum.REFUND_NO);
            //记录
            OrderRefundLogDO orderRefundLog = new OrderRefundLogDO();
            orderRefundLog.setOrderNo(orderDO.getOrderId());
            orderRefundLog.setOrderId(orderDO.getChildOrderId());
            orderRefundLog.setRefundNo(refundNo);
            orderRefundLog.setRefundType(RefundTypeEnum.ALI.getCode());
            orderRefundLog.setAmount(orderReturnDO.getShouldReturnMoney());
            if (StringUtils.equals(response.getCode(), "10000")) {
                orderRefundLog.setStatus(RefundStatusEnum.SUCCESS.getCode());
            } else {
                orderRefundLog.setStatus(RefundStatusEnum.FAIL.getCode());
                orderRefundLog.setErrorMsg(response.getSubMsg());
            }
            orderRefundLog.setResultMsg(response.getBody());
            orderRefundLog.setReturnId(orderReturnDO.getId());

            orderRefundLogDAO.newOrderRefund(orderRefundLog);
        } catch (Exception e) {
            LogUtil.error(logger,
                MessageFormat.format("支付宝退款,退款log保存异常：{0}", new Object[] { e.getMessage() }));
        }
        return response;
    }

    // public void doPost(HttpServletRequest httpRequest,
    // HttpServletResponse httpResponse) throws ServletException, IOException {
    // AlipayClient alipayClient = new
    // DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID,
    // APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    // //获得初始化的AlipayClient
    // AlipayTradePagePayRequest alipayRequest = new
    // AlipayTradePagePayRequest();//创建API对应的request
    // alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
    // alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
    // alipayRequest.setBizContent("{" +
    // "    \"out_trade_no\":\"20150320010101001\"," +
    // "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
    // "    \"total_amount\":88.88," +
    // "    \"subject\":\"Iphone6 16G\"," +
    // "    \"body\":\"Iphone6 16G\"," +
    // "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
    // +
    // "    \"extend_params\":{" +
    // "    \"sys_service_provider_id\":\"2088511833207846\"" +
    // "    }"+
    // "  }");//填充业务参数
    // String form="";
    // try {
    // form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
    // } catch (AlipayApiException e) {
    // e.printStackTrace();
    // }
    // httpResponse.setContentType("text/html;charset=" + CHARSET);
    // httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
    // httpResponse.getWriter().flush();
    // httpResponse.getWriter().close();
    // }

}
