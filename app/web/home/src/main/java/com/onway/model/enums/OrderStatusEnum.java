/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import java.util.ArrayList;
import java.util.List;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * <p>
 * Title: OrderStatusEnum
 * </p>
 * <p>
 * Description: 订单状态类枚举
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月3日 下午4:31:52
 */
public enum OrderStatusEnum implements EnumBase {

    /** 待付款 */
    WAIT_PAY("0", "待付款"),
    /** 待发货 */
    NOT_SEND("1", "待发货"),
    /** 等待发货 */
    NOT_SEND_ING("1.1", "待发货"),
    /** 待收货 */
    HAS_SEND("2", "待收货"),
    /** 已收货（待评价） */
    HAS_RECEIVE("3", "已收货（待评价）"),
    /** 已收货（完成评价） */
    SUCCESS_ORDER("6", "已收货（完成评价）"),
    /** 退款失败异常 */
    ERR_RETURN("10", "退款失败异常"),
    /** 订单取消 */
    CANCLE("11", "订单取消"),
    /** 待接单 */
    WAIT_RECEIVE("12", "待接单"),
    /** 待审核 */
    WAIT_CHECK("13", "待审核"),
    /** 审核不通过 */
    FAIL_CHECK("14", "审核不通过"),
    /** 退款审核中 */
    APPLY_MONEY_RETURN("4", "退款中"),
    /** 退款成功 */
    SUCCESS_MONEY_RETURN("5", "退款成功"),
    /** 退款失败 */
    FAIL_MONEY_RETURN("7", "退款失败"),
    /** 退款退货中  */
    APPLY_GOOD_RETURN("8", "退款退货中"),
    /** 退款退货成功 */
    SUCC_GOOD_RETURN("9", "退款退货成功"),
    /** 退款退货失败 */
    FAIL_GOOD_RETURN("15", "退款退货失败"),
    
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
    private OrderStatusEnum(String code, String message) {
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
    public static final OrderStatusEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (OrderStatusEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }
    
    public static final List<String> getSuccCode() {
        
        List<String> orderStatusList = new ArrayList<String>();
        orderStatusList.add(NOT_SEND.getCode());
        orderStatusList.add(NOT_SEND_ING.getCode());
        orderStatusList.add(HAS_SEND.getCode());
        orderStatusList.add(HAS_RECEIVE.getCode());
        orderStatusList.add(SUCCESS_ORDER.getCode());
        return orderStatusList;
    }
    
    public static final List<String> getSuccCodeS() {
        
        List<String> orderStatusList = new ArrayList<String>();
        orderStatusList.add(NOT_SEND.getCode());
        orderStatusList.add(NOT_SEND_ING.getCode());
        orderStatusList.add(HAS_SEND.getCode());
        orderStatusList.add(HAS_RECEIVE.getCode());
        orderStatusList.add(SUCCESS_ORDER.getCode());
        orderStatusList.add(ERR_RETURN.getCode());
        orderStatusList.add(APPLY_MONEY_RETURN.getCode());
        orderStatusList.add(SUCCESS_MONEY_RETURN.getCode());
        orderStatusList.add(FAIL_MONEY_RETURN.getCode());
        orderStatusList.add(APPLY_GOOD_RETURN.getCode());
        orderStatusList.add(SUCC_GOOD_RETURN.getCode());
        orderStatusList.add(FAIL_GOOD_RETURN.getCode());
        return orderStatusList;
    }
    
    public static final List<String> getSuccCodeNotReturn() {
        
        List<String> orderStatusList = new ArrayList<String>();
        orderStatusList.add(NOT_SEND.getCode());
        orderStatusList.add(NOT_SEND_ING.getCode());
        orderStatusList.add(HAS_SEND.getCode());
        orderStatusList.add(HAS_RECEIVE.getCode());
        orderStatusList.add(SUCCESS_ORDER.getCode());
        orderStatusList.add(ERR_RETURN.getCode());
        orderStatusList.add(APPLY_MONEY_RETURN.getCode());
        orderStatusList.add(FAIL_MONEY_RETURN.getCode());
        orderStatusList.add(APPLY_GOOD_RETURN.getCode());
        orderStatusList.add(FAIL_GOOD_RETURN.getCode());
        return orderStatusList;
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
