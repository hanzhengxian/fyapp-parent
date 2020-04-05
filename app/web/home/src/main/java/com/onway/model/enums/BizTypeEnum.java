/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: BizTypeEnum</p>  
* <p>Description: ��������ö����</p>  
* @author yugang.ni  
* @date 2018��6��26��  ����5:16:13
 */
public enum BizTypeEnum implements EnumBase {
	/** ��Ʒ��� */
    PRODUCT_NO("PRODUCT_NO", "��Ʒ���", 1106),
    
    /** �û���� */
	USER_ID("USER_ID", "�û����", 1001),

	/** �Ŷӱ�� */
	TEAM_NO("TEAM_NO", "�Ŷӱ��", 2001),
	
	/** �������� */
	PLAN_TEAM_NO("PLAN_TEAM_NO", "��������", 3001),
	
	/** �Żݷ������ */
	DISCOUNT_NO("DISCOUNT_NO", "�Żݷ������", 3003),
	
	/** �Ƽ��˷���������� */
	RETURN_ID("RETURN_ID", "�Ƽ��˷����������", 3004),
	
	/** ������� */
	PLAN_NO("PLAN_NO", "�������", 3002),
	
	/** ����֪ʶ/���Ź����� */
	KNOW_ID("KNOW_ID", "����֪ʶ/���Ź���ID", 1006),
	
	/** ˳���µ���� */
	SF_ORDER_ID("SF_ORDER_ID", "˳���µ����", 3048),
	
	/** ˳���µ���� */
    SF_ORDER_ID_DEV("SF_ORDER_ID_DEV", "˳���µ����", 2050),
    
    REFUND_NO("REFUND_NO", "�˿���", 1300),
    
    ;

    private String code;

    private String message;

    private int    value;

    BizTypeEnum(String code, String message, int value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    public String code() {
        return code;
    }

    /**
     * ��ȡö����Ϣ
     *
     * @return
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * ��ȡö��ֵ
     *
     * @return
     */
    @Override
    public Number value() {
        return value;
    }
}
