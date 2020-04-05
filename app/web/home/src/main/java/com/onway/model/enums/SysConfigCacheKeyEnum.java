package com.onway.model.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 系统参数key的枚举
 * 
 */
public enum SysConfigCacheKeyEnum implements EnumBase {

	// -----------------------支付相关
	MENU_SERVERURL("MENU_SERVERURL", "微信菜单重定向IP地址"),

	WE_PAY_PARTNER_ID("WE_PAY_PARTNER_ID", "微信公众号支付Partner ID"),

	WE_PAY_APP_SECRET_ID("WE_PAY_APP_SECRET_ID", "微信公众号支付APP Secret"),

	WE_PAY_MCH_ID("WE_PAY_MCH_ID", "微信支付MCH_ID"),

	WE_APP_PAY_APP_ID("WE_APP_PAY_APP_ID", "微信APP支付appid"),

	WE_APP_PAY_PAY_MCH_ID("WE_APP_PAY_PAY_MCH_ID", "微信APP支付MCH_ID"),

	WE_APP_PAY_APP_SECRET_ID("WE_APP_PAY_APP_SECRET_ID", "微信APP支付APP Secret"),

	WECHAT_PAY_CALL_BACK_URL("WECHAT_PAY_CALL_BACK_URL", "微信支付回调地址"),

	WECHAT_PAY_CALL_AUTH_URL("WECHAT_PAY_CALL_AUTH_URL", "微信支付授权地址"),
	
	ACCESS_TOKEN("ACCESS_TOKEN", "ACCESS_TOKEN标识"),
	
	WECHAT_INDEX_URL("WECHAT_INDEX_URL","首页链接地址"),
	
	PKCS_CER_URL("PKCS_CER_URL","证书地址"),
	
	/**【等级】普通用户等级评定周期*/
	LEVEL_USER_DEVOTE_TIME("LEVEL_USER_DEVOTE_TIME","【等级】普通用户等级评定周期"),
	/**【等级】门店等级评定周期*/
	LEVEL_SHOP_DEVOTE_TIME("LEVEL_SHOP_DEVOTE_TIME","【等级】门店等级评定周期"),
	/**【等级】代理商等级评定周期*/
	LEVEL_AGENT_DEVOTE_TIME("LEVEL_AGENT_DEVOTE_TIME","【等级】代理商等级评定周期"),
	/**【等级】服务商等级评定周期*/
	LEVEL_SERVICE_DEVOTE_TIME("LEVEL_SERVICE_DEVOTE_TIME","【等级】服务商等级评定周期"),
	
	/** 【提现】胡币兑换现金比例（1胡币 对应 value 现金）*/
	HU_WITHDRAW_RATE("HU_WITHDRAW_RATE","【提现】胡币兑换现金比例（1胡币 对应 value 现金）"),
	
	/** 【自动收货】订单发货后自动确认收货天数  */
	AUTO_RECEIVE_ORDER_DAY("AUTO_RECEIVE_ORDER_DAY","【自动收货】订单发货后自动确认收货天数"),
	
    /** 【链接】订单详情链接地址 */
    USER_ORDER_DETAILS_URL("USER_ORDER_DETAILS_URL","【链接】订单详情链接地址"),
    
    /** 【链接】订单详情链接API*/
    USER_ORDER_DETAILS_API("USER_ORDER_DETAILS_API","【链接】订单详情链接API"),
    
    /** 【购买人返利】返利类型（0不返利  1按积分值返利  2按比例返利）*/
    COMMEN_RETURN_TYPE("COMMEN_RETURN_TYPE","【购买人返利】返利类型（0不返利  1按积分值返利  2按比例返利）"),
    /** 【购买人返利】积分比例值（0为不返）*/
    COMMEN_RETURN_RATE("COMMEN_RETURN_RATE","【购买人返利】积分比例值（0为不返）"),
    /** 【购买人返利】积分值（0为不返）*/
    COMMEN_RETURN_VALUE("COMMEN_RETURN_VALUE","【购买人返利】积分值（0为不返）"),
    
    /** 已付款，已核销（X日内可申请）*/
    CAN_APPLY_DAY("CAN_APPLY_DAY","已付款，已核销（X日内可申请）"),
    
    
    /* 顺风  */
    /** 丰桥平台接口地址*/
    SF_REQ_URL("SF_REQ_URL", "丰桥平台接口地址"),
    
    /** 丰桥平台获取的顾客编码*/
    SF_CLIENT_CODE("SF_CLIENT_CODE", "丰桥平台获取的顾客编码"),
    
