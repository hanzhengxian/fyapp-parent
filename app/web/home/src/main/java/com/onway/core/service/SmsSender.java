//package com.onway.xcapi.core.service;
//
//import java.net.URLEncoder;
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import com.onway.common.lang.HttpUtils;
//import com.onway.common.lang.StringUtils;
//import com.onway.xcapi.common.dal.daointerface.SmDAO;
//import com.onway.xcapi.common.dal.dataobject.SmDO;
//import com.onway.platform.common.configration.ConfigrationFactory;
//
//@Component("yimeiSmsSender")
//public class SmsSender {
//    
//    
//    @Resource
//    private  SmDAO smDAO;
//    
//    
//    
//    private static final Logger logger = Logger.getLogger(SmsSender.class);
//
//    private final static String sn   = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.account");
//
//    private final static String PASSWD = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.pswd");
//
//    private final static String URL    = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.url");
//
//    private final static String key    = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.appkey");
//    
//    
//    
//    
//    
//    
//    public  boolean  send(String cell){
//        
//        String content="";
//        try {
//            
//            String verifyCode=getVerifyCode(6);
//            //��������
//           content = "�����֤��Ϊ��"+verifyCode+"������й¶";
//           
//            Map<String, String> paramsMap = new HashMap<String, String>();
//            paramsMap.put("username", sn);
//            paramsMap.put("password", PASSWD);
//            paramsMap.put("apikey", key);
//            paramsMap.put("mobile", cell);
//            paramsMap.put("content", URLEncoder.encode(content, "UTF-8"));
//            paramsMap.put("encode", "UTF-8");
//            
//            logger.info("�������ݣ�" + paramsMap.toString());
//
//            //ת������ֵ
//            String returnStr = HttpUtils.executePostMethod(URL, "UTF-8", paramsMap);
//
//            // ���ؽ��Ϊ��0��20140009090990,1���ύ�ɹ��� ���ͳɹ�   �����˵���ĵ�
//            logger.info(returnStr); //�ɹ�����Success ʧ�ܷ��أ�Faild
//            // ���ط��ͽ��
//            if (StringUtils.isBlank(returnStr)) {
//                return false;
//            }
//
//            if (StringUtils.contains(returnStr, "success")) {
//                
//                SmDO smDO=new SmDO();
//                smDO.setCell(cell);
//                smDO.setSmsStatus("SUCCESS");
//                smDO.setVerifyCode(verifyCode);
//                smDO.setMemo(content);
//                smDAO.insert(smDO);
//                return true;
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("���ŷ���ʧ��,������Ϣͨ�� phone:{0},content:{1}", new Object[] {
//                    cell, content}));
//            return false;
//        }
//        
//    }
//    
//    
//    /*
//     * �û����ƻ���������
//     */
//  public  boolean  sendMsg(String cell){
//        
//        String content="";
//        try {
//            
//            //��������
//           content = "�����ɰ����ã������յ���ӦƸ�ļ������������΢�Ź��ںš����ɰ�����񡰵������Ҫ��ְ�����������߽ӵ������ǻ��ݸ���������Ź������ͷ�΢�ţ�13012985447��~~����Ī���Ļ���ý���޹�˾";
//
//            Map<String, String> paramsMap = new HashMap<String, String>();
//            paramsMap.put("username", sn);
//            paramsMap.put("password", PASSWD);
//            paramsMap.put("apikey", key);
//            paramsMap.put("mobile", cell);
//            paramsMap.put("content", URLEncoder.encode(content, "UTF-8"));
//            paramsMap.put("encode", "UTF-8");
//            
//            logger.info("�������ݣ�" + paramsMap.toString());
//
//            //ת������ֵ
//            String returnStr = HttpUtils.executePostMethod(URL, "UTF-8", paramsMap);
//
//            // ���ؽ��Ϊ��0��20140009090990,1���ύ�ɹ��� ���ͳɹ�   �����˵���ĵ�
//            logger.info(returnStr); //�ɹ�����Success ʧ�ܷ��أ�Faild
//            // ���ط��ͽ��
//            if (StringUtils.isBlank(returnStr)) {
//                return false;
//            }
//
//            if (StringUtils.contains(returnStr, "success")) {
//                
//                SmDO smDO=new SmDO();
//                smDO.setCell(cell);
//                smDO.setSmsStatus("SUCCESS");
//                smDO.setMemo(content);
//                smDAO.insert(smDO);
//                return true;
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("���ŷ���ʧ��,������Ϣͨ�� phone:{0},content:{1}", new Object[] {
//                    cell, content}));
//            return false;
//        }
//        
//    }
//    
//    
//    
//    public  String getVerifyCode(int length) {
//        if (length < 1 || length > 10) {
//            return "";
//        }
//        StringBuffer sb = new StringBuffer();
//        for (int i = 1; i <= length; i++) {
//            int rand = new Random().nextInt(10);
//            sb.append(rand);
//        }
//        return sb.toString();
//    }
//
//}
