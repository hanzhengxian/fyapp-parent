package com.onway.jdbc;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.onway.common.lang.StringUtils;
import com.onway.core.service.Des3;
import com.onway.platform.common.configration.ConfigrationFactory;

/**
 * 增加xml动态变量替换支持.
 * <pre>
 *      1. 优先系统统配system_config获取变量.
 *      2. 变量不存在，则从系统环境变量中获取.
 *      3. 从自定义环境中获取.
 * </pre>
 * 
 * @author guangdong.li
 * @version $Id: XmlPropertyPlaceholderSupport.java, v 0.1 2013-9-24 下午8:02:34  Exp $
 */
public class XmlPropertyPlaceholderSupport extends PropertyPlaceholderConfigurer {

    /** 搜索系统变量开关 */
    private boolean searchSystemEnvironment = true;

    /** 搜索system_config变量开关 */
    private boolean searchSysConfig         = true;

    /**
     * 变量替换顺序调整
     * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#resolvePlaceholder(java.lang.String, java.util.Properties, int)
     */
    @Override
    protected String resolvePlaceholder(String placeholder, Properties props,
                                        int systemPropertiesMode) {
        String propVal = null;
        if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
            propVal = resolveSystemProperty(placeholder);
        }
        if (propVal == null) {
            propVal = resolvePlaceholder(placeholder, props);
        }
        if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
            propVal = resolveSystemProperty(placeholder);
        }
        // 针对密码DES解密处理
        if (StringUtils.contains(placeholder, "password")) {
            propVal = Des3.decode(propVal);
        }
        return propVal;
    }

    /**
     * 覆盖框架默认加载顺序
     * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#resolveSystemProperty(java.lang.String)
     */
    @Override
    protected String resolveSystemProperty(String key) {
        try {
            String value = StringUtils.EMPTY_STRING;
            //【1】 优先system_config文件搜索
            if (searchSysConfig) {
                value = ConfigrationFactory.getConfigration().getPropertyValue(key);
            }

            //【2】 从系统环境变量中搜索
            if (StringUtils.isBlank(value) && this.searchSystemEnvironment) {
                value = System.getProperty(key);
            }

            //【3】 从自定义环境变量中搜索
            if (StringUtils.isBlank(value) && this.searchSystemEnvironment) {
                value = System.getenv(key);
            }

            return value;
        } catch (Throwable ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Could not access system property '" + key + "': " + ex);
            }
            return null;
        }
    }

    public void setSearchSystemEnvironment(boolean searchSystemEnvironment) {
        this.searchSystemEnvironment = searchSystemEnvironment;
    }

    public void setSearchSysConfig(boolean searchSysConfig) {
        this.searchSysConfig = searchSysConfig;
    }

}
