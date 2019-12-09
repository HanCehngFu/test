package com.nest_lot.utils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.nest_lot.config.AppPayConfig;
import com.nest_lot.constant.AlipayRefund;
import com.nest_lot.constant.ResultBase;
import com.nest_lot.constant.ResultEnum;
import com.nest_lot.constant.WxRefund;

@Component
public class AlipayUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlipayUtil.class);
	// 编码级别
	private static String CHARSET = "UTF-8";

	/**
	 * 支付宝退款
	 * 
	 * @param aliRefundTO
	 * @return
	 */
	public static ResultBase refundOrder(AlipayRefund aliRefundTO) {
		ResultBase resultBase = new ResultBase();
		LOGGER.debug("开始调用支付宝加密******************************************************");
		// 实例化客户端
		JSONObject jsonObject = new JSONObject();
		AlipayClient alipayClient = new DefaultAlipayClient(AppPayConfig.gatewayUrl, AppPayConfig.app_id, AppPayConfig.merchant_private_key, "json", CHARSET,
				AppPayConfig.alipay_public_key, "RSA2");
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		jsonObject.put("out_trade_no", aliRefundTO.getOut_biz_no());
		jsonObject.put("refund_amount", aliRefundTO.getAmount());
		jsonObject.put("refund_reason", aliRefundTO.getRemark());
		request.setBizContent(jsonObject.toJSONString());// 2个都可以，这个参数的顺序 不影响退款
		try {
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				LOGGER.error("支付宝退款成功！");
				return resultBase.success();
			} else {
				LOGGER.error("支付宝退款错误！");
				return resultBase.fail(ResultEnum.ERROR.getCode(), response.getSubMsg());
			}
		} catch (Exception e) {
			LOGGER.error("支付宝退款错误！", ResultEnum.ERROR.getMessage());
			return resultBase.fail(Integer.parseInt(e.getMessage()), ResultEnum.ERROR.getMessage());
		}
	}

	/**
	 * 支付宝转账
	 * 
	 * @param alipayRefund
	 * @return
	 */
	public ResultBase transferAccount(AlipayRefund alipayRefund) {
		ResultBase resultBase = new ResultBase();
		JSONObject jsonObject = new JSONObject();
		AlipayClient alipayClient = new DefaultAlipayClient(AppPayConfig.gatewayUrl, AppPayConfig.app_id, AppPayConfig.merchant_private_key, "json", "GBK",
				AppPayConfig.alipay_public_key, "RSA2");
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		jsonObject.put("out_biz_no", alipayRefund.getOut_biz_no());
		jsonObject.put("payee_type", alipayRefund.getPayee_type());
		jsonObject.put("payee_account", alipayRefund.getPayee_account());
		jsonObject.put("amount", alipayRefund.getAmount());
		jsonObject.put("payee_real_name", alipayRefund.getPayee_real_name());
		jsonObject.put("remark", alipayRefund.getRemark());
		request.setBizContent(jsonObject.toJSONString());
		AlipayFundTransToaccountTransferResponse response;
		try {
			response = alipayClient.execute(request);
			if (response.isSuccess()) {
				LOGGER.debug("-------------------------转账成功" + alipayRefund.getOut_biz_no());

				return resultBase.success();
			} else {
				LOGGER.debug("-------------------------转账失败" + alipayRefund.getOut_biz_no());
				return resultBase.fail(ResultEnum.ERROR.getCode(), response.getSubMsg());
			}
		} catch (AlipayApiException e) {
			LOGGER.debug("-------------------------转账失败" + e.getErrMsg());
			return resultBase.fail(Integer.parseInt(e.getErrCode()), ResultEnum.ERROR.getMessage());
		}
	}

	/**
	 * 微信转账
	 * 
	 * @param alipayRefund
	 * @return
	 */
	public ResultBase wxTransferAccount(AlipayRefund alipayRefund,String appId) {
		ResultBase resultBase = new ResultBase();
		String nonce_str = Tools.getUUID(); // 随机字符串
		String partner_trade_no = alipayRefund.getOut_biz_no();
		String openid = alipayRefund.getPayee_account(); // 微信账户唯一标识
		String check_name = "NO_CHECK"; // 不校验姓名
		String re_user_name = alipayRefund.getPayee_real_name();
		int amount = DoubleUtils.mul(Double.parseDouble(alipayRefund.getAmount()), 100d).intValue();
		String desc = alipayRefund.getRemark();
		String spbill_create_ip = WxConfigUtil.IP;
		SortedMap<Object, Object> map = new TreeMap(); // sign
		map.put("mch_appid", appId);
		map.put("mchid", WxConfigUtil.mch_id);
		map.put("nonce_str", nonce_str);
		map.put("partner_trade_no", partner_trade_no);
		map.put("openid", openid);
		map.put("check_name", check_name);
		map.put("re_user_name", re_user_name);
		map.put("amount", amount);
		map.put("desc", desc);
		map.put("spbill_create_ip", spbill_create_ip);
		String sign = Tools.creatSign(map, WxConfigUtil.mch_key);
		map.put("sign", sign);

		String requestXml = PayCommonUtil.getRequestXml(map);
		String result = PayCommonUtil.httpsRequest(WxConfigUtil.transfers_url, "POST", requestXml);

		try {
			Map<String, String> mapResult = PayCommonUtil.xmlToMap(result);
			String return_code = mapResult.get("result_code");
			String return_msg = mapResult.get("return_msg");
			if (return_code.equals("SUCCESS") && return_code != null) {
				LOGGER.debug("-------------------------微信转账成功" + partner_trade_no);
				return resultBase.success();
			} else {
				LOGGER.debug("-------------------------转账失败" + partner_trade_no);
				return resultBase.fail(ResultEnum.ERROR.getCode(), return_msg);
			}
		}

		catch (Exception e) {
			LOGGER.debug("-------------------------转账失败" + e.getMessage());
			return resultBase.fail(Integer.parseInt(e.getMessage()), ResultEnum.ERROR.getMessage());
		}

	}

	/**
	 * 微信退款
	 * 
	 * @param alipayRefund
	 * @return
	 */
	public ResultBase wxRefundAccount(WxRefund wxRefund,String appId) {
		ResultBase resultBase = new ResultBase();
		int total_fee = DoubleUtils.mul(wxRefund.getTotal_fee(), 100d).intValue();
		int refund_fee = DoubleUtils.mul(wxRefund.getRefund_fee(), 100d).intValue();
		String refund_desc = wxRefund.getRefund_desc();
		SortedMap<Object, Object> map = new TreeMap(); // sign
		map.put("appid", appId);
		map.put("mch_id", WxConfigUtil.mch_id);
		map.put("nonce_str", Tools.getUUID());

		map.put("out_trade_no", wxRefund.getOut_trade_no());
		map.put("out_refund_no", Tools.getUUID());
		map.put("total_fee", total_fee);
		map.put("refund_fee", refund_fee);
		map.put("refund_desc", refund_desc);
		map.put("notify_url", WxConfigUtil.notify_url);
		String sign = Tools.creatSign(map, WxConfigUtil.mch_key);
		LOGGER.debug("11111111111111111"+WxConfigUtil.mch_key);
		map.put("sign", sign);

		String requestXml = PayCommonUtil.getRequestXml(map);
		String result = PayCommonUtil.httpsRequest(WxConfigUtil.refund_url, "POST", requestXml);

		try {
			Map<String, String> mapResult = PayCommonUtil.xmlToMap(result);
			String return_code = mapResult.get("result_code");
			System.out.println("88888888888"+return_code);
			String return_msg = mapResult.get("return_msg");
			System.out.println("99999999999"+return_msg);
			if (return_code.equals("SUCCESS") && return_code != null) {
				System.out.println("-------------------------微信退款成功" + wxRefund.getOut_trade_no());
				return resultBase.success();
			} else {
				System.out.println("22222222222222222222");
				System.out.println("-------------------------退款失败" + wxRefund.getOut_trade_no());
				System.out.println("33333333333333333333"+wxRefund.getOut_trade_no());
				return resultBase.fail(ResultEnum.ERROR.getCode(), return_msg);
			}
		}

		catch (Exception e) {
			System.out.println("-------------------------退款失败" + e.getMessage());
			System.out.println("444444444444444444"+e.getMessage());
			return resultBase.fail(500, ResultEnum.ERROR.getMessage());
		}

	}

	/*
	 * public ResultBase toBillList() throws AlipayApiException{
	 * 
	 * ResultBase resultBase = new ResultBase(); AlipayClient alipayClient = new
	 * DefaultAlipayClient(AppPayConfig.gatewayUrl,
	 * AppPayConfig.app_id,AppPayConfig.merchant_private_key,"json","utf-8",
	 * AppPayConfig.alipay_public_key,"RSA2");
	 * AlipayDataDataserviceBillDownloadurlQueryRequest request = new
	 * AlipayDataDataserviceBillDownloadurlQueryRequest(); request.setBizContent("{"
	 * + "\"bill_type\":\"trade\"," + "\"bill_date\":\"2016-04-05\"" + "  }");
	 * AlipayDataDataserviceBillDownloadurlQueryResponse response =
	 * alipayClient.execute(request); if(response.isSuccess()){
	 * LOGGER.debug("调用成功"); } else { LOGGER.debug("调用失败"); } return
	 * resultBase.success(response); }
	 */

}
