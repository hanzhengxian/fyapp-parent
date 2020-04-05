package com.onway.result;

/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

import java.io.Serializable;
import java.util.Date;

import com.onway.common.lang.DateUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * json调用默认结果
 * 
 * @author guangdong.li
 * @version $Id: JsonResult.java, v 0.1 2013-10-30 下午5:38:55  Exp $
 */
public class JsonResult implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1475348231900998033L;

    private String            time             = DateUtils.format(new Date(), DateUtils.newFormat);

    // 业务操作结果
    private boolean           success          = true;

    // 是否强制下线，账号已在另一设备登录
    //private boolean           isForceLogOut    = false;

    private String            message           = "";

    // 错误码
    private String            code              = "";
    

    public JsonResult(boolean success) {
        this.success = success;
    }

    public JsonResult(boolean success, String code, String message) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public JsonResult() {
		// TODO Auto-generated constructor stub
	}

	public void markResult(boolean success, String code, String message) {
    	this.success = success;
        this.message = message;
        this.code = code;
    }
	
	/**
     * 设置结果集
     * 
     * @param success
     * @param resultCode
     */
    public void markResult(boolean success, EnumBase resultCode) {
        this.success = success;
        if (resultCode != null) {
            this.code = resultCode.name();
            this.message = resultCode.message();
        }
    }

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
