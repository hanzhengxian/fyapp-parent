/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import java.util.ArrayList;
import java.util.List;

import com.onway.common.lang.StringUtils;
import com.onway.model.EnumItem;
import com.onway.platform.common.enums.EnumBase;

/**
 * 年龄段枚举
 * 
 * @author yuhang.ni
 * @version $Id: AgeGroupEnum.java, v 0.1 2019年8月22日 下午2:44:37 Administrator Exp $
 */
public enum AgeGroupEnum implements EnumBase {

    /** 20以下 */
    A("20以下", "20以下"),

    /** 20-30 */
    B("20-30", "20-30"),

    /** 30-40 */
    C("30-40", "30-40"),

    /** 40-50 */
    D("40-50", "40-50"),

    /** 50-60 */
    E("50-60", "50-60"),

    /** 60以上 */
    F("60以上", "60以上"),

    ;
    /** 枚举编号 */
    private String                     code;

    /** 枚举详情 */
    private String                     message;

    public static final List<EnumItem> list = new ArrayList<EnumItem>(6);

    static {
        for (AgeGroupEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }

    /**
     * 构造方法
     * 
     * @param code
     *            枚举编号
     * @param message
     *            枚举详情
     */
    private AgeGroupEnum(String code, String message) {
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
    public static final AgeGroupEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (AgeGroupEnum item : values()) {
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
