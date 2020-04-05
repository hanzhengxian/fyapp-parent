package com.onway.utils.sendMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.HttpUtils;

/**
 * 发送客服消息工具 
 * 
 * @author yugang.ni
 * @version $Id: SendCustomMessage.java, v 0.1 2018年9月19日 下午5:03:01 Administrator Exp $
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
     * 发送客服消息方法
     * 
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式客服消息
     * @return true|false
     * @throws IOException 
     * @throws HttpException 
     */
    public static boolean sendCustomMessage(String accessToken, String jsonMsg)
                                                                               throws HttpException,
                                                                               IOException {

        System.out.println("消息内容：{}" + jsonMsg);
        boolean result = false;
        String requestUrl = SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        String returnXml = HttpUtils.executePostMethod(requestUrl, "UTF-8", jsonMsg);
        if (null != returnXml) {
            System.out.println("已经发送客服消息");
        }
        return result;
    }

    // 组装文本客服消息
    public static String makeTextCustomMessage(String openId, String content) {
        // 对消息内容中的双引号进行转义
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":"
                         + "{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, content);
    }

    /**
     * 组装图片客服消息
     * @param openId 普通用户openid
     * @param mediaId 发送的图片的媒体ID
     * @return String
     */
    public static String makeImageCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":"
                         + "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 组装语音客服消息
     * 
     * @param openId 普通用户openid
     * @param mediaId 发送的语音的媒体ID
     * @return String
     */
    public static String makeVoiceCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":"
                         + "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
	 * 组装图文客服消息
	 * 
	 * @param openId
	 *            普通用户openid
	 * @param articleList
	 *            图文消息列表
	 * @return String
	 */
	public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg = String.format(jsonMsg, openId, JSONObject.toJSON(articleList).toString());
        return jsonMsg;
    }

    //组装图文消息的文章
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致   
    //http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg
    public static List<Article> makeArticleList(String picUrl, String linkUrl) {
        List<Article> articleList = new ArrayList<Article>();
        Article article = new Article();
        article.setDescription("我的二维码");
        article.setPicurl(picUrl);
        article.setTitle("这是一条测试用的图文消息\n引言");
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
