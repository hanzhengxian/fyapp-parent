package com.onway.web.template;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.BaseResultCodeEnum;
import com.onway.platform.common.enums.EnumBase;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.helper.ProfilerHelper;
import com.onway.result.JsonResult;

/**
 * 服务模板的实现
 * 
 * @author guangdong.li
 * @version $Id: ServiceTemplateImpl.java, v 0.1 15 Feb 2016 11:44:58
 *          guangdong.li Exp $
 */
public class ControllerTemplateImpl implements ControllerTemplate {

    /** logger */
    private static final Logger logger = Logger.getLogger(ControllerTemplateImpl.class);

    /** 事务模板 */
    @Resource
    private TransactionTemplate transactionTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void execute(final JsonResult jsonResult, final ControllerCallBack callBack) {

        ProfilerHelper.enter("开始模板操作处理");
        transactionTemplate.execute(new TransactionCallback() {
            /**
             * @see org.springframework.transaction.support.TransactionCallback#doInTransaction1(org.springframework.transaction.TransactionStatus)
             */
            public Object doInTransaction(TransactionStatus status) {
                try {
                    ProfilerHelper.enter("参数校验");
                    {
                        callBack.check();
                    }
                    ProfilerHelper.release();

                    ProfilerHelper.enter("执行业务操作");
                    {
                        callBack.executeService();
                    }
                    ProfilerHelper.release();

                    if (jsonResult.isSuccess()) {

                    }
                } catch (BaseRuntimeException ce) {
                    logger.warn("业务处理业务异常", ce);
                    status.setRollbackOnly();
                    EnumBase errorCode = ce.getErrorEnum();
                    if (errorCode == null) {
                        errorCode = BaseResultCodeEnum.getResultCodeEnumByCode(ce.getCode());
                    }
                    if (errorCode == null) {
                        errorCode = BaseResultCodeEnum.SYSTEM_FAILURE;
                    }
                    jsonResult.markResult(false, errorCode);
                    if (!StringUtils.isBlank(ce.getMessage())) {
                        jsonResult.setMessage(ce.getMessage());
                    }
                } catch (Throwable e) {
                    logger.error("业务处理系统异常", e);
                    status.setRollbackOnly();
                    jsonResult.markResult(false, BaseResultCodeEnum.SYSTEM_ERROR);
                } finally {
                    if (logger.isInfoEnabled()) {
                        logger.info("业务处理系统操作结果：" + jsonResult);
                    }
                    ProfilerHelper.release();
                }
                return jsonResult;
            }
        });
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

}
