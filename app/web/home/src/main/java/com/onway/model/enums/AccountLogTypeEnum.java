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
 * Title: AccountLogTypeEnum
 * </p>
 * <p>
 * Description: �˻���ˮ ����ö��
 * </p>
 * 
 * @author yugang.ni
 * @date 2018��6��26�� ����5:32:09
 */
public enum AccountLogTypeEnum implements EnumBase {

	/** ����ֵ */
	CONTRIBUTE("1", "����ֵ"),

	/** ���� */
	HU_COIN("2", "����"),

	/** ���� */
	POINT("3", "����"),

	/** ��ʱ���� */
	POINT_TEMP("4", "��ʱ����"),

	/** ��ʱ���� */
	HU_TEMP("5", "��ʱ����"),
	
	/** ���� */
    RE_POINT("6", "����"),

	;
	/** ö�ٱ�� */
	private String code;

	/** ö������ */
	private String message;
	
	
    public static final List<EnumItem> list = new ArrayList<EnumItem>(2);
    
    static{
//        for (AccountLogTypeEnum param : values()) {
//            list.add(new EnumItem(param.getCode(), param.message()));
//        }
        list.add(new EnumItem(AccountLogTypeEnum.CONTRIBUTE.getCode(), AccountLogTypeEnum.CONTRIBUTE.message()));
        list.add(new EnumItem(AccountLogTypeEnum.POINT.getCode(), AccountLogTypeEnum.POINT.message()));
        list.add(new EnumItem(AccountLogTypeEnum.RE_POINT.getCode(), AccountLogTypeEnum.RE_POINT.message()));
    }


	/**
	 * ���췽��
	 * 
	 * @param code
	 *            ö�ٱ��
	 * @param message
	 *            ö������
	 */
	private AccountLogTypeEnum(String code, String message) {
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
	public static final AccountLogTypeEnum getByCode(String code) {

		// ���ΪNUll���� NUll
		if (StringUtils.isBlank(code)) {
			return null;
		}

		for (AccountLogTypeEnum item : values()) {
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
