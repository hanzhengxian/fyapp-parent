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

    /** ���� */
    public int                id;

    /** ��ע */
    public String             memo;

    /** ����ʱ�� */
    public Date               gmtCreate;

    /** �޸�ʱ�� */
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
     * ��дtoString����
     * 
     * @param String
     *            �ַ���
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
