/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: PlanChildTypeEnum</p>  
* <p>Description: 方案子类型枚举</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:34:20
 */
public enum PlanChildTypeEnum implements EnumBase {
	
	/** 返利（无限制日期）  */
	R0("0", "返利（无限制日期）"),
	
	/** 返利(满赠)（无限制日期）*/
	R0_1("0.1", "返利(满赠)（无限制日期）"),

    /** 优惠券（无限制日期）  */
	R1("1", "优惠券（无限制日期）"),

    /** 折扣（无限制日期） */
    R2("2", "折扣（无限制日期）"),
    
    /** 特殊日期（优惠）  */
    R3_1("3.1", "特殊日期（优惠）"),
    
    /** 特殊日期（折扣）  */
    R3_2("3.2", "特殊日期（折扣）"),
    
    /** 特殊日期（返利） */
    R3_3("3.3", "特殊日期（返利）"),
    
    /** 特殊日期（返利）(满赠)*/
	R3_4("3.4", "特殊日期（返利）(满赠)"),
    
    /** 新会员（优惠） */
    R4_1("4.1", "新会员（优惠）"),
    
    /** 新会员（折扣）*/
    R4_2("4.2", "新会员（折扣）"),
    
    /** 新会员（返利）*/
    R4_3("4.3", "新会员（返利）"),
    
    /** 新会员（返利）(满赠)*/
	R4_4("4.4", "新会员（返利）(满赠)"),
    
    /** 旧会员（优惠）*/
    R5_1("5.1", "旧会员（优惠）"),
    
    /** 旧会员（折扣）*/
    R5_2("5.2", "旧会员（折扣）"),
    
    /** 旧会员（返利）*/
    R5_3("5.3", "旧会员（返利）"),
    
    /** 旧会员（返利）(满赠)*/
	R5_4("5.4", "旧会员（返利）(满赠)"),

    ;
    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;

    /**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private PlanChildTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final PlanChildTypeEnum getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (PlanChildTypeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
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

}
