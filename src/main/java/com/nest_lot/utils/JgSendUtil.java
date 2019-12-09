package com.nest_lot.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nest_lot.constant.ResultBase;
import com.nest_lot.constant.ResultEnum;
import com.nest_lot.model.CustomMessage;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageClient;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JgSendUtil {
	private static final Logger log = LoggerFactory.getLogger(JgSendUtil.class);
	private static String masterSecret = "d32e9309e7f74f49e72453be";
	private static String appKey = "ff125c8719cb40a1f0341daf";

	private static MessageClient messageClient = new MessageClient(appKey, masterSecret);
	/*
	 * setPlatform(Platform.all()) //设置所有平台
	 * 
	 * setPlatform(Platform.android())//设置android
	 * 
	 * setPlatform(Platform.android_ios())//设置Android和iOS
	 * 
	 * setPlatform(Platform.ios())//设置iOS
	 */

	/*
	 * setAudience(Audience.all())设置所有受众
	 * 
	 * setAudience(Audience.tag("tag1"，"tag2"))//设置tag为tag1，tag2的受众，群发
	 * 
	 * setAudience(AudienceTarget.alias("alias1", "alias2"))
	 * //设置别名alias为alias1，alias2的受众，单发
	 */

	/**
	 * alias为空默认全部用户 不为空就单个，多个以逗号形式分割
	 * 
	 * @param alias
	 * @param alert
	 * @param message
	 * @return
	 */
	// public static ResultBase toJpSend(String alias, String alert, String
	// message)
	// {
	// ResultBase resultBase = new ResultBase();
	// PushPayload payload = null;
	// if (alias == "")
	// {
	// System.out.println("对所有的用户推送信息");
	// payload = buildPushObject_all_all_alert(alert, message);
	// }
	// else
	// {
	// payload = buildPushObject_android_ios_alias_alert(Platform.android_ios(),
	// alias, alert, message);
	// }
	// PushResult result = push(payload);
	// if (result != null && result.isResultOK())
	// {
	// resultBase.success();
	// }
	// else
	// {
	// resultBase.fail(ResultEnum.ERR_1303.getCode(),
	// ResultEnum.ERR_1303.getMessage());
	// }
	// return resultBase;
	// }

	/**
	 * 别名推送 生成极光推送对象PushPayload（采用java SDK）
	 * 
	 * @param alias
	 *            //别名
	 * @param alert
	 *            //弹出消息
	 * @param message
	 *            内容
	 * @param type
	 *            内容 //安卓还是ios
	 * @return PushPayload
	 * 
	 */
	public static PushPayload buildPushObject_android_ios_alias_alert(Platform type, String alias, String alert, String message) {
		return PushPayload.newBuilder().setPlatform(type).setAudience(Audience.alias(alias))
				.setNotification(Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setStyle(1).build())
						.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).setBadge(5).setSound("happy").build()).build())
				.setMessage(Message.content(message)).setOptions(Options.newBuilder().setApnsProduction(false)// true-推送生产环境
																												// false-推送开发环境（测试使用参数）
						.setTimeToLive(90)// 消息在JPush服务器的失效时间（测试使用参数）
						.build())
				.build();
	}

	/**
	 * 所有用户
	 * 
	 * @return
	 */
	// public static PushPayload buildPushObject_all_all_alert(String alert,
	// String msgContent)
	// {
	// return
	// PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
	// .setNotification(Notification.newBuilder()
	// .addPlatformNotification(AndroidNotification.newBuilder().setAlert(alert).setStyle(1).build())
	// .addPlatformNotification(
	// IosNotification.newBuilder().setAlert(alert).setBadge(5).setSound("happy").build())
	// .build())
	// .setMessage(Message.content(msgContent))
	// .setOptions(Options.newBuilder().setApnsProduction(false)// true-推送生产环境
	// // false-推送开发环境（测试使用参数）
	// .setTimeToLive(90)// 消息在JPush服务器的失效时间（测试使用参数）
	// .build())
	// .build();
	// }

	/**
	 * 标签推送
	 */
	public static PushPayload buildPushObject_android_and_iosByTag(String tag, String title, String content) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.tag(tag))
				.setNotification(Notification.newBuilder().setAlert(content).addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
						.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra(title, content).build()).build())
				.build();
	}

	/**
	 * 极光推送方法(采用java SDK)
	 * 
	 * @return PushResult
	 */
	public static PushResult push(PushPayload payload) {
		ClientConfig clientConfig = ClientConfig.getInstance();
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, null, clientConfig);
		try {

			return jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
			return null;
		} catch (APIRequestException e) {
			log.error("Error response from JPush server. Should review and fix it. ", e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
			log.info("Msg ID: " + e.getMsgId());
			return null;
		}
	}

	/**
	 * 推送文本消息
	 *
	 * @param targetId
	 *            目标id single填username;group填Groupid;chatroom填chatroomid
	 * @param text
	 *            消息内容
	 * @param extra
	 *            选填的json对象 开发者可以自定义extras里面的key value
	 * @param targetType
	 *            发送目标类型 single - 个人;group - 群组;chatroom - 聊天室
	 * @return
	 */
	// public static boolean sendTextMessage(String targetId, String text,
	// Map<String, String> extra)
	// {
	// try
	// {
	// // 构建文本消息体
	// MessageBody.Builder builder = MessageBody.newBuilder().setText(text);
	// if (extra != null && !extra.isEmpty())
	// {
	// builder.addExtras(extra);
	// }
	// MessageBody messageBody = builder.build();
	//
	// // 构建消息载荷
	// MessagePayload messagePayload =
	// MessagePayload.newBuilder().setVersion(1).setTargetType("single")
	// .setTargetId(targetId).setFromType("admin").setFromId("NestlotMessageManger")
	// .setMessageType(MessageType.TEXT).setMessageBody(messageBody).build();
	//
	// SendMessageResult sendMessageResult =
	// messageClient.sendMessage(messagePayload);
	// log.info("发送文本消息响应: " + sendMessageResult.toString());
	// return true;
	// }
	// catch (APIConnectionException e)
	// {
	// log.error("Connection error. Should retry later. ", e);
	// }
	// catch (APIRequestException e)
	// {
	// log.error("Error response from JPush server. Should review and fix it. ",
	// e);
	// log.info("HTTP Status: " + e.getStatus());
	// log.info("Error Message: " + e.getMessage());
	// }
	// return false;
	// }

	/**
	 * 推送自定义消息
	 *
	 * @param targetId
	 * @param customMessage
	 * @param targetType
	 * @return
	 */
	// public static boolean sendCustomMessage(String targetId, CustomMessage
	// customMessage)
	// {
	// try
	// {
	// // 构建自定义消息
	// MessageBody messageBody = MessageBody.text(customMessage.toString());
	// // 构建消息载荷
	// MessagePayload messagePayload =
	// MessagePayload.newBuilder().setVersion(1).setTargetType("single")
	// .setTargetId(targetId).setFromType("admin").setFromId("NestlotMessageManger")
	// .setMessageType(MessageType.CUSTOM).setMessageBody(messageBody).build();
	// SendMessageResult sendMessageResult =
	// messageClient.sendMessage(messagePayload);
	// log.info("发送自定义消息响应: " + sendMessageResult.toString());
	// return true;
	// }
	// catch (APIConnectionException e)
	// {
	// log.error("Connection error. Should retry later. ", e);
	// }
	// catch (APIRequestException e)
	// {
	// log.error("Error response from JPush server. Should review and fix it. ",
	// e);
	// log.info("HTTP Status: " + e.getStatus());
	// log.info("Error Message: " + e.getMessage());
	// }
	// return false;
	// }
}
