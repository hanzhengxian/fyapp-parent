/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.onway.web.controller.task;

import com.onway.web.controller.service.UniformEvent;

/**
 * 事件监听器 (支持同步事件和异步事件)
 * 
 * @author guangdong.li
 * @version $Id: EventHandler.java, v 0.1 2013-10-22 下午4:17:17 WJL Exp $
 */
public interface EventHandler {

	/**
	 * 判断是否处理该事件。
	 *
	 * @param event
	 *            待处理事件
	 * @return 返回true，表示需要处理该事件；返回false，表示不处理该事件
	 */
	public boolean aboutToHandleEvent(UniformEvent event);

	/**
	 * 事件监听器处理
	 * 
	 * @param event
	 *            event 待处理的事件
	 */
	public void handleEvent(UniformEvent event);

}
