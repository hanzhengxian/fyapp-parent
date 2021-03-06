package com.onway.shunfeng;

import org.apache.log4j.Logger;

import com.sf.csim.express.service.CallExpressServiceTools;

public class TestSf {

    private static final Logger   log               = Logger.getLogger(TestSf.class);

    private static final String   xml_request_start = "<Request service='RouteService' lang='zh-CN'>";

    private static final String   xml_request_end   = "</Request>";

    private static final String   encod             = "UTF-8";

    protected static final String SF_REQ_URL        = "http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";

    protected static final String SF_CLIENT_CODE    = "LXJ_S9nuX";
    
    protected static final String SF_CHECK_CODE    = "GK8htwbwgmXN8r3JeQNvoqER9FEfcFtF";

    public static void main(String[] args) {

        String mailno = "235544409023";
        Request request = new Request(1, mailno, 1);

        CallExpressServiceTools client = CallExpressServiceTools.getInstance();

        String respXml = client.callSfExpressServiceByCSIM(SF_REQ_URL, format_request_xml(request), SF_CLIENT_CODE,
            SF_CHECK_CODE);
        
        System.out.println(respXml);
    }

    private static String format_request_xml(Request request) {
        StringBuilder xml = new StringBuilder();

        try {
            xml.append(xml_request_start);
            xml.append("<Head>" + SF_CLIENT_CODE + "</Head>");

            xml.append("<Body>");
            //参数部分
            xml.append("<RouteRequest ");
            xml.append(" tracking_type='" + request.getTracking_type()+"'");
            xml.append(" tracking_number='" + request.getTracking_number()+"'");
            xml.append(" method_type='" + request.getMethod_type()+"'");
            xml.append(" />");

            xml.append("</Body>");

            xml.append(xml_request_end);

        } catch (Exception e) {
            log.error("【丰桥快递单号查询】参数封装异常，" + e.getMessage());
        }
        
        System.out.println(xml.toString());
        return xml.toString();
    }

    static class Request {

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

        public Request(Integer tracking_type, String tracking_number, Integer method_type) {
            this.tracking_type = tracking_type;
            this.tracking_number = tracking_number;
            this.method_type = method_type;
        }

        public Request(Integer tracking_type, String tracking_number, Integer method_type,
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
}
