package com.nest_lot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

@Configuration
public class SmsConfig {

	private Logger logger = LoggerFactory.getLogger(SmsConfig.class);

	// 产品名称:云通信短信API产品,开发者无需替换
	@Value("${AliyunMessage.product}")
	private String product;

	// 产品域名,开发者无需替换
	@Value("${AliyunMessage.domain}")
	private String domain;

	// 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	@Value("${AliyunMessage.accessKeyId}")
	private String accessKeyId;

	@Value("${AliyunMessage.accessKeySecret}")
	private String accessKeySecret;

	// 短信签名
	@Value("${AliyunMessage.signName}")
	private String signName;

	/**
	 * 优惠券发送短信验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean sendValidateCode(String phoneNumber, String time, String messageTemplate) {
		if (null == phoneNumber && "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("优惠卷发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("优惠卷发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 退订发送短信验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean returnValidateCode(String adress, String phoneNumber, String time, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		String[] str = adress.split("/");
		if (str != null && str.length > 0 && null != str[str.length - 1] && !"".equals(str[str.length - 1])) {
			json.put("address", str[str.length - 1]);
		} else {
			if (adress.length() > 21) {
				json.put("address", adress.substring(adress.length() - 20, adress.length()));
			} else {
				json.put("address", adress);
			}
		}

		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("退单发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("退单发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 押金退款失败发送短信验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean returnPledgeCode(String phoneNumber, String time, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("押金退款失败发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("押金退款失败发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 活动发布成功发送短信验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean returnActivityeSuccessCode(String phoneNumber, String time, String title, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		json.put("title", title);// 根据阿里云短信模板变化

		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("活动成功----发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("活动成功------发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 活动发布失败发送短信验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean returnActivityeFailCode(String phoneNumber, String time, String title, String message, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		json.put("title", title);// 根据阿里云短信模板变化
		json.put("message", message);// 根据阿里云短信模板变化
		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("活动失败----发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("活动失败------发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 导师发布失败发送短信验证码
	 *
	 * @param phoneNumber //以逗号分隔。可以一次发一千个
	 * 
	 * @param validateCode
	 * 
	 * @param messageTemplate
	 * 
	 * @return
	 */
	public boolean returnTeachFailCode(String phoneNumber, String time, String message, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		json.put("message", message);// 根据阿里云短信模板变化

		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("导师失败----发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("导师失败------发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * 导师发布成功发送短信验证码
	 *
	 * @param phoneNumber //以逗号分隔。可以一次发一千个
	 * 
	 * @param validateCode
	 * 
	 * @param messageTemplate
	 * 
	 * @return
	 */
	public boolean returnTeachSuccessCode(String phoneNumber, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("导师成功----发送成功: " + phoneNumber);
				return true;
			} else {
				logger.error("导师成功------发送失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 发送库存验证码
	 *
	 * @param phoneNumber
	 *            //以逗号分隔。可以一次发一千个
	 * @param validateCode
	 * @param messageTemplate
	 * @return
	 */
	public boolean sendKucunCode(String nestInUsedPhone, String nestLotName, String num, String needNum, String messageTemplate) {
		if (null == nestInUsedPhone || "".equals(nestInUsedPhone)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("lotname", nestLotName);// 根据阿里云短信模板变化
		json.put("num", num);
		json.put("needNum", needNum);
		try {
			SendSmsResponse smsResponse = sendSms(nestInUsedPhone, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println(nestInUsedPhone + "库存提醒发送成功: " + json.toJSONString());
				return true;
			} else {
				logger.error("库存提醒发送失败: " + json.toJSONString() + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private SendSmsResponse sendSms(String phoneNumber, String message, String templateCode) throws ClientException {
		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(phoneNumber);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(message);
		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		// request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		return sendSmsResponse;
	}

	/**
	 * 重单退款发送短信
	 * 
	 * @param phoneNumber
	 *            手机号
	 * @param time
	 *            时间
	 * @param address
	 *            地址
	 * @param messageTemplate
	 *            短信模板
	 * @return
	 */
	public boolean returnPledgeCode2(String phoneNumber, String time, String address, String messageTemplate) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			System.out.println("-------------------------------------------->电话号码为空无法发送");
			return false;
		}
		JSONObject json = new JSONObject();
		json.put("time", time);// 根据阿里云短信模板变化
		json.put("address", address);
		try {
			SendSmsResponse smsResponse = sendSms(phoneNumber, json.toJSONString(), messageTemplate);
			if ("OK".equals(smsResponse.getCode())) {
				System.out.println("重单退款发送短信成功: " + phoneNumber);
				return true;
			} else {
				logger.error("重单退款发送短信成功失败: " + phoneNumber + "(Code: " + smsResponse.getCode() + " ,Message: " + smsResponse.getMessage() + " ,BizId: "
						+ smsResponse.getBizId());
				return false;
			}
		} catch (ClientException e) {
			logger.error("AliyunMessage客户端异常");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
