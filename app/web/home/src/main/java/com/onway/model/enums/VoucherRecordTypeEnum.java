/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 庆余卡明细类型
 * 
 * @author yugang.ni
 * @version $Id: VoucherRecordTypeEnum.java, v 0.1 2018年10月29日 下午1:53:39 Administrator Exp $
 */
public enum VoucherRecordTypeEnum implements EnumBase {
    
    /** 收入 +*/
    ORDER_IN("1","充值"),
    
    /** 支出 -*/
    ORDER_OUT("2","购物"),
    
    /** 支出退回 +*/
    ORDER_OUT_RETURN("2.1","购物退回"),
    
    /** 转赠 -*/
    GIFT_OUT("3","转赠"),
    
    /** 获赠 +*/
    GIFT_IN("4","获赠")
    
    ;
    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;

    /**
     * 构造方法
     * 
     * @param code
     *            枚举编号
     * @param message
     *            枚举详情
     */
    private VoucherRecordTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * 
     * @param code
     *            枚举值
     * @return 如果不存在返回NUll<br/>
     *         如果存在返回相关值
     */
    public static final VoucherRecordTypeEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (VoucherRecordTypeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message
     *            value to be assigned to property message
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
     * @param code
     *            value to be assigned to property code
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
