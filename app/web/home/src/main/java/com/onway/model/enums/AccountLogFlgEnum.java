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
* <p>Title: AccountLogFlgEnum</p>  
* <p>Description: �˻���ˮ��ʶö��</p>  
* @author yugang.ni  
* @date 2018��6��26��  ����5:34:20
 */
public enum AccountLogFlgEnum implements EnumBase {

    /** ֧�� -*/
	COST("1", "֧��"),
	
	/** ֧���˻� +*/
    COST_RETURN("1.1", "֧���˻�"),

    /** ����  +*/
    REBATE("2", "����"),
    
    /** ��������  -*/
    REBATE_RETURN("2.1", "�������"),
    
    /** ����  +*/
    SHOPPING("3", "����"),
    
    /** ����  +*/
    RECEIVE("4", "����"),
    
    /** �˻�  +*/
    RETURN("5", "�˻�"),
    
    /** ����  -*/
    PUTFORWARD("6", "����"),
    
    /** �����˻�  +*/
    RETURNPUTFORWARD("7", "�����˻�"),
    
    /** �һ�  +*/
    EXCHANGE("8", "�һ�"),
    
    /** ��ȡ  +*/
    GETRECEIVE("9", "��ȡ"),
    
    /** ���ֹ���  -*/
    OUT_TIME("10", "���ֹ���"),
    
    /** �������  +*/
    SHARE("11", "�������"),
    
    /** ʧЧ  ����ֵ  -*/
    NOLONGER("12", "ʧЧ"),
    
    ;
    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;
    
    public static final List<EnumItem> list = new ArrayList<EnumItem>(13);
    
    static{
        for (AccountLogFlgEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }
    

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private AccountLogFlgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final AccountLogFlgEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (AccountLogFlgEnum item : values()) {
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
