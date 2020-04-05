/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.model.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * <p>
 * Title: UserLevelEnum
 * </p>
 * <p>
 * Description: 用户等级枚举
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月18日 上午10:28:53
 */
public enum UserLevelEnum implements EnumBase {

	/** 等级一 */
    LEVEL_ONE("1", "V1", 1),

    /** 等级二 */
    LEVEL_TWO("2", "V2", 2),

    /** 等级三 */
    LEVEL_THREE("3", "V3", 3),

    /** 等级四 */
    LEVEL_FOUR("4", "V4", 4),

    /** 等级五 */
    LEVEL_FIVE("5", "V5", 5),

	;
	private String code;

	private String message;

	private int value;
	
	public static final UserLevelEnum getByValue(Integer value) {
        //如果为NUll返回 NUll
        if (null == value) {
            return null;
        }
        for (UserLevelEnum item : values()) {
            if (item.value() == value) {
                return item;
            }
        }
        return null;
    }

	UserLevelEnum(String code, String message, int value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}

	public String code() {
		return code;
	}

	/**
	 * 获取枚举消息
	 *
	 * @return
	 */
	@Override
	public String message() {
		return message;
	}

	/**
	 * 获取枚举值
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
