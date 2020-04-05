package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * @author lishuaikai
 * @Description ����״̬ö��
 * @data 2018��7��2�� ����11:18:28
 */
public enum CartStatusEnum implements EnumBase{
	/** �½�  */
	INIT("INIT", "�½�"),

    /** �ѽ��� */
	SETTLED("SETTLED", "�ѽ���"),
    
    /** ��ɾ��*/
	DELETED("DELETED", "��ɾ��"),
	;

    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;
    
    
	private CartStatusEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	   /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final CartStatusEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (CartStatusEnum item : values()) {
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
