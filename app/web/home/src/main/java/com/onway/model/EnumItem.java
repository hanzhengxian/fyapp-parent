package com.onway.model;

import com.onway.platform.common.base.ToString;

public class EnumItem extends ToString {
	/**  */
	private static final long serialVersionUID = 1L;

	/** 枚举编号 */
	private Object code;

	/** 枚举详情 */
	private String message;

	private String related;

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EnumItem(Object code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public EnumItem(Object code, String message, String related) {
		super();
		this.code = code;
		this.message = message;
		this.related = related;
	}

	public EnumItem() {
		super();
	}

}
