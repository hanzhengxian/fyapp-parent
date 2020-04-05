/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * ������״̬ö�٣�Ŀǰ���   �˻��˿
 * 
 * @author yugang.ni
 * @version $Id: OrderSubStatusEnum.java, v 0.1 2018��12��20�� ����2:24:26 Administrator Exp $
 */
public enum OrderSubStatusEnum implements EnumBase {
    /** ��˳�ʼ���˿ */
    MONEY_RETURN_INIT("4.0","�˿������"),
    /** ���ͨ�����˿*/
    MONEY_RETURN_SUCC("4.1","�˿����ͨ��"),
    /** ��˾ܾ����˿*/
    MONEY_RETURN_FAIL("4.2","�˿����ʧ��"),
    /** ��˳�ʼ���˿��˻��� */
    GOOD_FIRST_RETURN_INIT("8.0","�˿��˻���"),
    /** �˿��˻��У��ʹ���ˣ� */
    GOOD_FIRST_RETURN_QUARTY_INIT("8.0.1","�˿��˻��У��ʹ���ˣ�"),
    /** ���ͨ�����˿��˻��� */
    GOOD_FIRST_RETURN_SUCC("8.1","�˿�����ͨ�����ȴ��ļ�"),
    /** ��˾ܾ����˿��˻��� */
    GOOD_FIRST_RETURN_FAIL("8.2","�˿�����ܾ�"),
    /** ��˳�ʼ���˿��˻� ������ */
    GOOD_SECOND_RETURN_INIT("8.3","�˻�������"),
    /** ���ͨ�����˿��˻� ������ */
    GOOD_SECOND_RETURN_SUCC("8.4","�˿��˻�����ͨ��"),
    /** ��˾ܾ����˿��˻� ������ */
    GOOD__SECOND_RETURN_FAIL("8.5","�˿��˻�����ʧ��"),
    /** ��ҵ�������  */
    CHECK_BANSHI_MA("13.1","��ҵ�������"),
    /** ������������� */
    CHECK_DAQU_MA("13.2","�������������"),
    /** �������ͨ��  */
    CASHIER_SUCC("13.3","�������ͨ��"),
    /** ������˾ܾ�  */
    CASHIER_FAIL("13.4","������˾ܾ�"),
    
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
    private OrderSubStatusEnum(String code, String message) {
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
    public static final OrderSubStatusEnum getByCode(String code) {

        // ���ΪNUll���� NUll
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
