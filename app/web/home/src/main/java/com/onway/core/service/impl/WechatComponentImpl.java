package com.onway.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.core.service.pay.IpKit;
import com.onway.core.service.pay.PaymentKit;
import com.onway.fyapp.common.dal.daointerface.OrderRefundLogDAO;
import com.onway.fyapp.common.dal.dataobject.OrderRefundLogDO;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.model.enums.refund.RefundStatusEnum;
import com.onway.model.enums.refund.RefundTypeEnum;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.platform.common.service.template.ServiceCheckCallback;
import com.onway.utils.ParamsUtil;

/**
 * 微信企业转账到用户
 * 
 * @author jiaming.zhu
 * @version $Id: WechatComponentImpl.java, v 0.1 2017年3月6日 上午11:23:19 zjm Exp $
 */
public class WechatComponentImpl extends AbstractServiceImpl implements WechatComponent {

    /** logger */
    protected static final Logger logger = Logger.getLogger(WechatComponentImpl.class);

    private SysConfigCacheManager sysConfigCacheManager;
    private OrderRefundLogDAO     orderRefundLogDAO;

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    public void setOrderRefundLogDAO(OrderRefundLogDAO orderRefundLogDAO) {
        this.orderRefundLogDAO = orderRefundLogDAO;
    }

