package com.onway.core.service.push;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.onway.platform.common.configration.ConfigrationFactory;

@Component("MessagePushComponent")
public class MessagePushComponentImp implements MessagePushComponent {

	private final String appId = ConfigrationFactory.getConfigration()
			.getPropertyValue("PUSH_APPID");
	private final String appKey = ConfigrationFactory.getConfigration()
			.getPropertyValue("PUSH_APPKEY");
	private final String masterSecret = ConfigrationFactory.getConfigration()
			.getPropertyValue("PUSH_MASTERSECRET");
	private final String host = ConfigrationFactory.getConfigration()
			.getPropertyValue("HOST");

	@Override
	public void pushtoSingle(String alias, String title, String text)
			throws Exception {
		IGtPush push = new IGtPush(host, appKey, masterSecret);
		TransmissionTemplate template = getTemplate(title, text);

		// NotificationTemplate notificationTemplate =
		// notificationTemplateDemo(title, text);

		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		// 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
		message.setPushNetWorkType(0);
		Target target = new Target();
		target.setAppId(appId);
		target.setAlias(alias);
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

	@Override
	public void pushtoAPP(String title, String text) throws Exception {

		IGtPush push = new IGtPush(host, appKey, masterSecret);

		TransmissionTemplate template = getTemplate(title, text);
		AppMessage message = new AppMessage();
		message.setData(template);

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		// 推送给App的目标用户需要满足的条件
		AppConditions cdt = new AppConditions();
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);
		// 手机类型
		List<String> phoneTypeList = new ArrayList<String>();
		// 省份
		List<String> provinceList = new ArrayList<String>();
		// 自定义tag
		List<String> tagList = new ArrayList<String>();

		cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
		cdt.addCondition(AppConditions.REGION, provinceList);
		cdt.addCondition(AppConditions.TAG, tagList);
		message.setConditions(cdt);

		IPushResult ret = push.pushMessageToApp(message, "任务别名_" + title);
		if (ret != null) {
			System.out.println(ret.getResponse().toString());
		} else {
			System.out.println("服务器响应异常");
		}
	}

	public LinkTemplate linkTemplateDemo(String title, String text) {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title);
		style.setText(text);
		// 配置通知栏图标
		style.setLogo("icon.png");
		// 配置通知栏网络图标
		style.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		style.setRing(true);
		style.setVibrate(true);
		style.setClearable(true);
		template.setStyle(style);

		// 设置打开的网址地址
		// template.setUrl("http://www.baidu.com");

		return template;
	}

	public NotificationTemplate notificationTemplateDemo(String title,
			String text) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);

		Style0 style = new Style0();
		// 设置通知栏标题与内容
		style.setTitle(title);
		style.setText(text);
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

	public TransmissionTemplate getTemplate(String title, String text) {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appKey);
		template.setTransmissionContent(text);
		template.setTransmissionType(2);
		APNPayload payload = new APNPayload();
		// payload.setAutoBadge("+1");
		payload.setContentAvailable(1);
		payload.setSound("default");
		payload.setCategory("$由客户端定义");
		// 简单模式APNPayload.SimpleMsg
		// payload.setAlertMsg(new APNPayload.SimpleAlertMsg("hello"));
		// 字典模式使用下者
		payload.setAlertMsg(getDictionaryAlertMsg(title, text));
		template.setAPNInfo(payload);
		return template;
	}

	private APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title,
			String text) {
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		alertMsg.setBody(text);
		// alertMsg.setActionLocKey("ActionLockey");
		// alertMsg.setLocKey("LocKey");
		// alertMsg.addLocArg("loc-args");
		// alertMsg.setLaunchImage("launch-image");
		// IOS8.2以上版本支持
		alertMsg.setTitle(title);
		// alertMsg.setTitleLocKey("TitleLocKey");
		// alertMsg.addTitleLocArg("TitleLocArg");
		return alertMsg;
	}

}
