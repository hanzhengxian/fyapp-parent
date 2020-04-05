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
 * <p>
 * Title: UserRoleEnum
 * </p>
 * <p>
 * Description: �û���˽�ɫö��     2ҵ��Ա  3����  4�ʹ�  5�ͷ�  
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��7��17�� ����4:48:54
 */
public enum UserCheckRoleEnum implements EnumBase {

    /** ҵ��Ա*/
    SALES_ROLE("2", "ҵ��Ա"),

    /** ����*/
    ACC_ROLE("3", "����"),

    /** �ʹ�*/
    SAFE_ROLE("4", "�ʹ�"),

    /** �ͷ�*/
    SERV_ROLE("5", "�ͷ�"),

    /** ҵ����*/
    BANSHI_MA("7", "ҵ����"),

    /** ��������*/
    DAQU_MA("8", "��������"),

    /** ����*/
    DU_DAO("9", "����"),

    /** ������*/
    CW_MA("10", "������"),

    /** ��������*/
    CW_NQ("11", "��������"),

    ;
    /** ö�ٱ�� */
    private String                     code;

    /** ö������ */
    private String                     message;
    

    public static final List<EnumItem> list = new ArrayList<EnumItem>(9);

    static {
        for (UserCheckRoleEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }

    /**
     * ������� 
     * 
     * @return
     */
    public static final List<String> cashierOrderCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(BANSHI_MA.code);
        list.add(DAQU_MA.code);
        return list;
    }

    /**
     * �쳣������ȡ����ҵ�����
     * 
     * @return
     */
    public static final List<String> errOrderCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        list.add(BANSHI_MA.code);
        list.add(DAQU_MA.code);
        list.add(DU_DAO.code);
        list.add(CW_MA.code);
        list.add(CW_NQ.code);
        return list;
    }

    /**
     * �������
     * 
     * @return
     */
    public static final List<String> allocateProCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        return list;
    }

    /**
     * ���࿨���    
     * 
     * @return
     */
    public static final List<String> voucherCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        list.add(ACC_ROLE.code);
        return list;
    }

    /**
     * ���췽��
     * 
     * @param code
     *            ö�ٱ��
     * @param message
     *            ö������
     */
    private UserCheckRoleEnum(String code, String message) {
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
    public static final UserCheckRoleEnum getByCode(String code) {

        // ���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (UserCheckRoleEnum item : values()) {
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
