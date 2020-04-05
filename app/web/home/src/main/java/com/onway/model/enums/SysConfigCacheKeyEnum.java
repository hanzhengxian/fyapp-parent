package com.onway.model.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * ϵͳ����key��ö��
 * 
 */
public enum SysConfigCacheKeyEnum implements EnumBase {

	// -----------------------֧�����
	MENU_SERVERURL("MENU_SERVERURL", "΢�Ų˵��ض���IP��ַ"),

	WE_PAY_PARTNER_ID("WE_PAY_PARTNER_ID", "΢�Ź��ں�֧��Partner ID"),

	WE_PAY_APP_SECRET_ID("WE_PAY_APP_SECRET_ID", "΢�Ź��ں�֧��APP Secret"),

	WE_PAY_MCH_ID("WE_PAY_MCH_ID", "΢��֧��MCH_ID"),

	WE_APP_PAY_APP_ID("WE_APP_PAY_APP_ID", "΢��APP֧��appid"),

	WE_APP_PAY_PAY_MCH_ID("WE_APP_PAY_PAY_MCH_ID", "΢��APP֧��MCH_ID"),

	WE_APP_PAY_APP_SECRET_ID("WE_APP_PAY_APP_SECRET_ID", "΢��APP֧��APP Secret"),

	WECHAT_PAY_CALL_BACK_URL("WECHAT_PAY_CALL_BACK_URL", "΢��֧���ص���ַ"),

	WECHAT_PAY_CALL_AUTH_URL("WECHAT_PAY_CALL_AUTH_URL", "΢��֧����Ȩ��ַ"),
	
	ACCESS_TOKEN("ACCESS_TOKEN", "ACCESS_TOKEN��ʶ"),
	
	WECHAT_INDEX_URL("WECHAT_INDEX_URL","��ҳ���ӵ�ַ"),
	
	PKCS_CER_URL("PKCS_CER_URL","֤���ַ"),
	
	/**���ȼ�����ͨ�û��ȼ���������*/
	LEVEL_USER_DEVOTE_TIME("LEVEL_USER_DEVOTE_TIME","���ȼ�����ͨ�û��ȼ���������"),
	/**���ȼ����ŵ�ȼ���������*/
	LEVEL_SHOP_DEVOTE_TIME("LEVEL_SHOP_DEVOTE_TIME","���ȼ����ŵ�ȼ���������"),
	/**���ȼ��������̵ȼ���������*/
	LEVEL_AGENT_DEVOTE_TIME("LEVEL_AGENT_DEVOTE_TIME","���ȼ��������̵ȼ���������"),
	/**���ȼ��������̵ȼ���������*/
	LEVEL_SERVICE_DEVOTE_TIME("LEVEL_SERVICE_DEVOTE_TIME","���ȼ��������̵ȼ���������"),
	
	/** �����֡����Ҷһ��ֽ������1���� ��Ӧ value �ֽ�*/
	HU_WITHDRAW_RATE("HU_WITHDRAW_RATE","�����֡����Ҷһ��ֽ������1���� ��Ӧ value �ֽ�"),
	
	/** ���Զ��ջ��������������Զ�ȷ���ջ�����  */
	AUTO_RECEIVE_ORDER_DAY("AUTO_RECEIVE_ORDER_DAY","���Զ��ջ��������������Զ�ȷ���ջ�����"),
	
    /** �����ӡ������������ӵ�ַ */
    USER_ORDER_DETAILS_URL("USER_ORDER_DETAILS_URL","�����ӡ������������ӵ�ַ"),
    
    /** �����ӡ�������������API*/
    USER_ORDER_DETAILS_API("USER_ORDER_DETAILS_API","�����ӡ�������������API"),
    
    /** �������˷������������ͣ�0������  1������ֵ����  2������������*/
    COMMEN_RETURN_TYPE("COMMEN_RETURN_TYPE","�������˷������������ͣ�0������  1������ֵ����  2������������"),
    /** �������˷��������ֱ���ֵ��0Ϊ������*/
    COMMEN_RETURN_RATE("COMMEN_RETURN_RATE","�������˷��������ֱ���ֵ��0Ϊ������"),
    /** �������˷���������ֵ��0Ϊ������*/
    COMMEN_RETURN_VALUE("COMMEN_RETURN_VALUE","�������˷���������ֵ��0Ϊ������"),
    
    /** �Ѹ���Ѻ�����X���ڿ����룩*/
    CAN_APPLY_DAY("CAN_APPLY_DAY","�Ѹ���Ѻ�����X���ڿ����룩"),
    
    
    /* ˳��  */
    /** ����ƽ̨�ӿڵ�ַ*/
    SF_REQ_URL("SF_REQ_URL", "����ƽ̨�ӿڵ�ַ"),
    
