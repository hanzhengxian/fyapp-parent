package com.onway.core.service.localcache.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
 * @author guangdong.li
 *
 */
public enum LocalCacheNameEnum implements EnumBase {

	/** ÏµÍ³²ÎÊýÅäÖÃ */
	SYS_CONFIG("SYS_CONFIG", "ÏµÍ³²ÎÊýÅäÖÃ"),

	;

	private String code;

	private String message;

	private LocalCacheNameEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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
	 * @see com.onway.platform.common.enums.EnumBase#value()
	 */
	@Override
	public Number value() {
		return null;
	}

	/**
	 * Í¨ï¿½ï¿½Ã¶ï¿½ï¿½<code>code</code>ï¿½ï¿½ï¿½Ã¶ï¿½Ù¡ï¿? *
	 * 
	 * @param code
	 *            Ã¶ï¿½Ù±ï¿½ï¿? * @return
	 */
	public static LocalCacheNameEnum getLocalCacheNameEnumByCode(String code) {
		for (LocalCacheNameEnum param : values()) {
			if (StringUtils.equals(param.getCode(), code)) {
				return param;
			}
		}
		return null;
	}
}
