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
 * Title: UserRoleEnum
 * </p>
 * <p>
 * Description: 用户角色枚举   0营业员  1领导  2业务员  3财务  4质管  5客服  6普通用户
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月17日 下午4:48:54
 */
public enum UserRoleEnum implements EnumBase {
    
    /** 营业员*/
    ASS_ROLE("0","营业员"),
    
    /** 领导*/
    LEADER_ROLE("1","领导"),
    
    /** 业务员*/
    SALES_ROLE("2","业务员"),
    
    /** 财务*/
    ACC_ROLE("3","财务"),
    
    /** 质管*/
    SAFE_ROLE("4","质管"),
    
    /** 客服*/
    SERV_ROLE("5","客服"),
    
    /** 普通用户 */
    CUSTOMER("6", "普通用户"),
    
    /** 业务经理*/
    BANSHI_MA("7","业务经理"),
    
    /** 大区经理*/
    DAQU_MA("8","大区经理"),
    
    /** 督导*/
    DU_DAO("9","督导"),
    
    /** 财务经理*/
    CW_MA("10","财务经理"),
    
    /** 财务内勤*/
    CW_NQ("11","财务内勤"),
    
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
    private UserRoleEnum(String code, String message) {
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
    public static final UserRoleEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (UserRoleEnum item : values()) {
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
