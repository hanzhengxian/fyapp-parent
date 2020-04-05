/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.localcache.manager.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * ��ͬ�ı��ػ�������ת����StringBuffer
 * 
 * @author guangdong.li
 * @version $Id: CacheMessageUtil.java, v 0.1 2015��10��30�� ����11:04:10
 *          liudehong Exp $
 */
public class CacheMessageUtil {

	/** ENTERSTR */
	public static final String ENTERSTR = "\n";

	/**
	 * ��Map������ת����
	 * 
	 * @param map
	 * 
	 * @return StringBuffer
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String map2String(Map map) {

		StringBuffer cacheMessage = new StringBuffer();

		for (Iterator<String> i = map.keySet().iterator(); i.hasNext();) {

			String name = i.next();

			cacheMessage.append(ENTERSTR
					+ "["
					+ name
					+ "={"
					+ ToStringBuilder.reflectionToString(map.get(name),
							ToStringStyle.SHORT_PREFIX_STYLE) + "}]");

		}

		return cacheMessage.toString();
	}
}
