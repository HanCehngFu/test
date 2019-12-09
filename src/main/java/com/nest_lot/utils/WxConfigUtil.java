package com.nest_lot.utils;

public class WxConfigUtil {
	 /*public static String appId = "wxf7c565fd029f0fd8";
	 public static String appSecret = "93e3754966bf5db790bb5b8092fb94bb";
	 public static String xappId="wxf6e2447f64778d6d";
	 public static String xappSecret="43f4d877970786ee74d87b4f49346d4e";
	 public static String token = "zhangtoken";
	 public static String IP = "112.35.79.76";
	 public static String mch_id = "1499271092";
	 public static String mch_key = "GHchaoluo888chaoluogaoheng123456";*/

	public static String appId = "wxc8b0622551705a56";// 服务号的应用号
	public static String appSecret ="e382ee8bac685c47d322e912ee0fee3c";//服务号的应用密码
	public static String xappId="wx929d97664bf5758d";  
	public static String xappSecret="344642fb3e48310a89b4ebb0efe19a5f";
	public static String token = "testghtoken";
	public static String IP = "112.35.45.73";
	public static String mch_id = "1502251381";// 商户号
	public static String mch_key = "GHchaoluo888chaoluogaoheng654321";// API密钥
	public static String notify_url="http://tadmin.nestlot.com/SaleInfo/refundNotify";

	/**
	 * down weixin api
	 */
	// 微信时效性token
	public static String trade_type_js = "JSAPI";
	public static String access_token = "8_qDVlmaqE3aJ1FNdkXdFGnvCfAK-PCyzgOeRldZ3TNzjnGDPRor8vtzCsnWrQzQmkmbvTwm-iyRWISzllF0lDkApQLrciWm3NUUrlzK87JbKV8jA84ILI5uISjVvIfSOCmoswSULH20yJgy6KTQPjAGAZWV";
	public static String ticket = "HoagFKDcsGMVCIY2vOjf9qVlwyHfT8y3UlP6cg4RUChU0DBwu-L7wEYJ9H0mBsKtfOodwGLKDoSrIzWpw2qGpQ";
	public static final String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
	// public static String trade_type_js = "JSAPI";
	// public static String access_token =
	// "8_qDVlmaqE3aJ1FNdkXdFGnvCfAK-PCyzgOeRldZ3TNzjnGDPRor8vtzCsnWrQzQmkmbvTwm-iyRWISzllF0lDkApQLrciWm3NUUrlzK87JbKV8jA84ILI5uISjVvIfSOCmoswSULH20yJgy6KTQPjAGAZWV";
	// public static String ticket =
	// "HoagFKDcsGMVCIY2vOjf9qVlwyHfT8y3UlP6cg4RUChU0DBwu-L7wEYJ9H0mBsKtfOodwGLKDoSrIzWpw2qGpQ";
	// public static final String ticket_url =
	// "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
	//

	// 对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";

	// 微信转账
	public final static String transfers_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	// 微信退款
	public final static String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
}
