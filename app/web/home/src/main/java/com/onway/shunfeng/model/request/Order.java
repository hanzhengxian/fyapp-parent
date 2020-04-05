package com.onway.shunfeng.model.request;

/**
 * https://qiao.sf-express.com/pages/developDoc/index.html?level2=763554
 * 请求
 * @author yuhang.ni
 * @version $Id: OrderSF.java, v 0.1 2019年3月13日 上午11:46:55 Administrator Exp $
 */
public class Order {

    //客户订单号  是
    private String orderid;

    //顺丰运单号，一个订单只能有一个母单号，如果是子母单的情况，以半角逗号分隔，主单号在第一个位置，如“755123456789,001123456789,002123456789”，对于路由推送注册，此字段为必填。
    private String mailno;

    //寄件方公司名称，如果需要生成电子运单，则为必填。
    private String j_company;

    //寄件方联系人，如果需要生成电子运单，则为必填。
    private String j_contact;

    //寄件方联系电话，如果需要生成电子运单，则为必填。
    private String j_tel;

    //寄件方手机 否
    private String j_mobile;

    //寄件方所在省份 字段填写要求：必须是标准的省名称称谓 如：广东省，如果是直辖市，请直接传北京、上海等。 否
    private String j_province;

    //寄件方所在城市名称，字段填写要求：必须是标准的城市称谓 如：深圳市。 否
    private String j_city;

    //寄件人所在县/区，必须是标准的县/区称谓，示例：“福田区”。 否
    private String j_county;

    //寄件方详细地址，包括省市区，示例：“广东省深圳市福田区新洲十一街万基商务大厦10楼” ，如果需要生成电子运单，则为必填。
    private String j_address;

    //到件方公司名称 是
    private String d_company;

    //到件方联系人 是
    private String d_contact;

    //到件方联系电话 是
    private String d_tel;

    //到件方手机 否
    private String d_mobile;

    //到件方所在省份，必须是标准的省名称称谓 如：广东省，如果是直辖市，请直接传北京、上海等。如果此字段与d_city字段都有值，系统则直接使用这两个值而不是通过对d_address进行地址识别获取。为避免地址识别不成功的风险，建议传输此字段。
    private String d_province;

    //到件方所在城市名称，必须是标准的城市称谓 如：深圳市，如果是直辖市，请直接传北京（或北京市）、上海（或上海市）等。如果此字段与d_province字段都有值，顺丰系统则直接使用这两个值而不是对d_address进行地址识别获取。为避免地址识别不成功的风险，建议传输此字段。
    private String d_city;

    //到件方所在县/区，必须是标准的县/区称谓，示例：“福田区”。
    private String d_county;

    //到件方详细地址，如果不传输d_province/d_city字段，此详细地址需包含省市信息，以提高地址识别的成功率，示例：“广东省深圳市福田区新洲十一街万基商务大厦10楼”。
    private String d_address;

    //顺丰月结卡号
    private String custid;

    //付款方式：1:寄方付 2:收方付 3:第三方付   否
    private String pay_method;

    //快件产品编码，详见附录《快件产品类别表》，只有在商务上与顺丰约定的类别方可使用。 否
    private String express_type;

    //包裹数，一个包裹对应一个运单号，如果是大于1个包裹，则返回则按照子母件的方式返回母运单号和子运单号。  否
    private int    parcel_quantity;

    //客户订单货物总长，单位厘米，精确到小数点后3位，包含子母件。 否
    private double cargo_length;

    //客户订单货物总宽，单位厘米，精确到小数点后3位，包含子母件。 否
    private double cargo_width;

    //客户订单货物总高，单位厘米，精确到小数点后3位，包含子母件。 否
    private double cargo_height;

    //订单货物总体积，单位立方厘米，精确到小数点后3位，会用于计抛（是否计抛具体商务沟通中双方约定）。 否
    private double volume;

    //订单货物总重量，包含子母件，单位千克，精确到小数点后3位，如果提供此值，必须>0 。否
    private double cargo_total_weight;

    //要求上门取件开始时间，格式：YYYY-MM-DD HH24:MM:SS，示例：2012-7-30 09:30:00。否
    private String sendstarttime;

    //是否要求通过手持终端通知顺丰收派员收件：1：要求   其它为不要求 否
    private String is_docall;

    //是否要求签回单号：1：要求（丰密签回单必传routelabelForReturn、routelabelService）其它为不要求 否
    private String need_return_tracking_no;

    //顺丰签回单服务运单号 否
    private String return_tracking;

    //温度范围类型，当express_type为12医药温控件时必填：1为冷藏 3为冷冻 否
    private String temp_range;

    //业务模板编码，业务模板指顺丰系统针对客户业务需求配置的一套接口处理逻辑，一个接入编码可对应多个业务模板。 否
    private String template;

    //备注  否
    private String remark;

    //快件自取；1表示客户同意快件自取；非1表示客户不同意快件自取  否
    private String oneself_pickup_flg;

    //特殊派送类型代码 1:身份验证  否
    private String special_delivery_type_code;

    //特殊派件具体表述  证件类型:证件后8位  如：1:09296231（1表示身份证，暂不支持其他证件） 否
    private String special_delivery_value;

    //实名认证流水号 否
    private String realname_num;

    //签回单路由标签返回：默认0，1：查询，其他：不查询    否
    private String routelabelForReturn;

    //路由标签查询服务默认0不查询1查询其他不查询   否
    private String routelabelService;

    //是否使用国家统一面单号   1：是， 0：否（默认）   否
    private String is_unified_waybill_no;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMailno() {
        return mailno;
    }

