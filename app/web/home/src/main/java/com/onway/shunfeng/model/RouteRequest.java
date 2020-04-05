package com.onway.shunfeng.model;

public class RouteRequest {

    /**
     * 查询号类别:
        1:根据顺丰运单号查询,order节点中tracking_number将被当作顺丰运单号处理
        2:根据客户订单号查询,order节点中tracking_number将被当作客户订单号处理
        3:逆向单,根据客户原始订单号查询,order节点中tracking_number将被当作逆向单原始订单号处理
     */
    Integer tracking_type;

    /**
     * 查询号:
                            如果tracking_type=1,则此值为顺丰运单号
                            如果tracking_type=2,则此值为客户订单号
                            如果tracking_type=3,则此值为逆向单原始订单号
                            如果有多个单号,以逗号分隔,如"123,124,125"。
     */
    String  tracking_number;

    /**
     * 路由查询类别:1:标准路由查询
     */
    Integer method_type;

    /**
     * 参考编码(目前针对亚马逊客户,由客户传)
     */
    String  reference_number;

    /**
     * 校验电话号码后四位值;按运单号查询路由时,可通过该参数传入用于校验的电话号码后4位(寄方或收方都可以),如果涉及多个运单号,对应该值也需按顺序传入多个,并以英文逗号隔开。
     */
    String  check_phoneNo;

    public RouteRequest(Integer tracking_type, String tracking_number, Integer method_type) {
        this.tracking_type = tracking_type;
        this.tracking_number = tracking_number;
        this.method_type = method_type;
    }

    public RouteRequest(Integer tracking_type, String tracking_number, Integer method_type,
                        String reference_number, String check_phoneNo) {
        this.tracking_type = tracking_type;
        this.tracking_number = tracking_number;
        this.method_type = method_type;
        this.reference_number = reference_number;
        this.check_phoneNo = check_phoneNo;
    }

    public Integer getTracking_type() {
        return tracking_type;
    }

    public void setTracking_type(Integer tracking_type) {
        this.tracking_type = tracking_type;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public Integer getMethod_type() {
        return method_type;
    }

    public void setMethod_type(Integer method_type) {
        this.method_type = method_type;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public String getCheck_phoneNo() {
        return check_phoneNo;
    }

    public void setCheck_phoneNo(String check_phoneNo) {
        this.check_phoneNo = check_phoneNo;
    }

}
