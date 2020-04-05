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
* <p>Title: AccountLogFlgEnum</p>  
* <p>Description: 账户流水标识枚举</p>  
* @author yugang.ni  
* @date 2018年6月26日  下午5:34:20
 */
public enum AccountLogFlgEnum implements EnumBase {

    /** 支出 -*/
	COST("1", "支出"),
	
	/** 支出退回 +*/
    COST_RETURN("1.1", "支出退回"),

    /** 返利  +*/
    REBATE("2", "返利"),
    
    /** 返利回退  -*/
    REBATE_RETURN("2.1", "收入回退"),
    
    /** 购物  +*/
    SHOPPING("3", "购物"),
    
    /** 收入  +*/
    RECEIVE("4", "收入"),
    
    /** 退货  +*/
    RETURN("5", "退货"),
    
    /** 提现  -*/
    PUTFORWARD("6", "提现"),
    
    /** 提现退回  +*/
    RETURNPUTFORWARD("7", "提现退回"),
    
    /** 兑换  +*/
    EXCHANGE("8", "兑换"),
    
    /** 领取  +*/
    GETRECEIVE("9", "领取"),
    
    /** 积分过期  -*/
    OUT_TIME("10", "积分过期"),
    
    /** 邀请好友  +*/
    SHARE("11", "邀请好友"),
    
    /** 失效  贡献值  -*/
    NOLONGER("12", "失效"),
    
    ;
    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;
    
    public static final List<EnumItem> list = new ArrayList<EnumItem>(13);
    
    static{
        for (AccountLogFlgEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }
    

    /**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private AccountLogFlgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final AccountLogFlgEnum getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (AccountLogFlgEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
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
     * @param code value to be assigned to property code
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
