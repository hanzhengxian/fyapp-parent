/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.model.enums;

/**
 * 编码生成类型枚举
 * 
 * @author guangdong.li
 * @version $Id: CodeGenerateTypeEnum.java, v 0.1 2015年8月5日 下午4:21:37
 *          guangdong.li Exp $
 */
public enum CodeGenerateTypeEnum {

	/** 用户编号 */
	USER_ID("USER_ID", "用户编号", 1001),

	/** 团队编号 */
	TEAM_NO("TEAM_NO", "团队编号", 2001),
	
	/** 方案组编号 */
	PLAN_TEAM_NO("PLAN_TEAM_NO", "方案组编号", 3001),
	
	/** 方案编号 */
	PLAN_NO("PLAN_NO", "方案编号", 3002),

	/** 媒体编号 */
	MEDIA_ID("MEDIA_ID", "主被叫订单编号", 1003),

	/** 媒体订单编号 */
	MEDIA_ORDER_NO("MEDIA_ORDER_NO", "媒体订单编号", 1004),

	/** 签到流水编号 */
	SIGN_ID("SIGN_ID", "签到流水编号", 1005),

	KNOW_ID("KNOW_ID", "养生知识/新闻公告ID", 1006),

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
	 * 获取枚举消息
	 *
	 * @return
	 */
	public String message() {
		return message;
	}

	/**
	 * 获取枚举值
	 *
	 * @return
	 */
	public int value() {
		return value;
	}
}
