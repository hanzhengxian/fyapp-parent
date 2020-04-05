/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * ��ݵ���ǰǩ��״̬
 * 0��;�С�1�����ա�2���ѡ�3��ǩ�ա�4��ǩ��5ͬ�������С�6�˻ء�7ת��
 * @author yugang.ni
 * @version $Id: Kuaidi100StateEnum.java, v 0.1 2019��3��8�� ����4:42:56 Administrator Exp $
 */
public enum Kuaidi100StateEnum implements EnumBase {

    /** ��;�� */
    STATE_ZERO("0", "��;��"),

    /** ������*/
    STATE_ONE("1", "������"),
    
    /** ���� */
    STATE_TWO("2", "����"),

    /** ��ǩ��*/
    STATE_THREE("3", "��ǩ��"),
    
    /** ��ǩ*/
    STATE_FOUR("4", "��ǩ"),
    
    /** ͬ��������*/
    STATE_FIVE("5", "ͬ��������"),
    
    /** �˻�*/
    STATE_SIX("6", "�˻�"),
    
    /** ת��*/
    STATE_SEVEN("7", "ת��"),


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
    private Kuaidi100StateEnum(String code, String message) {
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
    public static final Kuaidi100StateEnum getByCode(String code) {

        // ���ΪNUll���� NUll
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
