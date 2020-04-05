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
 * �����ö��
 * 
 * @author yuhang.ni
 * @version $Id: AgeGroupEnum.java, v 0.1 2019��8��22�� ����2:44:37 Administrator Exp $
 */
public enum AgeGroupEnum implements EnumBase {

    /** 20���� */
    A("20����", "20����"),

    /** 20-30 */
    B("20-30", "20-30"),

    /** 30-40 */
    C("30-40", "30-40"),

    /** 40-50 */
    D("40-50", "40-50"),

    /** 50-60 */
    E("50-60", "50-60"),

    /** 60���� */
    F("60����", "60����"),

    ;
    /** ö�ٱ�� */
    private String                     code;

    /** ö������ */
    private String                     message;

    public static final List<EnumItem> list = new ArrayList<EnumItem>(6);

    static {
        for (AgeGroupEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }

    /**
     * ���췽��
     * 
     * @param code
     *            ö�ٱ��
     * @param message
     *            ö������
     */
    private AgeGroupEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * 
     * @param code
     *            ö��ֵ
     * @return ��������ڷ���NUll<br/>
     *         ������ڷ������ֵ
     */
    public static final AgeGroupEnum getByCode(String code) {

        // ���ΪNUll���� NUll
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
