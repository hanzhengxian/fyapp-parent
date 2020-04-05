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
 * Title: PlanMainTypeEnum
 * </p>
 * <p>
 * Description: 方案主类型枚举
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年6月26日 下午5:34:20
 */
public enum PlanMainTypeEnum implements EnumBase {

	/** 优惠方案 */
	PLAN_REDUCE("1", "优惠方案"),

	/** 返利方案 */
	PLAN_RETURN("2", "返利方案"),

	;
	/** 枚举编号 */
	private String code;

	/** 枚举详情 */
	private String message;

	/**
	 * 构造方法
	 * 
	 * @param code
	 *            枚举编号
	 * @param message
	 *            枚举详情
	 */
	private PlanMainTypeEnum(String code, String message) {
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
	public static final PlanMainTypeEnum getByCode(String code) {

		// 如果为NUll返回 NUll
		if (StringUtils.isBlank(code)) {
			return null;
		}

		for (PlanMainTypeEnum item : values()) {
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
