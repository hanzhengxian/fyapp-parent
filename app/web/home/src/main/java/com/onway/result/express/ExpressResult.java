package com.onway.result.express;

import com.onway.platform.common.base.ToString;

public class ExpressResult extends ToString {

    /**  */
    private static final long serialVersionUID = 1264182100039330861L;

    //快递公司编码
    private String            expressNo;

    //快递公司code
    private String            com;

    //快递公司名字
    private String            expressName;

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

   
}
