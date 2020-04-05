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
            //��������
            xml.append("<RouteRequest ");
            xml.append(" tracking_type='" + request.getTracking_type()+"'");
            xml.append(" tracking_number='" + request.getTracking_number()+"'");
            xml.append(" method_type='" + request.getMethod_type()+"'");
            xml.append(" />");

            xml.append("</Body>");

            xml.append(xml_request_end);

        } catch (Exception e) {
            log.error("�����ſ�ݵ��Ų�ѯ��������װ�쳣��" + e.getMessage());
        }
        
        System.out.println(xml.toString());
        return xml.toString();
    }

    static class Request {

        /**
         * ��ѯ�����:
            1:����˳���˵��Ų�ѯ,order�ڵ���tracking_number��������˳���˵��Ŵ���
            2:���ݿͻ������Ų�ѯ,order�ڵ���tracking_number���������ͻ������Ŵ���
            3:����,���ݿͻ�ԭʼ�����Ų�ѯ,order�ڵ���tracking_number������������ԭʼ�����Ŵ���
         */
        Integer tracking_type;

        /**
         * ��ѯ��:
                                ���tracking_type=1,���ֵΪ˳���˵���
                                ���tracking_type=2,���ֵΪ�ͻ�������
                                ���tracking_type=3,���ֵΪ����ԭʼ������
                                ����ж������,�Զ��ŷָ�,��"123,124,125"��
         */
        String  tracking_number;

        /**
         * ·�ɲ�ѯ���:1:��׼·�ɲ�ѯ
         */
        Integer method_type;

        /**
         * �ο�����(Ŀǰ�������ѷ�ͻ�,�ɿͻ���)
         */
        String  reference_number;

        /**
         * У��绰�������λֵ;���˵��Ų�ѯ·��ʱ,��ͨ���ò�����������У��ĵ绰�����4λ(�ķ����շ�������),����漰����˵���,��Ӧ��ֵҲ�谴˳������,����Ӣ�Ķ��Ÿ�����
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
