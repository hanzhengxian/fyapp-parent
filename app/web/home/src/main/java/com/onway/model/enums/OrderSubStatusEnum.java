/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 订单子状态枚举（目前针对   退货退款）
 * 
 * @author yugang.ni
 * @version $Id: OrderSubStatusEnum.java, v 0.1 2018年12月20日 下午2:24:26 Administrator Exp $
 */
public enum OrderSubStatusEnum implements EnumBase {
    /** 审核初始（退款） */
    MONEY_RETURN_INIT("4.0","退款审核中"),
    /** 审核通过（退款）*/
    MONEY_RETURN_SUCC("4.1","退款审核通过"),
    /** 审核拒绝（退款）*/
    MONEY_RETURN_FAIL("4.2","退款审核失败"),
    /** 审核初始（退款退货） */
    GOOD_FIRST_RETURN_INIT("8.0","退款退货中"),
    /** 退款退货中（质管审核） */
    GOOD_FIRST_RETURN_QUARTY_INIT("8.0.1","退款退货中（质管审核）"),
    /** 审核通过（退款退货） */
    GOOD_FIRST_RETURN_SUCC("8.1","退款申请通过，等待寄件"),
    /** 审核拒绝（退款退货） */
    GOOD_FIRST_RETURN_FAIL("8.2","退款申请拒绝"),
    /** 审核初始（退款退货 物流） */
    GOOD_SECOND_RETURN_INIT("8.3","退货申请中"),
    /** 审核通过（退款退货 物流） */
    GOOD_SECOND_RETURN_SUCC("8.4","退款退货申请通过"),
    /** 审核拒绝（退款退货 物流） */
    GOOD__SECOND_RETURN_FAIL("8.5","退款退货申请失败"),
    /** 待业务经理审核  */
    CHECK_BANSHI_MA("13.1","待业务经理审核"),
    /** 待大区经理审核 */
    CHECK_DAQU_MA("13.2","待大区经理审核"),
    /** 收银审核通过  */
    CASHIER_SUCC("13.3","收银审核通过"),
    /** 收银审核拒绝  */
    CASHIER_FAIL("13.4","收银审核拒绝"),
    
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
    private OrderSubStatusEnum(String code, String message) {
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
    public static final OrderSubStatusEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (OrderSubStatusEnum item : values()) {
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
