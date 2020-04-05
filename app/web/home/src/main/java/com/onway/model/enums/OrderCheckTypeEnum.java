/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * 
 * @author yugang.ni
 * @version $Id: OrderCheckTypeEnum.java, v 0.1 2018年12月28日 下午6:31:35 Administrator Exp $
 */
public enum OrderCheckTypeEnum implements EnumBase {

    /** 退款审核 */
    FOR_RETURN_MONEY("1", "退款审核"),
    
    /** 退款退货审核 */
    FOR_RETURN_GOODS("2", "退款退货审核"),
    
    /** 异常订单取消审核 */
    FOR_CANCLE_ERR("3", "异常订单取消审核"),
    
    /** 收银订单审核 */
    CASHIER_ORDER("4", "收银订单审核 "),
    
    /** 调拨审核*/
    ALLOCATE_PRO("5","调拨审核"),
    
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
    private OrderCheckTypeEnum(String code, String message) {
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
    public static final OrderCheckTypeEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (OrderCheckTypeEnum item : values()) {
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
