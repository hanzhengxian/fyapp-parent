package com.onway.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.onway.platform.common.base.ToString;

/**
 * 
 * @author guangdong.li
 *
 */
public class BaseModel extends ToString {

    /** serialVersionUID */
    private static final long serialVersionUID = -1311308794243280392L;

    /** 主键 */
    public int                id;

    /** 备注 */
    public String             memo;

    /** 创建时间 */
    public Date               gmtCreate;

    /** 修改时间 */
    public Date               gmtModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 重写toString方法
     * 
     * @param String
     *            字符串
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
