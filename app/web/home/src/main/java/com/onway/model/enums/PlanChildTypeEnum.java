/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: PlanChildTypeEnum</p>  
* <p>Description: ����������ö��</p>  
* @author yugang.ni  
* @date 2018��6��26��  ����5:34:20
 */
public enum PlanChildTypeEnum implements EnumBase {
	
	/** ���������������ڣ�  */
	R0("0", "���������������ڣ�"),
	
	/** ����(����)�����������ڣ�*/
	R0_1("0.1", "����(����)�����������ڣ�"),

    /** �Ż�ȯ�����������ڣ�  */
	R1("1", "�Ż�ȯ�����������ڣ�"),

    /** �ۿۣ����������ڣ� */
    R2("2", "�ۿۣ����������ڣ�"),
    
    /** �������ڣ��Żݣ�  */
    R3_1("3.1", "�������ڣ��Żݣ�"),
    
    /** �������ڣ��ۿۣ�  */
    R3_2("3.2", "�������ڣ��ۿۣ�"),
    
    /** �������ڣ������� */
    R3_3("3.3", "�������ڣ�������"),
    
    /** �������ڣ�������(����)*/
	R3_4("3.4", "�������ڣ�������(����)"),
    
    /** �»�Ա���Żݣ� */
    R4_1("4.1", "�»�Ա���Żݣ�"),
    
    /** �»�Ա���ۿۣ�*/
    R4_2("4.2", "�»�Ա���ۿۣ�"),
    
    /** �»�Ա��������*/
    R4_3("4.3", "�»�Ա��������"),
    
    /** �»�Ա��������(����)*/
	R4_4("4.4", "�»�Ա��������(����)"),
    
    /** �ɻ�Ա���Żݣ�*/
    R5_1("5.1", "�ɻ�Ա���Żݣ�"),
    
    /** �ɻ�Ա���ۿۣ�*/
    R5_2("5.2", "�ɻ�Ա���ۿۣ�"),
    
    /** �ɻ�Ա��������*/
    R5_3("5.3", "�ɻ�Ա��������"),
    
    /** �ɻ�Ա��������(����)*/
	R5_4("5.4", "�ɻ�Ա��������(����)"),

    ;
    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private PlanChildTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final PlanChildTypeEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (PlanChildTypeEnum item : values()) {
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
