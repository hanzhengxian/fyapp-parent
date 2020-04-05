package com.onway.shunfeng.util;

import org.apache.log4j.Logger;

import com.onway.shunfeng.model.RouteRequest;
import com.onway.shunfeng.model.request.AddedService;
import com.onway.shunfeng.model.request.Cargo;
import com.onway.shunfeng.model.request.Order;

public class ComplateRequest {
    
    private static final Logger   log               = Logger.getLogger(ComplateRequest.class);
    
    public static String getRequest(Order order, Cargo cargo, AddedService addedService,
                                    String clientCode) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<Request service='OrderService' lang='zh-CN' >");
        buffer.append("<Head>");
        buffer.append(clientCode);
        buffer.append("</Head>");
        buffer.append("<Body>");

        buffer.append("<Order ");
        buffer.append("orderid='").append(order.getOrderid()).append("' ");//*
        buffer.append("express_type='").append(order.getExpress_type()).append("' ");//*

        buffer.append("j_province='").append(order.getJ_province()).append("' ");
        buffer.append("j_city='").append(order.getJ_city()).append("' ");
        buffer.append("j_company='").append(order.getJ_company()).append("' ");
        buffer.append("j_contact='").append(order.getJ_contact()).append("' ");
        buffer.append("j_tel='").append(order.getJ_tel()).append("' ");
        buffer.append("j_address='").append(order.getJ_address()).append("' ");

        buffer.append("d_province='").append(order.getD_province()).append("' ");
        buffer.append("d_city='").append(order.getD_city()).append("' ");
        buffer.append("d_county='").append(order.getD_county()).append("' ");
        buffer.append("d_company='").append(order.getD_company()).append("' ");
        buffer.append("d_contact='").append(order.getD_company()).append("' ");
        buffer.append("d_tel='").append(order.getD_tel()).append("' ");
        buffer.append("d_address='").append(order.getD_address()).append("' ");

        buffer.append("parcel_quantity='").append(order.getParcel_quantity()).append("' ");//*
        buffer.append("pay_method='").append(order.getPay_method()).append("' ");//*

        buffer.append("custid='").append(order.getCustid()).append("' >");//顺丰月结卡号
        buffer.append("</Order>");

        buffer.append("<Cargo ");
        buffer.append("name='").append(cargo.getName()).append("' >");
        buffer.append("</Cargo>");

        buffer.append("</Body>");
        buffer.append("</Request>");

        return buffer.toString();
    }
    
    public static String format_routeRequest_xml(String clientCode, RouteRequest request) {
        StringBuilder xml = new StringBuilder();

        try {
            xml.append("<Request service='RouteService' lang='zh-CN'>");
            xml.append("<Head>" + clientCode + "</Head>");

            xml.append("<Body>");
            //参数部分
            xml.append("<RouteRequest ");
            xml.append(" tracking_type='" + request.getTracking_type()+"'");
            xml.append(" tracking_number='" + request.getTracking_number()+"'");
            xml.append(" method_type='" + request.getMethod_type()+"'");
            xml.append(" />");

            xml.append("</Body>");

            xml.append("</Request>");

        } catch (Exception e) {
            log.error("【丰桥快递单号查询】参数封装异常，" + e.getMessage());
        }
        
        System.out.println(xml.toString());
        return xml.toString();
    }
}
