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
 * Description: 账户流水 类型枚举
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年6月26日 下午5:32:09
 */
public enum AccountLogTypeEnum implements EnumBase {

	/** 健康值 */
	CONTRIBUTE("1", "健康值"),

	/** 胡币 */
	HU_COIN("2", "胡币"),

	/** 积分 */
	POINT("3", "积分"),

	/** 临时积分 */
	POINT_TEMP("4", "临时积分"),

	/** 临时胡币 */
	HU_TEMP("5", "临时胡币"),
	
	/** 胡币 */
    RE_POINT("6", "胡币"),

	;
	/** 枚举编号 */
	private String code;

	/** 枚举详情 */
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
	 * 构造方法
	 * 
	 * @param code
	 *            枚举编号
	 * @param message
	 *            枚举详情
	 */
	private AccountLogTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举。
	 * 
	 * @param code
	 *            枚举值
	 * @return 如果不存在返回NUll<br/>
	 *         如果存在返回相关值
	 */
	public static final AccountLogTypeEnum getByCode(String code) {

		// 如果为NUll返回 NUll
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
