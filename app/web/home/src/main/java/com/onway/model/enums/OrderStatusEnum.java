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
 * Description: ����״̬��ö��
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��7��3�� ����4:31:52
 */
public enum OrderStatusEnum implements EnumBase {

    /** ������ */
    WAIT_PAY("0", "������"),
    /** ������ */
    NOT_SEND("1", "������"),
    /** �ȴ����� */
    NOT_SEND_ING("1.1", "������"),
    /** ���ջ� */
    HAS_SEND("2", "���ջ�"),
    /** ���ջ��������ۣ� */
    HAS_RECEIVE("3", "���ջ��������ۣ�"),
    /** ���ջ���������ۣ� */
    SUCCESS_ORDER("6", "���ջ���������ۣ�"),
    /** �˿�ʧ���쳣 */
    ERR_RETURN("10", "�˿�ʧ���쳣"),
    /** ����ȡ�� */
    CANCLE("11", "����ȡ��"),
    /** ���ӵ� */
    WAIT_RECEIVE("12", "���ӵ�"),
    /** ����� */
    WAIT_CHECK("13", "�����"),
    /** ��˲�ͨ�� */
    FAIL_CHECK("14", "��˲�ͨ��"),
    /** �˿������ */
    APPLY_MONEY_RETURN("4", "�˿���"),
    /** �˿�ɹ� */
    SUCCESS_MONEY_RETURN("5", "�˿�ɹ�"),
    /** �˿�ʧ�� */
    FAIL_MONEY_RETURN("7", "�˿�ʧ��"),
    /** �˿��˻���  */
    APPLY_GOOD_RETURN("8", "�˿��˻���"),
    /** �˿��˻��ɹ� */
    SUCC_GOOD_RETURN("9", "�˿��˻��ɹ�"),
    /** �˿��˻�ʧ�� */
    FAIL_GOOD_RETURN("15", "�˿��˻�ʧ��"),
    
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
    private OrderStatusEnum(String code, String message) {
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
    public static final OrderStatusEnum getByCode(String code) {

        // ���ΪNUll���� NUll
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
