/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: BannerPositionEnum</p>  
* <p>Description: banner λ��ö��</p>  
* @author yugang.ni  
* @date 2018��6��27��  ����4:45:24
 */
public enum BannerPositionEnum implements EnumBase {

    /** ΢����ҳ */
    WECHAT_HOME("WECHAT_HOME", "΢����ҳ", 0),

    /** ΢����Ʒҳ */
    WECHAT_PRODUCT("WECHAT_PRODUCT", "΢����Ʒҳ", 1),
    
    /** ΢����ҳ��ͼ */
    WECHAT_INDEX("WECHAT_INDEX", "΢����ҳ��ͼ", 4),

    /** ΢����ҳ���౳��ͼ */
    WECHAT_INDEX_BACK("WECHAT_INDEX_BACK", "΢����ҳ���౳��ͼ", 5), 
    
    /** WEB��ҳ */
    WEB_HOME("WEB_HOME", "WEB��ҳ", 2),

    /** WEB�̳�ҳ */
    WEB_SHOP("WEB_SHOP", "WEB�̳�ҳ", 3),
    
    /** WEB�̳�ҳ ��� */
    WEB_SHOP_LEFT("WEB_SHOP_LEFT", "WEB�̳�ҳ ���", 6),
    
    /** WEB�̳�ҳ ��� */
    WEB_SHOP_RIGHT("WEB_SHOP_RIGHT", "WEB�̳�ҳ ���", 7),
    
    /** WEB����֪ʶ */
    WEB_KNOWLEDGE("WEB_KNOWLEDGE","WEB����֪ʶ", 8),
    
    /** WEB���Ź��� */
    WEB_NEWS("WEB_NEWS","WEB���Ź���", 9),

    /** WEB�����ŵ� */
    WEB_SHOPS("WEB_SHOPS","WEB�����ŵ�", 10),
    
    /** WEB�������� */
    WEB_US("WEB_US","WEB��������", 11),
    ;
    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;
    
    /** ö������ */
    private Number value;

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private BannerPositionEnum(String code, String message, Number value) {
        this.code = code;
        this.message = message;
        this.value = value;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final BannerPositionEnum getByCode(String code) {

        //���ΪNUll���� NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (BannerPositionEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }
    
    /**
     * ͨ��ö��<code>value</code>���ö�١�
     * @param value ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final BannerPositionEnum getByValue(Number value) {
        for (BannerPositionEnum item : values()) {
            if (item.getValue() == value) {
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

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

}