    /**
     * 
     * @see 微信支付
     */
    @Override
    public BaseResult withdrawByWechat(final String orderNo, final Money money,
                                       final String openId, final HttpServletRequest request) {
        final BaseResult rst = new BaseResult(false);
        serviceTemplate.execute(rst, new ServiceCheckCallback() {
            @Override
            public void check() {
            }

            @Override
            public void executeService() {
                // CA 证书协议问题

                String appKey = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_APP_SECRET_ID);

                // 【3】.微信支付参数 ?????
                String mch_appid = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_APP_ID);// 申请商户号的appid或商户号绑定的appid（企业号corpid即为此appId）
                String mch_id = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_PAY_MCH_ID);// 微信支付分配的商户号
                String nonce_str = getRandomString(12);// 随机字符串，不长于32位
                String partner_trade_no = orderNo;// 商户订单号，需保持唯一性
                String openid = openId;// 商户appid下，某用户的openid
                String check_name = "NO_CHECK";// NO_CHECK：不校验真实姓名
                                               // FORCE_CHECK：强校验真实姓名
                String amount = String.valueOf(money.getCent());// 金额 企业付款金额，单位为分
                String desc = "商户退款（胡庆余堂）";// 企业付款操作说明信息。必填。
                String spbill_create_ip = IpKit.getRealIpV3(request);
                if (StringUtils.isBlank(spbill_create_ip)) {
                    spbill_create_ip = "127.0.0.1";
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("mch_appid", mch_appid);//商户账号appid
                params.put("mchid", mch_id);//商户号
                params.put("nonce_str", nonce_str);//随机字符串
                params.put("partner_trade_no", partner_trade_no);//商户订单号
                params.put("openid", openid);//用户openid
                params.put("check_name", check_name);
                //                if (!SystemHelper.isProd()) {
                //                    amount = "100";
                //                }
                params.put("amount", amount);
                params.put("desc", desc);
                params.put("spbill_create_ip", spbill_create_ip);
                try {
                    String sign = PaymentKit.createSign(params, appKey);// 签名
                    params.put("sign", sign);

                    String xml = PaymentKit.toXml(params);
                    //                    String returnXml = HttpUtils.executePostMethod(
                    //                        "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers",
                    //                        "UTF-8", xml);
                    //                    logger.info("返回结果:" + returnXml);

                    // 携带证书请求
                    String caReturn = getCA(
                        "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", xml,
                        mch_id);
                    logger.info("CA返回结果:" + caReturn);

                    ParamsUtil paramsUtil = new ParamsUtil();
                    String returnCode = paramsUtil.getValue("return_code", caReturn);// SUCCESS/FAIL
                                                                                     // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
                    if (StringUtils.contains(returnCode, "FAIL")) {
                        rst.setSuccess(false);
                        rst.setMessage(paramsUtil.getValueCDATA(paramsUtil.getValue("return_msg",
                            caReturn)));
                        return;
                    }
                    if (StringUtils.contains(returnCode, "SUCCESS")) {
                        String resultCode = paramsUtil.getValue("result_code", caReturn);
                        if (StringUtils.contains(resultCode, "SUCCESS")) {
                            rst.setSuccess(true);
                        } else {
                            rst.setSuccess(false);
                            rst.setMessage(paramsUtil.getValueCDATA(paramsUtil.getValue(
                                "err_code_des", caReturn)) + spbill_create_ip);
                            return;
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        return rst;
    }

    @Override
    public BaseResult refundByWechat(final String orderNo, final String orderId,
                                     final String refundNo, final Money money,
                                     final Money refundMoney, final int linkReturnId) {
        final BaseResult rst = new BaseResult(false);
        serviceTemplate.execute(rst, new ServiceCheckCallback() {
            @Override
            public void check() {
            }

            @Override
            public void executeService() {
                // CA 证书协议问题

                String appKey = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_APP_SECRET_ID);

                // 【3】.微信支付参数 ?????
                String appid = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_APP_ID);// 申请商户号的appid或商户号绑定的appid（企业号corpid即为此appId）
                String mch_id = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WE_APP_PAY_PAY_MCH_ID);// 微信支付分配的商户号
                String nonce_str = getRandomString(12);// 随机字符串，不长于32位
                String out_trade_no = orderNo;// 商户订单号
                String out_refund_no = refundNo;// 商户系统内部的退款单号
                String total_fee = String.valueOf(money.getCent());// 订单总金额
                String refund_fee = String.valueOf(refundMoney.getCent());// 退款总金额

                Map<String, String> params = new HashMap<String, String>();
                params.put("appid", appid);//公众账号ID
                params.put("mch_id", mch_id);//商户号
                params.put("nonce_str", nonce_str);//随机字符串
                params.put("out_trade_no", out_trade_no + "00");//原支付订单的商户订单号 (微信支付时的订单号 有特殊处理的  目前默认加了00)
                params.put("out_refund_no", out_refund_no);//商户系统内部的退款单号
                params.put("total_fee", total_fee);
                params.put("refund_fee", refund_fee);
                String notify_url = sysConfigCacheManager
                    .getConfigValue(SysConfigCacheKeyEnum.WECHAT_REFUND_NOTIFY_URL);
                params.put("notify_url", notify_url);
                try {
                    String sign = PaymentKit.createSign(params, appKey);// 签名
                    params.put("sign", sign);

                    String xml = PaymentKit.toXml(params);

                    // 携带证书请求
                    String caReturn = getCA("https://api.mch.weixin.qq.com/secapi/pay/refund", xml,
                        mch_id);
                    logger.info("退款返回结果" + orderNo + ":" + caReturn);

                    ParamsUtil paramsUtil = new ParamsUtil();
                    String returnCode = paramsUtil.getValue("return_code", caReturn);// SUCCESS/FAIL
                                                                                     // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
                    //记录
                    OrderRefundLogDO orderRefundLog = new OrderRefundLogDO();

                    orderRefundLog.setOrderNo(orderNo);
                    orderRefundLog.setOrderId(orderId);
                    orderRefundLog.setRefundNo(refundNo);
                    orderRefundLog.setRefundType(RefundTypeEnum.WECHAT.getCode());
                    orderRefundLog.setAmount(refundMoney);
                    orderRefundLog.setStatus(RefundStatusEnum.INIT.getCode());
                    orderRefundLog.setResultMsg(returnCode);
                    orderRefundLog.setReturnId(linkReturnId);

                    if (StringUtils.contains(returnCode, "FAIL")) {
                        rst.setSuccess(false);

                        orderRefundLog.setStatus(RefundStatusEnum.FAIL.getCode());
                        orderRefundLog.setErrorMsg(paramsUtil.getValueCDATA(paramsUtil.getValue(
                            "return_msg", caReturn)));

                    } else if (StringUtils.contains(returnCode, "SUCCESS")) {
                        String resultCode = paramsUtil.getValue("result_code", caReturn);
                        if (StringUtils.contains(resultCode, "SUCCESS")) {
                            rst.setSuccess(true);
                        } else {
                            rst.setSuccess(false);

                            orderRefundLog.setStatus(RefundStatusEnum.FAIL.getCode());
                            orderRefundLog.setErrorMsg(paramsUtil.getValueCDATA(paramsUtil
                                .getValue("err_code_des", caReturn)));
                        }
                    }

                    orderRefundLogDAO.newOrderRefund(orderRefundLog);

                } catch (Exception e) {

                }
            }
        });
        return rst;
    }

    /**
     * 微信支付获取随机串
     * 
     * @param length
     * @return
     */
    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";// 含有字符和数字的字符串
        Random random = new Random();// 随机类初始化
        StringBuffer sb = new StringBuffer();// StringBuffer类生成，为了拼接字符串

        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(62);// [0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * @param url
     *            api接口
     * @param data
     *            请求 xml数据
     * @param caPwd
     *            证书设置密码 初始密码是商户ID
     * 
     */
    private String getCA(String url, String data, String caPwd) {

        String jsonStr = "";
        try {
            String pkcsUrl = "/usr/local/tomcat/cert/apiclient_cert.p12";
            //            String pkcsUrl = "D:\\Java\\tomcat_nyh\\tomcat8105\\cert\\apiclient_cert.p12";
            // sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.PKCS_CER_URL);
            // 指定读取证书格式为PKCS12
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            // 读取 本机 存放的PKCS12证书文件
            FileInputStream instream = new FileInputStream(new File(pkcsUrl));
            try {
                // 指定PKCS12的密码(商户ID)
                keyStore.load(instream, caPwd.toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, caPwd.toCharArray()).build();
            // 指定TLS版本
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
                new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            // 设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
                .build();

            HttpPost httpost = new HttpPost(url);
            // 设置请求头
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            // 设置请求参数
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            HttpEntity entity = response.getEntity();
            jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");

            // httpost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            // // 执行请求
            // HttpResponse response = httpclient.execute(httpost);
            // HttpEntity entity = response.getEntity();
            // jsonStr = EntityUtils.toString(entity, "utf-8");

            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
