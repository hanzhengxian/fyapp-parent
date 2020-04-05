package com.onway.utils.sendMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.HttpUtils;

/**
 * ���Ϳͷ���Ϣ���� 
 * 
 * @author yugang.ni
 * @version $Id: SendCustomMessage.java, v 0.1 2018��9��19�� ����5:03:01 Administrator Exp $
 */
public class SendCustomMessage {

    protected final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    protected final static String SEND_CUSTOM_URL      = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    public static void main(String[] args) {
        String accessToken = "13_ta0HkB1rCSxPBDHdP9rVMus216kQpkIJP-0bF6FLaEls03g1H0H6xeBVG9bbsRvSfAZ9HGTpU8BQsTRIyHxng_vbstT4_cuKCn_z7st2Y7ytKyuPYuuTY4ts8pVEkwfMJmN9jEh5Hz5ao2MbEVCbAHALCJ";

        List<Article> makeArticleList = makeArticleList(
            "https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=c7fc043a871001e95a311c5dd9671089/e7cd7b899e510fb36b143a44d333c895d0430cd7.jpg",
            "www.baidu.com");
        String makeTextCustomMessage = SendCustomMessage.makeNewsCustomMessage(
            "oaVfts1TDafg3a5WJohO43gH72I0", makeArticleList);
        try {
            SendCustomMessage.sendCustomMessage(accessToken, makeTextCustomMessage);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���Ϳͷ���Ϣ����
     * 
     * @param accessToken �ӿڷ���ƾ֤
     * @param jsonMsg json��ʽ�ͷ���Ϣ
     * @return true|false
     * @throws IOException 
     * @throws HttpException 
     */
    public static boolean sendCustomMessage(String accessToken, String jsonMsg)
                                                                               throws HttpException,
                                                                               IOException {

        System.out.println("��Ϣ���ݣ�{}" + jsonMsg);
        boolean result = false;
        String requestUrl = SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
        // ���Ϳͷ���Ϣ
        String returnXml = HttpUtils.executePostMethod(requestUrl, "UTF-8", jsonMsg);
        if (null != returnXml) {
            System.out.println("�Ѿ����Ϳͷ���Ϣ");
        }
        return result;
    }

    // ��װ�ı��ͷ���Ϣ
    public static String makeTextCustomMessage(String openId, String content) {
        // ����Ϣ�����е�˫���Ž���ת��
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":"
                         + "{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, content);
    }

    /**
     * ��װͼƬ�ͷ���Ϣ
     * @param openId ��ͨ�û�openid
     * @param mediaId ���͵�ͼƬ��ý��ID
     * @return String
     */
    public static String makeImageCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":"
                         + "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * ��װ�����ͷ���Ϣ
     * 
     * @param openId ��ͨ�û�openid
     * @param mediaId ���͵�������ý��ID
     * @return String
     */
    public static String makeVoiceCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":"
                         + "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
	 * ��װͼ�Ŀͷ���Ϣ
	 * 
	 * @param openId
	 *            ��ͨ�û�openid
	 * @param articleList
	 *            ͼ����Ϣ�б�
	 * @return String
	 */
	public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg = String.format(jsonMsg, openId, JSONObject.toJSON(articleList).toString());
        return jsonMsg;
    }

    //��װͼ����Ϣ������
    // ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80������ͼƬ���ӵ�������Ҫ�뿪������д�Ļ��������е�Urlһ��   
    //http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg
    public static List<Article> makeArticleList(String picUrl, String linkUrl) {
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setDescription("�ҵĶ�ά��");
        article.setPicurl(picUrl);
        article.setTitle("����һ�������õ�ͼ����Ϣ\n����");
        article.setUrl(linkUrl);
        articleList.add(article);
        return articleList;
    }
    
    public static List<Article> makeArticleListForCheckOrder(String picUrl, String linkUrl, String title, String desc) {
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setDescription(desc);
        article.setPicurl(picUrl);
        article.setTitle(title);
        article.setUrl(linkUrl);
        articleList.add(article);
        return articleList;
    }

}
