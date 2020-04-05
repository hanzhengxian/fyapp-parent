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
 * <p>
 * Title: UserRoleEnum
 * </p>
 * <p>
 * Description: 用户审核角色枚举     2业务员  3财务  4质管  5客服  
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年7月17日 下午4:48:54
 */
public enum UserCheckRoleEnum implements EnumBase {

    /** 业务员*/
    SALES_ROLE("2", "业务员"),

    /** 财务*/
    ACC_ROLE("3", "财务"),

    /** 质管*/
    SAFE_ROLE("4", "质管"),

    /** 客服*/
    SERV_ROLE("5", "客服"),

    /** 业务经理*/
    BANSHI_MA("7", "业务经理"),

    /** 大区经理*/
    DAQU_MA("8", "大区经理"),

    /** 督导*/
    DU_DAO("9", "督导"),

    /** 财务经理*/
    CW_MA("10", "财务经理"),

    /** 财务内勤*/
    CW_NQ("11", "财务内勤"),

    ;
    /** 枚举编号 */
    private String                     code;

    /** 枚举详情 */
    private String                     message;
    

    public static final List<EnumItem> list = new ArrayList<EnumItem>(9);

    static {
        for (UserCheckRoleEnum param : values()) {
            list.add(new EnumItem(param.getCode(), param.message()));
        }
    }

    /**
     * 收银审核 
     * 
     * @return
     */
    public static final List<String> cashierOrderCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(BANSHI_MA.code);
        list.add(DAQU_MA.code);
        return list;
    }

    /**
     * 异常订单（取消）业务审核
     * 
     * @return
     */
    public static final List<String> errOrderCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        list.add(BANSHI_MA.code);
        list.add(DAQU_MA.code);
        list.add(DU_DAO.code);
        list.add(CW_MA.code);
        list.add(CW_NQ.code);
        return list;
    }

    /**
     * 调拨审核
     * 
     * @return
     */
    public static final List<String> allocateProCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        return list;
    }

    /**
     * 庆余卡审核    
     * 
     * @return
     */
    public static final List<String> voucherCheckRole() {
        List<String> list = new ArrayList<String>();
        list.add(SALES_ROLE.code);
        list.add(ACC_ROLE.code);
        return list;
    }

    /**
     * 构造方法
     * 
     * @param code
     *            枚举编号
     * @param message
     *            枚举详情
     */
    private UserCheckRoleEnum(String code, String message) {
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
    public static final UserCheckRoleEnum getByCode(String code) {

        // 如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (UserCheckRoleEnum item : values()) {
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
