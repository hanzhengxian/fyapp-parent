package com.onway.core.service.push;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class MessagePushTest {

	// @Resource
	// private SysConfigCacheManager sysConfigCacheManager;

	/*
	 * private final String appId =
	 * sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.PUSH_APPID);
	 * private final String appKey =
	 * sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.PUSH_APPKEY);
	 * private final String masterSecret =
	 * sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum
	 * .PUSH_MASTERSECRET); private final static String host =
	 * "http://sdk.open.api.igexin.com/apiex.htm";
	 */

	static String appId = "AHzBdPlyHjAXNXCsZxtI87";
	static String appKey = "livqTqWtpI6JDTfogldB62";
	static String masterSecret = "24WMiACKje6wLdFKnVLpX8";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";

	static String Alias = "1001171204000096";

//	public static void main(String[] args) throws Exception {
//		IGtPush push = new IGtPush(host, appKey, masterSecret);
//		LinkTemplate template = linkTemplateDemo();
//		SingleMessage message = new SingleMessage();
//		message.setOffline(true);
//		// 离线有效时间，单位为毫秒，可选
//		message.setOfflineExpireTime(24 * 3600 * 1000);
//		message.setData(template);
//		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
//		message.setPushNetWorkType(0);
//		Target target = new Target();
//		target.setAppId(appId);
//		// target.setClientId(CID);
//		target.setAlias(Alias);
//		IPushResult ret = null;
//		try {
//			ret = push.pushMessageToSingle(message, target);
//		} catch (RequestException e) {
//			e.printStackTrace();
//			ret = push.pushMessageToSingle(message, target, e.getRequestId());
//		}
//		if (ret != null) {
//			System.out.println(ret.getResponse().toString());
//		} else {
//			System.out.println("服务器响应异常");
//		}
//	}
//
//	public static LinkTemplate linkTemplateDemo() {
//		LinkTemplate template = new LinkTemplate();
//		// 设置APPID与APPKEY
//		template.setAppId(appId);
//		template.setAppkey(appKey);
//
//		Style0 style = new Style0();
//		// 设置通知栏标题与内容
//		style.setTitle("请输入通知栏标题");
//		style.setText("请输入通知栏内容");
//		// 配置通知栏图标
//		style.setLogo("icon.png");
//		// 配置通知栏网络图标
//		style.setLogoUrl("");
//		// 设置通知是否响铃，震动，或者可清除
//		style.setRing(true);
//		style.setVibrate(true);
//		style.setClearable(true);
//		template.setStyle(style);
//
//		// 设置打开的网址地址
//		template.setUrl("http://www.baidu.com");
//
//		return template;
//	}

	public static void main(String[] args) throws Exception {
		// 配置返回每个用户返回用户状态，可选
		System.setProperty("gexin_pushList_needDetails", "true");
		// 配置返回每个别名及其对应cid的用户状态，可选
		// System.setProperty("gexin_pushList_needAliasDetails", "true");
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		// 通知透传模板
		NotificationTemplate template = notificationTemplateDemo();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		// target.setClientId(CID);
		target.setAlias(Alias);
		IPushResult ret = null;
		try {
			ret = push.pushMessageToSingle(message, target);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
	}

	public static NotificationTemplate notificationTemplateDemo() {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle("请输入通知栏标题");
		style.setText("请输入通知栏内容");
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);
		template.setTransmissionContent("请输入您要透传的内容");
		return template;
	}

}