    /** ����ƽ̨��ȡ�Ĺ˿ͱ���*/
    SF_CLIENT_CODE("SF_CLIENT_CODE", "����ƽ̨��ȡ�Ĺ˿ͱ���"),
    
    /** ����ƽ̨��ȡ��У����*/
    SF_CHECK_CODE("SF_CHECK_CODE", "����ƽ̨��ȡ��У����"),
    
    /** ˳���½Ῠ��*/
    SF_CUSTID("SF_CUSTID", "˳���½Ῠ��"),
    
    /** �ļ�ʡ��*/
    J_PROVINCE("J_PROVINCE", "�ļ�ʡ��"),
    
    /** �ļ�����*/
    J_CITY("J_CITY", "�ļ�����"),
    
    /** �ļ���*/
    J_COUNTY("J_COUNTY", "�ļ���"),
    
    /** �ļ���˾����*/
    J_COMPANY("J_COMPANY", "�ļ���˾����"),
    
    /** �ļ���ϵ��*/
    J_CONTACT("J_CONTACT", "�ļ���ϵ��"),
    
    /** �ļ��绰*/
    J_TEL("J_TEL", "�ļ��绰"),
    
    /** �ļ��ֻ�*/
    J_MOBILE("J_MOBILE", "�ļ��ֻ�"),
    
    /** �ļ���ַ*/
    J_ADDRESS("J_ADDRESS", "�ļ���ַ"),
    
    /** ˳�繫˾����*/
    SHUNFENG_CODE("SHUNFENG_CODE", "˳�繫˾����"),
    
    /** ���ر���·��*/
    SAVE_PATH("SAVE_PATH", "���ر���·��"),
    
    /** �����ʡ�֧���ֽ�һ�����ֵ���ʣ�1�ֽ� ��Ӧ value ����ֵ�� */
    DEVOTE_MONEY_RATE("DEVOTE_MONEY_RATE", "�����ʡ�֧���ֽ�һ�����ֵ���ʣ�1�ֽ� ��Ӧ value ����ֵ��"),
	
    /** ��Ӫ�����������ķֺţ�������*/
    BUSINESS_CATEGORY("BUSINESS_CATEGORY","��Ӫ�����������ķֺţ�������"),
    
    
    KUAI_DI_CUSTOMER("KUAI_DI_CUSTOMER","����ݡ����100��˾���"),
    
    KUAI_DI_KEY("KUAI_DI_KEY","����ݡ����100��ѯkey"),
    
    UNIFY_CUSTOMER_CELL("UNIFY_CUSTOMER_CELL","ͳһ�ǻ�Ա�ֻ���"),
    
    
    SF_YWLX("SF_YWLX", "��˳�᡿ҵ������"),
    
    SF_FKFS("SF_FKFS", "��˳�᡿���ʽ"),
    
    
    // -----------------------֧�����===֧����

    ALIPAY_PARTNER("ALIPAY_PARTNER", "֧����֧��partner"),

    ALIPAY_SELLER("ALIPAY_SELLER", "֧����֧��seller �̻���"),

    ALIPAY_PRIVATE_KEY("ALIPAY_PRIVATE_KEY", "֧��������˽Կ"),

    ALIPAY_APPID("ALIPAY_APPID", "֧����֧��appId"),

    ALIPAY_PUBLIC_KEY("ALIPAY_PUBLIC_KEY", "֧������Կ(����)"),

    ALI_SECURITY_PUBLIC_KEY("ALI_SECURITY_PUBLIC_KEY", "֧������Կ(�ص�У��)"),

    ALIPAY_CALLBACK_URL("ALIPAY_CALLBACK_URL", "֧����֧���ص���ַ"),

    ALIPAY_URL("FREEBUY_ALIPAY_URL", "֧�������أ��̶���"),

    ALIPAY_PAY_PRIVATE_KEY("ALIPAY_PAY_PRIVATE_KEY", "֧����֧��˽Կ"),

    ALIPAY_PAGE_RETURN_URL("ALIPAY_PAGE_RETURN_URL", "֧����֧��ҳ��ͬ���ص���ַ"),

    ALIPAY_PAGE_NOTIFY_URL("ALIPAY_PAGE_NOTIFY_URL", "֧����֧��ҳ���첽֪ͨ��ַ"),
    
    WECHAT_REFUND_NOTIFY_URL("WECHAT_REFUND_NOTIFY_URL", "΢���˿���֪ͨurl"),
    
    
	;
	/** ö���� */
	private String code;

	/** ������Ϣ */
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
	 * ͨ��ö��<code>code</code>���ö�١�
	 * 
	 * @param code
	 *            ö�ٱ��
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
