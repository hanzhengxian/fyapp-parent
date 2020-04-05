/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: BizTypeEnum</p>  
* <p>Description: 编码生成枚举类</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:16:13
 */
public enum BizTypeEnum implements EnumBase {
	/** 商品编号 */
    PRODUCT_NO("PRODUCT_NO", "商品编号", 1106),
    
    /** 用户编号 */
	USER_ID("USER_ID", "用户编号", 1001),

	/** 团队编号 */
	TEAM_NO("TEAM_NO", "团队编号", 2001),
	
	/** 方案组编号 */
	PLAN_TEAM_NO("PLAN_TEAM_NO", "方案组编号", 3001),
	
	/** 优惠方案编号 */
	DISCOUNT_NO("DISCOUNT_NO", "优惠方案编号", 3003),
	
	/** 推荐人返利方案编号 */
	RETURN_ID("RETURN_ID", "推荐人返利方案编号", 3004),
	
	/** 方案编号 */
	PLAN_NO("PLAN_NO", "方案编号", 3002),
	
	/** 养生知识/新闻公告编号 */
	KNOW_ID("KNOW_ID", "养生知识/新闻公告ID", 1006),
	
	/** 顺风下单编号 */
	SF_ORDER_ID("SF_ORDER_ID", "顺风下单编号", 3048),
	
	/** 顺风下单编号 */
    SF_ORDER_ID_DEV("SF_ORDER_ID_DEV", "顺风下单编号", 2050),
    
    REFUND_NO("REFUND_NO", "退款编号", 1300),
    
    ;

    private String code;

    private String message;

    private int    value;

    BizTypeEnum(String code, String message, int value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public String code() {
        return code;
    }

    /**
     * 获取枚举消息
     *
     * @return
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * 获取枚举值
     *
     * @return
     */
    @Override
    public Number value() {
        return value;
    }
}
