/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.model.enums;

/**
 * ������������ö��
 * 
 * @author guangdong.li
 * @version $Id: CodeGenerateTypeEnum.java, v 0.1 2015��8��5�� ����4:21:37
 *          guangdong.li Exp $
 */
public enum CodeGenerateTypeEnum {

	/** �û���� */
	USER_ID("USER_ID", "�û����", 1001),

	/** �Ŷӱ�� */
	TEAM_NO("TEAM_NO", "�Ŷӱ��", 2001),
	
	/** �������� */
	PLAN_TEAM_NO("PLAN_TEAM_NO", "��������", 3001),
	
	/** ������� */
	PLAN_NO("PLAN_NO", "�������", 3002),

	/** ý���� */
	MEDIA_ID("MEDIA_ID", "�����ж������", 1003),

	/** ý�嶩����� */
	MEDIA_ORDER_NO("MEDIA_ORDER_NO", "ý�嶩�����", 1004),

	/** ǩ����ˮ��� */
	SIGN_ID("SIGN_ID", "ǩ����ˮ���", 1005),

	KNOW_ID("KNOW_ID", "����֪ʶ/���Ź���ID", 1006),

	;

	private String code;

	private String message;

	private int value;

	CodeGenerateTypeEnum(String code, String message, int value) {
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
	public String message() {
		return message;
	}

	/**
	 * ��ȡö��ֵ
	 *
	 * @return
	 */
	public int value() {
		return value;
	}
}
