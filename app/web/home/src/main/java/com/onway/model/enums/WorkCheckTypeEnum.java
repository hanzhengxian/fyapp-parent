package com.onway.model.enums;

import java.util.ArrayList;
import java.util.List;

import com.onway.common.lang.StringUtils;
import com.onway.model.EnumItem;
import com.onway.platform.common.enums.EnumBase;

/**
 * ��������   
 * 0 �ϰ� 1�°�
 * @author yuhang.ni
 * @version $Id: WorkCheckTypeEnum.java, v 0.1 2019��7��3�� ����5:22:19 Administrator Exp $
 */
public enum WorkCheckTypeEnum implements EnumBase{
	
	/** �ϰ�  */
	ON_WORK("0", "�ϰ�"),

	/** �°�  */
	OFF_WORK("1", "�°�"),
	;

    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;
    
    public static final List<EnumItem> list = new ArrayList<EnumItem>(2);
    
    static{
        for (WorkCheckTypeEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private WorkCheckTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final WorkCheckTypeEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (WorkCheckTypeEnum item : values()) {
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