    public void setMailno(String mailno) {
        this.mailno = mailno;
    }

    public String getJ_company() {
        return j_company;
    }

    public void setJ_company(String j_company) {
        this.j_company = j_company;
    }

    public String getJ_contact() {
        return j_contact;
    }

    public void setJ_contact(String j_contact) {
        this.j_contact = j_contact;
    }

    public String getJ_tel() {
        return j_tel;
    }

    public void setJ_tel(String j_tel) {
        this.j_tel = j_tel;
    }

    public String getJ_mobile() {
        return j_mobile;
    }

    public void setJ_mobile(String j_mobile) {
        this.j_mobile = j_mobile;
    }

    public String getJ_province() {
        return j_province;
    }

    public void setJ_province(String j_province) {
        this.j_province = j_province;
    }

    public String getJ_city() {
        return j_city;
    }

    public void setJ_city(String j_city) {
        this.j_city = j_city;
    }

    public String getJ_county() {
        return j_county;
    }

    public void setJ_county(String j_county) {
        this.j_county = j_county;
    }

    public String getJ_address() {
        return j_address;
    }

    public void setJ_address(String j_address) {
        this.j_address = j_address;
    }

    public String getD_company() {
        return d_company;
    }

    public void setD_company(String d_company) {
        this.d_company = d_company;
    }

    public String getD_contact() {
        return d_contact;
    }

    public void setD_contact(String d_contact) {
        this.d_contact = d_contact;
    }

    public String getD_tel() {
        return d_tel;
    }

    public void setD_tel(String d_tel) {
        this.d_tel = d_tel;
    }

    public String getD_mobile() {
        return d_mobile;
    }

    public void setD_mobile(String d_mobile) {
        this.d_mobile = d_mobile;
    }

    public String getD_province() {
        return d_province;
    }

    public void setD_province(String d_province) {
        this.d_province = d_province;
    }

    public String getD_city() {
        return d_city;
    }

    public void setD_city(String d_city) {
        this.d_city = d_city;
    }

    public String getD_county() {
        return d_county;
    }

    public void setD_county(String d_county) {
        this.d_county = d_county;
    }

    public String getD_address() {
        return d_address;
    }

    public void setD_address(String d_address) {
        this.d_address = d_address;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public String getExpress_type() {
        return express_type;
    }

    public void setExpress_type(String express_type) {
        this.express_type = express_type;
    }

    public int getParcel_quantity() {
        return parcel_quantity;
    }

    public void setParcel_quantity(int parcel_quantity) {
        this.parcel_quantity = parcel_quantity;
    }

    public double getCargo_length() {
        return cargo_length;
    }

    public void setCargo_length(double cargo_length) {
        this.cargo_length = cargo_length;
    }

    public double getCargo_width() {
        return cargo_width;
    }

    public void setCargo_width(double cargo_width) {
        this.cargo_width = cargo_width;
    }

    public double getCargo_height() {
        return cargo_height;
    }

    public void setCargo_height(double cargo_height) {
        this.cargo_height = cargo_height;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCargo_total_weight() {
        return cargo_total_weight;
    }

    public void setCargo_total_weight(double cargo_total_weight) {
        this.cargo_total_weight = cargo_total_weight;
    }

    public String getSendstarttime() {
        return sendstarttime;
    }

    public void setSendstarttime(String sendstarttime) {
        this.sendstarttime = sendstarttime;
    }

    public String getIs_docall() {
        return is_docall;
    }

    public void setIs_docall(String is_docall) {
        this.is_docall = is_docall;
    }

    public String getNeed_return_tracking_no() {
        return need_return_tracking_no;
    }

    public void setNeed_return_tracking_no(String need_return_tracking_no) {
        this.need_return_tracking_no = need_return_tracking_no;
    }

    public String getReturn_tracking() {
        return return_tracking;
    }

    public void setReturn_tracking(String return_tracking) {
        this.return_tracking = return_tracking;
    }

    public String getTemp_range() {
        return temp_range;
    }

    public void setTemp_range(String temp_range) {
        this.temp_range = temp_range;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOneself_pickup_flg() {
        return oneself_pickup_flg;
    }

    public void setOneself_pickup_flg(String oneself_pickup_flg) {
        this.oneself_pickup_flg = oneself_pickup_flg;
    }

    public String getSpecial_delivery_type_code() {
        return special_delivery_type_code;
    }

    public void setSpecial_delivery_type_code(String special_delivery_type_code) {
        this.special_delivery_type_code = special_delivery_type_code;
    }

    public String getSpecial_delivery_value() {
        return special_delivery_value;
    }

    public void setSpecial_delivery_value(String special_delivery_value) {
        this.special_delivery_value = special_delivery_value;
    }

    public String getRealname_num() {
        return realname_num;
    }

    public void setRealname_num(String realname_num) {
        this.realname_num = realname_num;
    }

    public String getRoutelabelForReturn() {
        return routelabelForReturn;
    }

    public void setRoutelabelForReturn(String routelabelForReturn) {
        this.routelabelForReturn = routelabelForReturn;
    }

    public String getRoutelabelService() {
        return routelabelService;
    }

    public void setRoutelabelService(String routelabelService) {
        this.routelabelService = routelabelService;
    }

    public String getIs_unified_waybill_no() {
        return is_unified_waybill_no;
    }

    public void setIs_unified_waybill_no(String is_unified_waybill_no) {
        this.is_unified_waybill_no = is_unified_waybill_no;
    }

}
