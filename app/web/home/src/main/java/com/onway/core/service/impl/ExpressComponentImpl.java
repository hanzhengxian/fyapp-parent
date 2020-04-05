package com.onway.core.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;
import com.onway.common.lang.DateUtils;
import com.onway.common.lang.HttpUtils;
import com.onway.common.lang.StringUtils;
import com.onway.core.service.ExpressComponent;
import com.onway.core.service.localcache.manager.SysConfigCacheManager;
import com.onway.model.enums.Kuaidi100StateEnum;
import com.onway.model.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.service.template.AbstractServiceImpl;
import com.onway.result.express.ExpressDataPojo;
import com.onway.result.express.ExpressQueryPojo;

/**
 * 快递100企业版调用方式接口对接实现
 * 
 */
public class ExpressComponentImpl extends AbstractServiceImpl implements ExpressComponent {

    /*
     * 日志
     */
    public Log                    logger = LogFactory.getLog(ExpressComponentImpl.class);

    private SysConfigCacheManager sysConfigCacheManager;

    public void setSysConfigCacheManager(SysConfigCacheManager sysConfigCacheManager) {
        this.sysConfigCacheManager = sysConfigCacheManager;
    }

    /**
     * 
     * @param com
     *            快递公司code
     * @param num
     *            快递编号
     * @return
     */
    @Override
    public QueryResult<ExpressQueryPojo> getKuaiDiInfo4Firm(String com, String num) {

        QueryResult<ExpressQueryPojo> result = new QueryResult<ExpressQueryPojo>(false);

        String param = "{\"com\":\"" + com + "\",\"num\":\"" + num + "\"}";// 字段拼接

        String customer = sysConfigCacheManager
            .getConfigValue(SysConfigCacheKeyEnum.KUAI_DI_CUSTOMER);//"81F6C62AB7DE3C460F8FADCBCEB5A0B4";// 快递100分配给公司的的公司编号
        String key = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.KUAI_DI_KEY);//"TsTSbhIm433";// 快递100分配给公司实时快递查询接口的授权密匙
        String sign = encode(param + key + customer);// MD5加密，加密结果为字母大写

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", param);
        params.put("sign", sign);
        params.put("customer", customer);
        String resp = null;
        try {
            resp = HttpUtils.executePostMethod("http://poll.kuaidi100.com/poll/query.do", "UTF-8",
                params);

            ExpressQueryPojo pojo = JSON.parseObject(resp, ExpressQueryPojo.class);

            if (pojo == null || !StringUtils.equals(pojo.getStatus(), "200")) {
                result.setMessage("查询失败");
                return result;
            }

            for (ExpressDataPojo expressDataPojo : pojo.getData()) {
                String time = expressDataPojo.getTime();
                Date dateTime = DateUtils.parseDate(time, DateUtils.newFormat);
                String date = DateUtils.format(dateTime, DateUtils.webFormat);
                String expressTime = DateUtils.format(dateTime, DateUtils.HH_MM);
                expressDataPojo.setDate(date);
                expressDataPojo.setExpressTime(expressTime);
            }

            String state = pojo.getState();
            Kuaidi100StateEnum kuaidi100StateEnum = Kuaidi100StateEnum.getByCode(state);
            if (null != kuaidi100StateEnum)
                pojo.setState(kuaidi100StateEnum.message());

            result.setSuccess(true);
            result.setResultObject(pojo);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("快递查询错误");
        }
        return result;
    }

    public final static String encode(String s) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(s.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果 (大写)
            return buffer.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
