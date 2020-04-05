/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * �Ŷӵȼ�
 * 
 * @author yuhang.ni
 * @version $Id: TeamLevelEnum.java, v 0.1 2019��8��9�� ����11:16:56 Administrator Exp $
 */
public enum TeamLevelEnum implements EnumBase {

	/** �ȼ�һ */
    LEVEL_ONE("1", "V1", 1),

    /** �ȼ��� */
    LEVEL_TWO("2", "V2", 2),

    /** �ȼ��� */
    LEVEL_THREE("3", "V3", 3),

    /** �ȼ��� */
    LEVEL_FOUR("4", "V4", 4),

    /** �ȼ��� */
    LEVEL_FIVE("5", "V5", 5),

	;
	private String code;

	private String message;

	private int value;
	
	public static final TeamLevelEnum getByValue(Integer value) {
        //���ΪNUll���� NUll
        if (null == value) {
            return null;
        }
        for (TeamLevelEnum item : values()) {
            if (item.value() == value) {
                return item;
            }
        }
        return null;
    }

	TeamLevelEnum(String code, String message, int value) {
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
	@Override
	public String message() {
		return message;
	}

	/**
	 * ��ȡö��ֵ
	 *
	 * @return
	 */
	@Override
	public Number value() {
		return value;
	}
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
