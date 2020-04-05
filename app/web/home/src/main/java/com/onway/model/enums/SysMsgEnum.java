package com.onway.model.enums; 

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 
* <p>Title: SysMsgEnum</p>  
* <p>Description: 系统返回信息  枚举</p>  
* @author yugang.ni  
* @date 2018年7月4日  上午11:53:57
 */
public enum SysMsgEnum implements EnumBase {
	/**异常*/
    ERROR("ERROR","异常"),
    /**操作成功*/
    SUCCESS("SUCCESS","操作成功"),
    /**操作失败*/
    FAIL("FAIL","操作失败"),
	/**账户不存在*/
    INEXISTENCE("INEXISTENCE","该账户不存在"),
    /**账号或密码错误*/
    ERROR_LOGIN("ERROR_LOGIN","账号或密码错误"),
    /**该账户已注册*/
    REGIST_AGAIN("REGIST_AGAIN","该账户已注册"),
    /**登陆成功*/
    LOGIN_SUCCESS("LOGIN_SUCCESS","登陆成功"),
    /**登陆失败*/
    LOGIN_FAIL("LOGIN_FAIL","登陆失败"),
    /**登陆成功*/
    REGIST_SUCCESS("REGIST_SUCCESS","注册成功"),
    /**登陆失败*/
    REGIST_FAIL("REGIST_FAIL","注册失败"),
    /**发送验证码成功*/
    SEND_VERCODE_SUCCESS("SEND_VERCODE_SUCCESS","发送验证码成功"),
    /**发送验证码失败*/
    SEND_VERCODE_FAIL("SEND_VERCODE_FAIL","发送验证码失败"),
    /**非法请求*/
    INVALID_REQUEST("INVALID_REQUEST","非法请求"),
    /**手机号格式不正确*/
    ERROR_CELL("ERROR_CELL","手机号格式不正确"),
    /**该手机号未注册*/
    NO_REGIST_CELL("NO_REGIST_CELL","该手机号未注册"),
    /**两次密码输入不一致*/
    TWICE_PWD("TWICE_PWD","两次密码输入不一致"),
    /**密码长度6到30位*/
    ERROR_PWD_LENGTH("ERROR_PWD_LENGTH","密码长度6到30位"),
    /**密码不能带有中文等特殊字符*/
    ERROR_PWD_CONTENT("ERROR_PWD_CONTENT","密码不能带有中文等特殊字符"),
    ;

    /** 枚举码*/
    private String code;

    /** 描述信息*/
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
     * 通过枚举<code>code</code>获得枚举。
     * 
     * @param code         枚举编号
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