    /** 丰桥平台获取的校验码*/
    SF_CHECK_CODE("SF_CHECK_CODE", "丰桥平台获取的校验码"),
    
    /** 顺丰月结卡号*/
    SF_CUSTID("SF_CUSTID", "顺丰月结卡号"),
    
    /** 寄件省份*/
    J_PROVINCE("J_PROVINCE", "寄件省份"),
    
    /** 寄件城市*/
    J_CITY("J_CITY", "寄件城市"),
    
    /** 寄件区*/
    J_COUNTY("J_COUNTY", "寄件区"),
    
    /** 寄件公司名称*/
    J_COMPANY("J_COMPANY", "寄件公司名称"),
    
    /** 寄件联系人*/
    J_CONTACT("J_CONTACT", "寄件联系人"),
    
    /** 寄件电话*/
    J_TEL("J_TEL", "寄件电话"),
    
    /** 寄件手机*/
    J_MOBILE("J_MOBILE", "寄件手机"),
    
    /** 寄件地址*/
    J_ADDRESS("J_ADDRESS", "寄件地址"),
    
    /** 顺风公司编码*/
    SHUNFENG_CODE("SHUNFENG_CODE", "顺风公司编码"),
    
    /** 本地保存路径*/
    SAVE_PATH("SAVE_PATH", "本地保存路径"),
    
    /** 【比率】支付现金兑换健康值比率（1现金 对应 value 健康值） */
    DEVOTE_MONEY_RATE("DEVOTE_MONEY_RATE", "【比率】支付现金兑换健康值比率（1现金 对应 value 健康值）"),
	
    /** 经营类别（务必用中文分号；隔开）*/
    BUSINESS_CATEGORY("BUSINESS_CATEGORY","经营类别（务必用中文分号；隔开）"),
    
    
    KUAI_DI_CUSTOMER("KUAI_DI_CUSTOMER","【快递】快递100公司编号"),
    
    KUAI_DI_KEY("KUAI_DI_KEY","【快递】快递100查询key"),
    
    UNIFY_CUSTOMER_CELL("UNIFY_CUSTOMER_CELL","统一非会员手机号"),
    
    
    SF_YWLX("SF_YWLX", "【顺丰】业务类型"),
    
    SF_FKFS("SF_FKFS", "【顺丰】付款方式"),
    
    
    // -----------------------支付相关===支付宝

    ALIPAY_PARTNER("ALIPAY_PARTNER", "支付宝支付partner"),

    ALIPAY_SELLER("ALIPAY_SELLER", "支付宝支付seller 商户号"),

    ALIPAY_PRIVATE_KEY("ALIPAY_PRIVATE_KEY", "支付宝提现私钥"),

    ALIPAY_APPID("ALIPAY_APPID", "支付宝支付appId"),

    ALIPAY_PUBLIC_KEY("ALIPAY_PUBLIC_KEY", "支付宝公钥(提现)"),

    ALI_SECURITY_PUBLIC_KEY("ALI_SECURITY_PUBLIC_KEY", "支付宝公钥(回调校验)"),

    ALIPAY_CALLBACK_URL("ALIPAY_CALLBACK_URL", "支付宝支付回调地址"),

    ALIPAY_URL("FREEBUY_ALIPAY_URL", "支付宝网关（固定）"),

    ALIPAY_PAY_PRIVATE_KEY("ALIPAY_PAY_PRIVATE_KEY", "支付宝支付私钥"),

    ALIPAY_PAGE_RETURN_URL("ALIPAY_PAGE_RETURN_URL", "支付宝支付页面同步回调地址"),

    ALIPAY_PAGE_NOTIFY_URL("ALIPAY_PAGE_NOTIFY_URL", "支付宝支付页面异步通知地址"),
    
    WECHAT_REFUND_NOTIFY_URL("WECHAT_REFUND_NOTIFY_URL", "微信退款结果通知url"),
    
    
	;
	/** 枚举码 */
	private String code;

	/** 描述信息 */
	private String message;

	private SysConfigCacheKeyEnum(String code, String message) {
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
	 * 通过枚举<code>code</code>获得枚举。
	 * 
	 * @param code
	 *            枚举编号
	 * @return
	 */
	public static SysConfigCacheKeyEnum getByCode(String code) {

		for (SysConfigCacheKeyEnum param : values()) {
			if (StringUtils.equals(param.getCode(), code)) {
				return param;
			}
		}

		return null;
	}
}
