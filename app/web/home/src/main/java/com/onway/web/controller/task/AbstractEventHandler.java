/**
 * onway.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.onway.web.controller.task;

import org.apache.log4j.Logger;

import com.onway.web.controller.service.UniformEvent;

/**
 * 
 * @author guangdong.li
 * @version $Id: SmsEventHandler.java, v 0.1 2017年9月18日 下午7:46:05 guangdong.li Exp $
 */
public abstract class AbstractEventHandler implements EventHandler {
    /** logger */
    protected static final Logger logger = Logger.getLogger(AbstractEventHandler.class);

    /**
     * 事件处理器
     * 
     * @see com.onway.settlecore.biz.listener.EventHandler#handleEvent(com.onway.settlecore.core.model.event.UniformEvent)
     */
    @Override
    public void handleEvent(UniformEvent event) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("事件发送开始...,事件Event:" + event.getClass().getSimpleName());
            }
            process(event);

            if (logger.isInfoEnabled()) {
                logger.info("[EVENT_NAME=" + event.getClass().getSimpleName() + "]处理完成...");
            }
        } catch (Exception e) {
            logger.error("[EVENT_NAME=" + event.getClass().getSimpleName() + "]处理异常===>", e);
        }
    }

    /** 
     * 抽象处理方法
     * 
     * @param event     统一发布事件
     */
    public abstract void process(UniformEvent event);

}
