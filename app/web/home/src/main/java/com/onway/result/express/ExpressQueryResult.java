package com.onway.result.express;

import java.util.List;

import com.onway.platform.common.base.BaseResult;

public class ExpressQueryResult extends BaseResult {

    private static final long     serialVersionUID = 327234125L;
    /**  */
    private List<ExpressDataPojo> data;

    private String                status;

    private String                expressName;

    private String                expressNu;
    
    /** 收货人 */
    private String consignee;
    
    /** 收件人电话 */
    private String consigneeCell;
    
    /** 收件人地址 */
    private String consigneeAddress;
    
    /** 商品的图片 */
    List<String> listIcons;
    
    
    
    public List<String> getListIcons() {
        return listIcons;
    }

    public void setListIcons(List<String> listIcons) {
        this.listIcons = listIcons;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeCell() {
        return consigneeCell;
    }

    public void setConsigneeCell(String consigneeCell) {
        this.consigneeCell = consigneeCell;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressNu() {
        return expressNu;
    }

    public void setExpressNu(String expressNu) {
        this.expressNu = expressNu;
    }

    public ExpressQueryResult(boolean success) {
        super(success);
    }

    public List<ExpressDataPojo> getData() {
        return data;
    }

    public void setData(List<ExpressDataPojo> data) {
        this.data = data;
    }

}
