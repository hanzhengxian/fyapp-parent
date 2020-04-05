/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * <p>
 * Title: UserRoleEnum
 * </p>
 * <p>
 * Description: �û���ɫö��   0ӪҵԱ  1�쵼  2ҵ��Ա  3����  4�ʹ�  5�ͷ�  6��ͨ�û�
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��7��17�� ����4:48:54
 */
public enum UserRoleEnum implements EnumBase {
    
    /** ӪҵԱ*/
    ASS_ROLE("0","ӪҵԱ"),
    
    /** �쵼*/
    LEADER_ROLE("1","�쵼"),
    
    /** ҵ��Ա*/
    SALES_ROLE("2","ҵ��Ա"),
    
    /** ����*/
    ACC_ROLE("3","����"),
    
    /** �ʹ�*/
    SAFE_ROLE("4","�ʹ�"),
    
    /** �ͷ�*/
    SERV_ROLE("5","�ͷ�"),
    
    /** ��ͨ�û� */
    CUSTOMER("6", "��ͨ�û�"),
    
    /** ҵ����*/
    BANSHI_MA("7","ҵ����"),
    
    /** ��������*/
    DAQU_MA("8","��������"),
    
    /** ����*/
    DU_DAO("9","����"),
    
    /** ������*/
    CW_MA("10","������"),
    
    /** ��������*/
    CW_NQ("11","��������"),
    
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
    private UserRoleEnum(String code, String message) {
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
    public static final UserRoleEnum getByCode(String code) {

        // ���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (UserRoleEnum item : values()) {
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
