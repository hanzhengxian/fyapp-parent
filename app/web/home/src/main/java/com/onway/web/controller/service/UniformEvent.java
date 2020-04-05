/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller.service;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.context.ApplicationEvent;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.helper.TimeKey;

/**
 * 统一发布事件(内部事件统一继承本类)
 * <pre>
 *      TODO 未来接入notify 系统
 * </pre>
 * 
 * @author guangdong.li
 * @version $Id: UniformEvent.java, v 0.1 2013-10-30 下午4:03:09 WJL Exp $
 */
public abstract class UniformEvent extends ApplicationEvent implements Serializable {

    /** serialVersionUID */
    private static final long  serialVersionUID          = 1812150333666127525L;

    /** task创建事件 */
    public static final String EVENTTYPE_TASK_CREATE     = "task-create";

    /** listener创建事件 */
    public static final String EVENTTYPE_LISTENER_CREATE = "listener-create";

    /** 
     * 事件主题。
     * <p>
     * 监听时，既可以监听在事件主题树的叶结点上，也可以监听在事件主题树的中间结点上，甚至可以以通配符的形式选择监听主题。
     */
    private String             topic;

    /** 异步事件key（由调用者传入线程事件搓 ）*/
    private String             EVENT_ID                  = "";

    /**
     * 构建统一时间，生成事件搓，优先获取当前线程timeKey,null情况生成新timeKey
     * 
     * @param source
     */
    public UniformEvent(Object source) {
        super(source);
        this.EVENT_ID = StringUtils.defaultIfBlank(TimeKey.getTimeKey(), TimeKey.createTimeKey());
    }

    /**
     * 构建统一时间，生成事件搓，优先获取当前线程timeKey,null情况生成新timeKey
     * 
     * @param topic         事件主题
     * @param source        事件对象
     */
    public UniformEvent(String topic, Object source) {
        super(source);
        this.topic = topic;
        this.EVENT_ID = StringUtils.defaultIfBlank(TimeKey.getTimeKey(), TimeKey.createTimeKey());
    }

    /**
     * Getter method for property <tt>eVENT_ID</tt>.
     * 
     * @return property value of EVENT_ID
     */
    public String getEVENT_ID() {
        return EVENT_ID;
    }

    /**
     * Getter method for property <tt>topic</tt>.
     * 
     * @return property value of topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Setter method for property <tt>topic</tt>.
     * 
     * @param topic value to be assigned to property topic
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /** 
     * @see java.util.EventObject#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
