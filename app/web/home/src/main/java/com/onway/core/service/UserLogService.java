/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.core.service;

import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;

/**
 * �û�������־����
 * 
 * @author guangdong.li
 * @version $Id: UserLogService.java, v 0.1 18 Feb 2016 17:24:08 guangdong.li
 *          Exp $
 */
public interface UserLogService {

	/**
	 * ��¼�û�������־
	 * 
	 * @param cifUserLogInfo
	 * @return
	 */
	@DigestLogAnnotated(serviceType = ServiceTypeEnum.SERVICE, bizKey = "��¼�û�������־")
	public BaseResult storeUserLog(Object cifUserLogInfo);

}
