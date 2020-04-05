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
 * Title: OrderPayWayEnum
 * </p>
 * <p>
 * Description: ����֧������ö��
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��7��13�� ����10:30:28
 */
public enum OrderPayWayEnum implements EnumBase {

    /** ΢�� */
    WEIXIN("1", "΢��֧��"),

    /** ֧���� */
    ALIPAY("2", "֧����֧��"),

    /** ����֧�� */
    OFF_LINE("3", "����֧��"),

    /** ����֧�� */
    HU("4", "����"),

    OTHER("5", "����")
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
	private OrderPayWayEnum(String code, String message) {
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
	public static final OrderPayWayEnum getByCode(String code) {

		// ���ΪNUll���� NUll
		if (StringUtils.isBlank(code)) {
			return null;
		}

		for (OrderPayWayEnum item : values()) {
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
