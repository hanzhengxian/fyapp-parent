/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.web.controller.service;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.onway.platform.common.service.RetryCallback;
import com.onway.platform.common.service.RetryTemplate;
import com.onway.platform.common.service.util.AssertUtil;

/**
 * 事件发布组件实现
 * 
 * @author guangdong.li
 * @version $Id: EventPublishComponentImpl.java, v 0.1 2015年8月17日 下午2:40:07 guangdong.li Exp $
 */
public class EventPublishComponentImpl implements ApplicationContextAware, EventPublishComponent {

    /** logger */
    private static final Logger logger        = Logger.getLogger(EventPublishComponentImpl.class);

    /** spring context 容器 */
    private ApplicationContext  applicationContext;

    /** 重试处理模板  */
    private RetryTemplate       retryTemplate = new RetryTemplate();

    /**
     * 
     */
    @Override
    public void publishEvent(final UniformEvent uniformEvent) {
        AssertUtil.notNull(uniformEvent, "异步事件对象为空");
        logger.info(MessageFormat.format("发布异步事件, uniformEvent:{0}", uniformEvent));

        // 自动重试多次业务执行
        retryTemplate.execute(new RetryCallback() {
            boolean executeResult = false;

            @Override
            public boolean isComplete(Object result) {
                return executeResult;
            }

            @Override
            public Object doWithRetry() {
                applicationContext.publishEvent(uniformEvent);
                executeResult = true;
                return executeResult;
            }

        });
    }

    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
