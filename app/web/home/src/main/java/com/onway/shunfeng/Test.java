package com.onway.shunfeng;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Test {

    public static void main(String[] args) throws DocumentException {
        String xml = "<Response service=\"RouteService\">"
                     + "<Head>OK</Head>"
                     + "<Body>"
                     + "<RouteResponse mailno=\"444003077898\">"
                     + "<Route accept_time=\"2015-01-04 10:11:26\" accept_address=\"深圳\" remark=\"已收件\" opcode=\"50\"/>"
                     + "<Route accept_time=\"2015-01-05 17:41:50\" remark=\"此件签单返还的单号为 123638813180\" opcode=\"922\"/>"
                     + "</RouteResponse>" + " </Body>" + "</Response>";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element rootElement = document.getRootElement();
            System.out.println(rootElement.getName());
            
            Element element2 = rootElement.element("Head");
            System.out.println(element2.getName());
            System.out.println(element2.getText());
            
            Element element3 = rootElement.element("Body");
            System.out.println(element3.getName());
            
            Element element31 = element3.element("RouteResponse");
            System.out.println(element31.getName());
            System.out.println(element31.attributeValue("mailno"));
            
            @SuppressWarnings("unchecked")
            List<Element> elements = element31.elements("Route");
            for (Element element : elements) {
                System.out.println("accept_time=" + element.attributeValue("accept_time"));
                System.out.println("accept_address=" + element.attributeValue("accept_address"));
                System.out.println("remark=" + element.attributeValue("remark"));
                System.out.println("opcode=" + element.attributeValue("opcode"));
            }

        } catch (DocumentException e) {

            e.printStackTrace();
        }
    }
}
