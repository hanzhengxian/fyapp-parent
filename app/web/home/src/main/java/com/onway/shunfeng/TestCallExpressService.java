package com.onway.shunfeng;

import net.sf.json.xml.XMLSerializer;

import org.dom4j.DocumentException;

import com.alibaba.fastjson.JSONObject;
import com.onway.shunfeng.model.request.AddedService;
import com.onway.shunfeng.model.request.Cargo;
import com.onway.shunfeng.model.request.Order;
import com.onway.shunfeng.util.ComplateRequest;
import com.sf.csim.express.service.CallExpressServiceTools;

public class TestCallExpressService {

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws DocumentException {

        //丰桥平台公共测试账号  
        //SLKJ2019
        //FBIqMkZjzxbsZgo7jTpeq7PD8CVzLT4Q
        String reqURL = "http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
        String clientCode = "LXJ_S9nuX";//此处替换为您在丰桥平台获取的顾客编码
        String checkword = "GK8htwbwgmXN8r3JeQNvoqER9FEfcFtF";//此处替换为您在丰桥平台获取的校验码

        Order order = new Order();
        order.setOrderid("201903130006");
        order.setExpress_type("1");

        order.setJ_province("浙江省");
        order.setJ_city("杭州市");
        order.setJ_company("杭州胡庆余堂药品零售有限公司");
        order.setJ_contact("冯琪");
        order.setJ_tel("18657107503");
        order.setJ_address("浙江省-杭州市-滨江区浦沿街道至仁街10号");

        order.setD_province("上海市");
        order.setD_city("上海市");
        order.setD_county("");
//        order.setD_company("神罗科技");
        order.setD_contact("风一样的旭哥");
        order.setD_tel("33992159");
        order.setD_address("海珠区宝芝林大厦701室");

        order.setParcel_quantity(1);
        order.setPay_method("1");
        order.setCustid("");

        Cargo cargo = new Cargo();
        cargo.setName("iphone 7 plus");

        AddedService addedService = new AddedService();

        String requestXml = ComplateRequest.getRequest(order, cargo, addedService, clientCode);

        CallExpressServiceTools client = CallExpressServiceTools.getInstance();
        System.out.println("请求报文：" + requestXml);
        String respXml = client.callSfExpressServiceByCSIM(reqURL, requestXml, clientCode,
            checkword);

        if (respXml != null) {
            System.out.println("--------------------------------------");
            System.out.println("返回报文: " + respXml);
            System.out.println("--------------------------------------");
        }  
        
        XMLSerializer xmlSerializer = new XMLSerializer();
        String xmlStr = xmlSerializer.read(respXml).toString();
        
        JSONObject resp = JSONObject.parseObject(xmlStr);
        String code = resp.getString("Head");
        // 接口请求成功
        if (code.equals("OK")) {
            JSONObject orderResponse = resp.getJSONObject("Body").getJSONObject("OrderResponse");
            // 下单返回结果  1：人工确认  2：可收派  3：不可以收派
            String filter_result = orderResponse.getString("@filter_result");
            // 顺丰运单号
            String mailno = orderResponse.getString("@mailno");
            System.out.println("----------顺丰请求成功-------，打印订单号：" + mailno);
            System.out.println("----------顺丰请求成功-------，filter_result：" + filter_result);
        } else {
            System.out.println("----------顺丰请求失败-----");
            String error = resp.getString("ERROR");
            System.out.println("----------顺丰请求失败-----" + error);
        }
    }

    /*********************标准返回报文参考*****************************/
    //1.下订单-请求返回结果
    // <?xml version='1.0' encoding='UTF-8'?><Response service="OrderService"><Head>OK</Head><Body><OrderResponse filter_result="2" destcode="020" mailno="444000010085" origincode="755" orderid="QIAO-20171017001"/></Body></Response>

    //2.订单结果查询 -请求返回结果
    //<?xml version='1.0' encoding='UTF-8'?><Response service="OrderSearchService"><Head>OK</Head><Body><OrderResponse filter_result="2" destcode="020" mailno="444000010085,819000008006,819000008015" origincode="755" orderid="QIAO-20171017001"/></Body></Response>

    //3.订单取消-请求返回结果
    // <?xml version='1.0' encoding='UTF-8'?><Response service="OrderConfirmService"><Head>OK</Head><Body><OrderConfirmResponse res_status="2" orderid="QIAO-20171017001"/></Body></Response>

    //4.订单筛选-请求返回结果
    // <?xml version='1.0' encoding='UTF-8'?><Response service="OrderFilterService"><Head>OK</Head><Body><OrderFilterResponse filter_result="1" orderid="QIAO-20171017001"/></Body></Response>

    //5.路由查询-请求返回结果
    // <?xml version='1.0' encoding='UTF-8'?><Response service="RouteService"><Head>OK</Head><Body><RouteResponse mailno="444000010085" orderid="QIAO-20171017001"><Route remark="已经收件" accept_time="2017-08-31 02:01:44" accept_address="广东省深圳市软件产业基地" opcode="50"/><Route remark="已经收件" accept_time="2017-08-31 02:01:44" accept_address="广东省深圳市软件产业基地" opcode="80"/></RouteResponse></Body></Response>

    //6.路由推送

    //7.子单号申请-请求返回结果
    //<?xml version='1.0' encoding='UTF-8'?><Response service="OrderZDService"><Head>OK</Head><Body><OrderZDResponse><OrderZDResponse main_mailno="444000010085" mailno_zd="819000008006,819000008015" orderid="QIAO-20171017001"/></OrderZDResponse></Body></Response>

}
