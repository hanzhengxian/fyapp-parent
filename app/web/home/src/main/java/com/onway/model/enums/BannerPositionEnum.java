/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: BannerPositionEnum</p>  
* <p>Description: banner 位置枚举</p>  
* @author yugang.ni  
* @date 2018年6月27日  下午4:45:24
 */
public enum BannerPositionEnum implements EnumBase {

    /** 微信首页 */
    WECHAT_HOME("WECHAT_HOME", "微信首页", 0),

    /** 微信商品页 */
    WECHAT_PRODUCT("WECHAT_PRODUCT", "微信商品页", 1),
    
    /** 微信首页大图 */
    WECHAT_INDEX("WECHAT_INDEX", "微信首页大图", 4),

    /** 微信首页分类背景图 */
    WECHAT_INDEX_BACK("WECHAT_INDEX_BACK", "微信首页分类背景图", 5), 
    
    /** WEB首页 */
    WEB_HOME("WEB_HOME", "WEB首页", 2),

    /** WEB商城页 */
    WEB_SHOP("WEB_SHOP", "WEB商城页", 3),
    
    /** WEB商城页 活动左 */
    WEB_SHOP_LEFT("WEB_SHOP_LEFT", "WEB商城页 活动左", 6),
    
    /** WEB商城页 活动右 */
    WEB_SHOP_RIGHT("WEB_SHOP_RIGHT", "WEB商城页 活动右", 7),
    
    /** WEB养生知识 */
    WEB_KNOWLEDGE("WEB_KNOWLEDGE","WEB养生知识", 8),
    
    /** WEB新闻公告 */
    WEB_NEWS("WEB_NEWS","WEB新闻公告", 9),

    /** WEB庆余门店 */
    WEB_SHOPS("WEB_SHOPS","WEB庆余门店", 10),
    
    /** WEB关于我们 */
    WEB_US("WEB_US","WEB关于我们", 11),
    ;
    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;
    
    /** 枚举详情 */
    private Number value;

    /**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private BannerPositionEnum(String code, String message, Number value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final BannerPositionEnum getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (BannerPositionEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }
    
    /**
     * 通过枚举<code>value</code>获得枚举。
     * @param value 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final BannerPositionEnum getByValue(Number value) {
        for (BannerPositionEnum item : values()) {
            if (item.getValue() == value) {
                return item;
            }
        }
        return null;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** 
     * @see com.onway.platform.common.enums.EnumBase#message()
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** 
     * @see com.onway.platform.common.enums.EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

}
