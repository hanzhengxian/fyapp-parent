/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.web.controller.service;

/**
 * 事件发布组件
 * 
 * @author guangdong.li
 * @version $Id: EventPublishComponent.java, v 0.1 2015年8月17日 下午2:29:03
 *          guangdong.li Exp $
 */
public interface EventPublishComponent {

	/**
	 * 发布事件
	 * 
	 * @param uniformEvent
	 *            事件信息
	 */
	public void publishEvent(UniformEvent uniformEvent);

}
