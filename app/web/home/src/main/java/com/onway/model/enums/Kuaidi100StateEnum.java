/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 快递单当前签收状态
 * 0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单
 * @author yugang.ni
 * @version $Id: Kuaidi100StateEnum.java, v 0.1 2019年3月8日 下午4:42:56 Administrator Exp $
 */
public enum Kuaidi100StateEnum implements EnumBase {

    /** 在途中 */
    STATE_ZERO("0", "在途中"),

    /** 已揽收*/
    STATE_ONE("1", "已揽收"),
    
    /** 疑难 */
    STATE_TWO("2", "疑难"),

    /** 已签收*/
    STATE_THREE("3", "已签收"),
    
    /** 退签*/
    STATE_FOUR("4", "退签"),
    
    /** 同城派送中*/
    STATE_FIVE("5", "同城派送中"),
    
    /** 退回*/
    STATE_SIX("6", "退回"),
    
    /** 转单*/
    STATE_SEVEN("7", "转单"),


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
    private Kuaidi100StateEnum(String code, String message) {
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
    public static final Kuaidi100StateEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (Kuaidi100StateEnum item : values()) {
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
