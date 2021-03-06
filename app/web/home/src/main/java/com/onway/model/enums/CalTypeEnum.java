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
 * 
 * 
 * @author yuhang.ni
 * @version $Id: CalTypeEnum.java, v 0.1 2019年4月9日 下午4:26:59 Administrator Exp $
 */
public enum CalTypeEnum implements EnumBase {

    /** 同比 （比同期）*/
	TB("TB", "同比"),
	
	/** 环比 （比上期） */
    HB("HB", "环比"),
    ;
    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;
    
    public static final List<EnumItem> list = new ArrayList<EnumItem>(13);
    
    static{
        for (CalTypeEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }
    

    /**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private CalTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final CalTypeEnum getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (CalTypeEnum item : values()) {
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
