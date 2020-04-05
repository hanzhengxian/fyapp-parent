package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: SysMsgEnum</p>  
* <p>Description: ϵͳ������Ϣ  ö��</p>  
* @author yugang.ni  
* @date 2018��7��4��  ����11:53:57
 */
public enum SysMsgEnum implements EnumBase {
	/**�쳣*/
    ERROR("ERROR","�쳣"),
    /**�����ɹ�*/
    SUCCESS("SUCCESS","�����ɹ�"),
    /**����ʧ��*/
    FAIL("FAIL","����ʧ��"),
	/**�˻�������*/
    INEXISTENCE("INEXISTENCE","���˻�������"),
    /**�˺Ż��������*/
    ERROR_LOGIN("ERROR_LOGIN","�˺Ż��������"),
    /**���˻���ע��*/
    REGIST_AGAIN("REGIST_AGAIN","���˻���ע��"),
    /**��½�ɹ�*/
    LOGIN_SUCCESS("LOGIN_SUCCESS","��½�ɹ�"),
    /**��½ʧ��*/
    LOGIN_FAIL("LOGIN_FAIL","��½ʧ��"),
    /**��½�ɹ�*/
    REGIST_SUCCESS("REGIST_SUCCESS","ע��ɹ�"),
    /**��½ʧ��*/
    REGIST_FAIL("REGIST_FAIL","ע��ʧ��"),
    /**������֤��ɹ�*/
    SEND_VERCODE_SUCCESS("SEND_VERCODE_SUCCESS","������֤��ɹ�"),
    /**������֤��ʧ��*/
    SEND_VERCODE_FAIL("SEND_VERCODE_FAIL","������֤��ʧ��"),
    /**�Ƿ�����*/
    INVALID_REQUEST("INVALID_REQUEST","�Ƿ�����"),
    /**�ֻ��Ÿ�ʽ����ȷ*/
    ERROR_CELL("ERROR_CELL","�ֻ��Ÿ�ʽ����ȷ"),
    /**���ֻ���δע��*/
    NO_REGIST_CELL("NO_REGIST_CELL","���ֻ���δע��"),
    /**�����������벻һ��*/
    TWICE_PWD("TWICE_PWD","�����������벻һ��"),
    /**���볤��6��30λ*/
    ERROR_PWD_LENGTH("ERROR_PWD_LENGTH","���볤��6��30λ"),
    /**���벻�ܴ������ĵ������ַ�*/
    ERROR_PWD_CONTENT("ERROR_PWD_CONTENT","���벻�ܴ������ĵ������ַ�"),
    ;

    /** ö����*/
    private String code;

    /** ������Ϣ*/
    private String message;

    private SysMsgEnum(String code, String message) {
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
     * @param code         ö�ٱ��
     * @return
     */
    public static SysMsgEnum getByCode(String code) {

        for (SysMsgEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}

