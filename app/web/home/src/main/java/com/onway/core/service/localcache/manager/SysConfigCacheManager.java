/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.core.service.localcache.manager;

import com.onway.model.enums.SysConfigCacheKeyEnum;


/**
 * 系统参数缓存
 * 
 * @author guangdong.li
 * @version $Id: SysConfigCacheManager.java, v 0.1 2015�?1�?�?下午3:33:06 liudehong Exp $
 */
public interface SysConfigCacheManager {

    /**
     * 根据参数配置的key获取对应的value
     * 
     * @param configKeyEnum 配置的key
     * 
     * @return
     */
    public String getConfigValue(SysConfigCacheKeyEnum configKeyEnum);

    /**
     * 根据key获取参数
     * 
     * @param configKey
     * @return
     */
    public String getConfigValue(String configKey);
    
    /**
     * 刷新缓存
     */
    public void reload();
}
