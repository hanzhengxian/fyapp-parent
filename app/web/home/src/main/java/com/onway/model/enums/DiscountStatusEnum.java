/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * �Ż�ȯ״̬ö��
 * 
 * @author yugang.ni
 * @version $Id: DiscountStatusEnum.java, v 0.1 2018��10��9�� ����4:48:15 Administrator Exp $
 */
public enum DiscountStatusEnum implements EnumBase {

    /** ��ʹ��*/
    WAIT_USE("0", "��ʹ��"),

    /** ��ʹ�� */
    HAS_USED("1", "��ʹ��"),

    /** �ѹ��� */
    OUT_TIME("2", "�ѹ���"),

    ;
    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;

    /**
     * ���췽��
     * 
     * @param code
     *            ö�ٱ��
     * @param message
     *            ö������
     */
    private DiscountStatusEnum(String code, String message) {
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
    public static final DiscountStatusEnum getByCode(String code) {

        // ���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (DiscountStatusEnum item : values()) {
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
