/*
 * package com.nest_lot.config;
 * 
 * import java.util.ArrayList; import java.util.HashMap; import java.util.List;
 * import java.util.Map; import java.util.Set; import
 * org.apache.commons.collections.CollectionUtils; import
 * org.apache.poi.ss.usermodel.DateUtil; import org.slf4j.Logger; import
 * org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.factory.annotation.Qualifier; import
 * org.springframework.jms.core.JmsTemplate; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Component; import
 * com.nest_lot.constant.AlipayRefund; import com.nest_lot.constant.ResultBase;
 * import com.nest_lot.constant.ResultEnum; import
 * com.nest_lot.constant.WxRefund; import com.nest_lot.dao.entity.NestlotUsed;
 * import com.nest_lot.dao.entity.PledgeInfo; import
 * com.nest_lot.dao.entity.PledgeWithdrawInfo; import
 * com.nest_lot.dao.entity.SaleInfo; import
 * com.nest_lot.dao.entity.SaleUserInfo; import
 * com.nest_lot.dao.entity.StorehouseInfo; import
 * com.nest_lot.dao.entity.UserInfoReal; import
 * com.nest_lot.dao.mapper.PledgeInfoMapper; import
 * com.nest_lot.dao.mapper.PledgeWithdrawInfoMapper; import
 * com.nest_lot.dao.mapper.SaleInfoMapper; import
 * com.nest_lot.dao.mapper.SaleUserInfoMapper; import
 * com.nest_lot.dao.mapper.UserInfoRealMapper; import
 * com.nest_lot.model.CustomMessage; import com.nest_lot.service.NestUseService;
 * import com.nest_lot.service.StorehouseService; import
 * com.nest_lot.utils.AlipayUtil; import com.nest_lot.utils.DateUtils; import
 * com.nest_lot.utils.Tools; import com.nest_lot.utils.WxConfigUtil; import
 * com.qiniu.util.StringUtils;
 * 
 * @Component public class TaskConfig { private static final Logger LOGGER =
 * LoggerFactory.getLogger(TaskConfig.class);
 * 
 * @Autowired private NestUseService nestLotUsefoService;
 * 
 * @Autowired private SmsConfig smsConfig;
 * 
 * @Autowired private StorehouseService storehouseService;
 * 
 * @Autowired private AlipayUtil alipayUtil;
 * 
 * @Autowired private PledgeWithdrawInfoMapper pledgeWithdrawInfoMapper;
 * 
 * @Autowired private PledgeInfoMapper pledgeInfoMapper;
 * 
 * @Autowired private SaleInfoMapper saleInfoMapper;
 * 
 * @Autowired private SaleUserInfoMapper saleUserInfoMapper;
 * 
 * @Autowired private UserInfoRealMapper userInfoRealMapper;
 * 
 * @Autowired
 * 
 * @Qualifier("firstJmsTemplate") private JmsTemplate jmsTemplate;
 * 
 *//**
	 * 保洁房屋库存定时器
	 */
/*
 * @Scheduled(cron = "0 0 10 * * ?") public void scheduled() { List<NestlotUsed>
 * lUseds = nestLotUsefoService.queryByListId(""); Map<String, List<String>> map
 * = new HashMap<>(); Map<String, List<String>> mapMin = new HashMap<>(); for
 * (NestlotUsed nestlotUsed : lUseds) {
 * LOGGER.info("----->com.nest_lot.config.TaskConfig.scheduled()----->start");
 * Set<String> set = map.keySet(); if (nestlotUsed.getNumberMin() == null) {
 * nestlotUsed.setNumberMin(5); } if (nestlotUsed.getNumber() <=
 * nestlotUsed.getNumberMin()) { if (set.size() <= 0) { List<String> list = new
 * ArrayList<>(); // 目前数量 List<String> listMin = new ArrayList<>(); // 最低数量
 * list.add(nestlotUsed.getNiName() + ":" + nestlotUsed.getNumber());
 * listMin.add(nestlotUsed.getNiName() + ":" + nestlotUsed.getNumberMin());
 * map.put(nestlotUsed.getLotName(), list); mapMin.put(nestlotUsed.getLotName(),
 * listMin); } else { Boolean chosse = false; String key2 = ""; for (String key
 * : set) { if (key.indexOf(nestlotUsed.getLotName()) != -1) { chosse = true;
 * key2 = key; } } if (chosse) { List<String> list = map.get(key2); List<String>
 * listMin = mapMin.get(key2); list.add(nestlotUsed.getNiName() + ":" +
 * nestlotUsed.getNumber()); listMin.add(nestlotUsed.getNiName() + ":" +
 * nestlotUsed.getNumberMin()); } else { List<String> list = new ArrayList<>();
 * List<String> listMin = new ArrayList<>(); list.add(nestlotUsed.getNiName() +
 * ":" + nestlotUsed.getNumber()); listMin.add(nestlotUsed.getNiName() + ":" +
 * nestlotUsed.getNumberMin()); map.put(nestlotUsed.getLotName(), list);
 * mapMin.put(nestlotUsed.getLotName(), listMin); } }
 * 
 * } }
 * 
 * Set<String> set = map.keySet(); for (String key : set) { List<String> list =
 * map.get(key); List<String> listMin = mapMin.get(key); StringBuffer buffer =
 * new StringBuffer(); StringBuffer bufferMin = new StringBuffer(); for (String
 * temp : list) { buffer.append(temp + ","); } for (String temp1 : listMin) {
 * bufferMin.append(temp1 + ","); } List<StorehouseInfo> infos =
 * storehouseService.queryByListId(""); if (CollectionUtils.isEmpty(infos)) {
 * return; } StringBuffer buffer2 = new StringBuffer(); for (StorehouseInfo
 * temp2 : infos) { buffer2.append(temp2.getStorehousePhone() + ","); } //
 * LOGGER.info(key+":"+buffer.toString()+"保持数量"+bufferMin.toString()+"以上");
 * smsConfig.sendKucunCode(buffer2.toString(), key, buffer.toString(),
 * bufferMin.toString(), ResultEnum.SMS_1302.getMessage()); }
 * LOGGER.info("----->com.nest_lot.config.TaskConfig.scheduled()----->end"); }
 * 
 *//**
	 * 提现定时器
	 */
/*
 * 
 * @Scheduled(cron = "0/5 * *  * * ?")
 * 
 * @Scheduled(cron="0 0 14 * * ?") public void transferAccount() {
 * List<RemainingSumRecord> list = recordMapper.queryByreansTransfer("3", "2");
 * // 3提现 // 2已审核 if (!CollectionUtils.isEmpty(list)) { for (RemainingSumRecord
 * temp : list) { ResultBase resultBase = new ResultBase(); CustomMessage
 * customMessage = new CustomMessage(); customMessage.setType(""); AlipayRefund
 * alipayRefun = new AlipayRefund();
 * alipayRefun.setAmount(temp.getRsrTradeSum().toString());
 * alipayRefun.setOut_biz_no(temp.getRsrUuid());
 * 
 * if (temp.getRsrAlipayName() != null && !temp.getRsrAlipayName().equals("")) {
 * // 支付宝转账 alipayRefun.setPayee_account(temp.getRsrAlipayAccount());
 * alipayRefun.setPayee_real_name(temp.getRsrAlipayName());
 * alipayRefun.setRemark("巢落提现测试"); resultBase =
 * alipayUtil.transferAccount(alipayRefun);
 * 
 * } else { alipayRefun.setPayee_account(temp.getRsrWechatId());
 * alipayRefun.setPayee_real_name(temp.getRsrWechatName());
 * alipayRefun.setRemark("巢落提现测试"); resultBase =
 * alipayUtil.wxTransferAccount(alipayRefun); } if (resultBase.isSuccess()) {
 * recordMapper.updateStatus("3", "5", temp.getRsrUuid());
 * customMessage.setTitle("余额提现成功"); customMessage.setDescription("您于 " +
 * temp.getRsrTradeTime() + "将 " + temp.getRsrTradeSum() + " 从零钱内提现至 " +
 * (temp.getRsrAlipayName() != null && !temp.getRsrAlipayName().equals("") ?
 * "支付宝" : "微信") + " 的操作已成功，注意查收哟~有问题请联系客服小巢");
 * JgSendUtil.toJpSend(temp.getUserUuid(), "余额提现", "您于 " +
 * temp.getRsrTradeTime() + "将 " + temp .getRsrTradeSum() + " 从零钱内提现至 " +
 * (temp.getRsrAlipayName() != null && !temp.getRsrAlipayName().equals("") ?
 * "支付宝" : "微信") + " 的操作已成功，注意查收哟~有问题请联系@客服小巢");
 * JgSendUtil.sendCustomMessage(temp.getUserUuid(), customMessage); } else {
 * customMessage.setTitle("余额提现失败"); customMessage.setDescription("很抱歉！由于 " +
 * resultBase.getMessage() + "您于 " + temp.getRsrTradeTime() + "将 " +
 * temp.getRsrTradeSum() + " 从零钱内提现至 " + (temp.getRsrAlipayName() != null &&
 * !temp.getRsrAlipayName().equals("") ? "支付宝" : "微信") + " 操作失败，有问题请联系客服小巢");
 * JgSendUtil.toJpSend(temp.getUserUuid(), "余额提现", "很抱歉！由于 " +
 * resultBase.getMessage() + "您于 " + temp.getRsrTradeTime() + "将 " + temp
 * .getRsrTradeSum() + " 从零钱内提现至 " + (temp.getRsrAlipayName() != null &&
 * !temp.getRsrAlipayName().equals("") ? "支付宝" : "微信") + " 操作失败，有问题请联系@客服小巢");
 * JgSendUtil.sendCustomMessage(temp.getUserUuid(), customMessage);
 * 
 * } } }
 * 
 * }
 * 
 * 
 *//**
	 * 押金退款定时器（30秒扫描一次）
	 */
/*
 * @Scheduled(cron = "0/40 * * * * ?") public void returnAccount() {
 * List<PledgeWithdrawInfo> list = pledgeWithdrawInfoMapper.selectReturnMoney();
 * // 3提现// 2已审核 if (!CollectionUtils.isEmpty(list)) { LOGGER.
 * info("================com.nest_lot.config.TaskConfig.returnAccount()===============return start======="
 * ); for (PledgeWithdrawInfo temp : list) { LOGGER.info("走到这里来了，肯定！！！！！！！！！");
 * LOGGER.info("走到这里来了，肯定！！！！！！！！！"); PledgeInfo pledgeInfo =
 * pledgeInfoMapper.queryByStatus(temp.getPledgeUuid());
 * LOGGER.info("来了老弟1111111111111"+pledgeInfo); Double txtotal = 0.0D; SaleInfo
 * saleInfo =null; if (pledgeInfo != null &&
 * org.springframework.util.StringUtils.hasText(pledgeInfo.getSaleUuid())) {
 * saleInfo = saleInfoMapper.selectByPrimaryKey(pledgeInfo.getSaleUuid()); if
 * (saleInfo != null && saleInfo.getSalePrefprice() >= 99.00D) { txtotal =
 * saleInfo.getSalePrefprice(); LOGGER.info("来了老弟222222222222"+txtotal);
 * LOGGER.info("来了老弟222222222222"+txtotal); } else { txtotal =
 * temp.getTxTotal(); LOGGER.info(
 * "############################押金数据并不异常#############################"); } }
 * else { txtotal = temp.getTxTotal(); LOGGER.info("来了老弟3333333333333"+txtotal);
 * LOGGER.info("来了老弟3333333333333"+txtotal); } ResultBase resultBase = new
 * ResultBase(); CustomMessage customMessage = new CustomMessage();
 * customMessage.setType(""); if (temp.getPledgePayType().equals("2")) { //
 * 支付宝退款 AlipayRefund alipayRefun = new AlipayRefund();
 * alipayRefun.setAmount(String.valueOf(temp.getPledgeTotal()));
 * alipayRefun.setOut_biz_no(temp.getPledgeUuid());
 * alipayRefun.setRemark("巢落押金退款"); resultBase =
 * AlipayUtil.refundOrder(alipayRefun);
 * 
 * } else { LOGGER.info("================================weipay start=======");
 * WxRefund wxrefund = new WxRefund();
 * 
 * wxrefund.setOut_refund_no(temp.getPledgeUuid());
 * wxrefund.setOut_trade_no(temp.getPledgeUuid());
 * wxrefund.setRefund_desc("巢落押金退款"); LOGGER.info("来了老弟44444444444"+txtotal);
 * wxrefund.setTotal_fee(txtotal); wxrefund.setRefund_fee(temp.getTxTotal()); if
 * (saleInfo != null && saleInfo.getSaleChannel() != null) { resultBase =
 * alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }else { resultBase
 * = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }
 * LOGGER.info("================================weipay end=======" +
 * resultBase.isSuccess()); } if (resultBase.isSuccess()) {
 * pledgeWithdrawInfoMapper.updateStatus(temp.getTxUuid(), "4", ""); // 明细表改
 * pledgeInfoMapper.updateReturnStatus(temp.getPledgeUuid(), temp.getTxTotal());
 * // 押金表更改 if (!StringUtils.isNullOrEmpty(temp.getUserPhone())) {
 * smsConfig.returnPledgeCode(temp.getUserPhone(), temp.getTxTime(),
 * ResultEnum.SMS_1306.getMessage()); }
 * 
 * // customMessage.setTitle("押金退款成功").setDescription("您于 " + temp.getTxTime() +
 * // "申请押金退款,退款的操作已成功，注意查收哟~有问题请联系客服小巢"); //
 * JgSendUtil.toJpSend(temp.getUserUuid(), "押金退款", // "您于 " + temp.getTxTime() +
 * "申请押金退款,退款的操作已成功，注意查收哟~有问题请联系@客服小巢"); //
 * JgSendUtil.sendCustomMessage(temp.getUserUuid(), customMessage);
 * 
 * } else { // if (!StringUtils.isNullOrEmpty(temp.getUserPhone())) { //
 * smsConfig.returnPledgeCode(temp.getUserPhone(), temp.getTxTime(), //
 * ResultEnum.SMS_1304.getMessage()); // } // //
 * JgSendUtil.toJpSend(temp.getUserUuid(), "押金退款", "您于 " + temp.getTxTime() + //
 * "申请押金退款,因" // + resultBase.getMessage() + "退款的操作失败，注意查收哟~有问题请联系@客服小巢"); //
 * customMessage.setTitle("押金退款失败"); // customMessage.setDescription("您于 " +
 * temp.getTxTime() + "申请押金退款,因" + // resultBase.getMessage() // +
 * "退款的操作失败，注意查收哟~有问题请联系客服小巢"); //
 * JgSendUtil.sendCustomMessage(temp.getUserUuid(), customMessage); } } }
 * LOGGER.
 * info("==========22======com.nest_lot.config.TaskConfig.returnAccount()=============== end======="
 * ); }
 * 
 * 
 *//**
	 * 2019 0828 新需求：每个房间都带有一个押金金额，下订单的同时缴纳押金，订单结束后三天，自动退还押金给用户 押金退款定时器（1小时扫描一次）
	 */
/*
 * //@Scheduled(cron = "0/30 * * * * ?") //30秒执行一次 //@Scheduled(cron =
 * "0 0 * * * ? *") //1小时执行一次
 * 
 * public void pledgeReturnWithinThreeDays() {
 * 
 * //查询已缴纳的押金 List<PledgeInfo>
 * pledgeInfos=pledgeInfoMapper.selectPledgeInfo("1", "2"); ResultBase
 * resultBase = new ResultBase(); for (int i = 0; i < pledgeInfos.size(); i++) {
 * double pledgeSum=0.00; String pledgeUuid=pledgeInfos.get(i).getPledgeUuid();
 * PledgeInfo pledgeInfo =pledgeInfoMapper.selectByPrimaryKey(pledgeUuid);
 * PledgeWithdrawInfo
 * pledgeWithdrawInfo=pledgeWithdrawInfoMapper.selectPledgeWithdrawInfo(
 * pledgeUuid); if(pledgeWithdrawInfo!=null) {
 * pledgeSum=pledgeWithdrawInfo.getTxTotal(); }else {
 * pledgeSum=pledgeInfo.getPledgeSum(); }
 * 
 * //查询该押金跟随的订单信息 String saleUuid=pledgeInfo.getSaleUuid(); if(saleUuid!=null) {
 * SaleInfo saleInfo1 =saleInfoMapper.selectSaleInfoBySaleUuid(saleUuid); String
 * saleTimeEnd=saleInfo1.getSaleTimeEnd(); //判断 结束时间距离当前时间是否有3天 String
 * currentTime=Tools.timeStamp2Date(); String
 * timeDifference=DateUtils.getDateValueDay(saleTimeEnd, currentTime); Integer
 * a=Integer.parseInt(timeDifference); if(a>=2) { WxRefund wxrefund = new
 * WxRefund(); wxrefund.setOut_refund_no(pledgeUuid);
 * wxrefund.setOut_trade_no(pledgeUuid); wxrefund.setRefund_desc("巢落押金退款");
 * LOGGER.info("来了老弟44444444444"+pledgeSum); wxrefund.setTotal_fee(pledgeSum);
 * wxrefund.setRefund_fee(pledgeSum); SaleInfo saleInfo =
 * saleInfoMapper.selectByPrimaryKey(pledgeInfo.getSaleUuid()); if (saleInfo !=
 * null && saleInfo.getSaleChannel() != null) { resultBase =
 * alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }else { resultBase
 * = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }
 * LOGGER.info("========pledgeUuid==========" + pledgeUuid);
 * LOGGER.info("========没有申请退押金，押金自动退还========weipay end=======" +
 * resultBase.isSuccess()); } }else { //如果押金信息里面saleUuid为null，那么就是以前版本的
 * 单独支付押金，押金金额为99元 WxRefund wxrefund = new WxRefund();
 * wxrefund.setOut_refund_no(pledgeUuid); wxrefund.setOut_trade_no(pledgeUuid);
 * wxrefund.setRefund_desc("巢落押金退款"); LOGGER.info("来了老弟44444444444"+pledgeSum);
 * wxrefund.setTotal_fee(pledgeSum); wxrefund.setRefund_fee(pledgeSum); SaleInfo
 * saleInfo = saleInfoMapper.selectByPrimaryKey(pledgeInfo.getSaleUuid()); if
 * (saleInfo != null && saleInfo.getSaleChannel() != null) { resultBase =
 * alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }else { resultBase
 * = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }
 * LOGGER.info("========pledgeUuid==========" + pledgeUuid);
 * LOGGER.info("========没有申请退押金，押金自动退还========weipay end=======" +
 * resultBase.isSuccess()); }
 * 
 * 
 * 
 * if(resultBase.isSuccess()) { int
 * count=pledgeInfoMapper.updateReturnStatus(pledgeUuid, pledgeSum); // 押金表更改
 * if(pledgeWithdrawInfo!=null) {
 * pledgeWithdrawInfoMapper.updateStatus(pledgeWithdrawInfo.getTxUuid(), "4",
 * ""); // 押金明细表改 } if(count>1) { LOGGER.info("====pledgeInfo表的押金状态修改成功=====");
 * }
 * 
 * 
 * } }
 * 
 * //查询申请退押金 List<PledgeInfo>
 * pledgeInfos2=pledgeInfoMapper.selectPledgeInfo("2", "2"); for (int i = 0; i <
 * pledgeInfos2.size(); i++) { double pledgeSum=0.00; String
 * pledgeUuid=pledgeInfos2.get(i).getPledgeUuid(); PledgeWithdrawInfo
 * pledgeWithdrawInfo=pledgeWithdrawInfoMapper.selectPledgeWithdrawInfo(
 * pledgeUuid); PledgeInfo pledgeInfo
 * =pledgeInfoMapper.selectByPrimaryKey(pledgeUuid);
 * if(pledgeWithdrawInfo!=null) { pledgeSum=pledgeWithdrawInfo.getTxTotal();
 * }else { pledgeSum=pledgeInfo.getPledgeSum(); }
 * 
 * WxRefund wxrefund = new WxRefund(); wxrefund.setOut_refund_no(pledgeUuid);
 * wxrefund.setOut_trade_no(pledgeUuid); wxrefund.setRefund_desc("巢落押金退款");
 * LOGGER.info("来了老弟44444444444"+pledgeSum); wxrefund.setTotal_fee(pledgeSum);
 * wxrefund.setRefund_fee(pledgeSum); SaleInfo saleInfo =
 * saleInfoMapper.selectByPrimaryKey(pledgeInfo.getSaleUuid()); if (saleInfo !=
 * null && saleInfo.getSaleChannel() != null) { resultBase =
 * alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }else { resultBase
 * = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }
 * LOGGER.info("========pledgeUuid==========" + pledgeUuid);
 * LOGGER.info("========申请退押金，押金自动退还========weipay end=======" +
 * resultBase.isSuccess());
 * 
 * if(resultBase.isSuccess()) { if(pledgeWithdrawInfo!=null) { int
 * count1=pledgeWithdrawInfoMapper.updateStatus(pledgeWithdrawInfo.getTxUuid(),
 * "4", ""); // 明细表改 int
 * count2=pledgeInfoMapper.updateReturnStatus(pledgeUuid,pledgeSum); // 押金表更改
 * if(count1>0 && count2>0) {
 * LOGGER.info("========pledgeWithdrawInfo和pledgeInfo状态修改成功==========" +
 * pledgeUuid); } }
 * 
 * } }
 * 
 * LOGGER.info(
 * "======3天之内退还押金======com.nest_lot.config.TaskConfig.pledgeReturnWithinThreeDays()======end======"
 * ); }
 * 
 * 
 * @Scheduled(cron = "0/50 * * * * ?") //30秒执行一次 //@Scheduled(cron =
 * "0 0 * * * ? *") //1小时执行一次 //@Scheduled(cron = "0 0 0 1/3 * ? *") //3天执行一次
 * public void testPledgeReturnWithinThreeDays() {
 * LOGGER.info("=========走到这里来了吗========="); //查询已缴纳的押金
 * 
 * ResultBase resultBase = new ResultBase();
 * 
 * String pledgeUuid="fca22e9d3f86424b869a957f03eb278a"; PledgeInfo pledgeInfo
 * =pledgeInfoMapper.selectByPrimaryKey(pledgeUuid); double
 * pledgeSum=pledgeInfo.getPledgeSum();
 * 
 * WxRefund wxrefund = new WxRefund(); wxrefund.setOut_refund_no(pledgeUuid);
 * wxrefund.setOut_trade_no(pledgeUuid); wxrefund.setRefund_desc("巢落押金退款");
 * LOGGER.info("来了老弟44444444444"+pledgeSum); wxrefund.setTotal_fee(pledgeSum);
 * wxrefund.setRefund_fee(pledgeSum); SaleInfo saleInfo =
 * saleInfoMapper.selectByPrimaryKey(pledgeInfo.getSaleUuid()); if (saleInfo !=
 * null && saleInfo.getSaleChannel() != null) { resultBase =
 * alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }else { resultBase
 * = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }
 * LOGGER.info("========pledgeUuid==========" + pledgeUuid);
 * LOGGER.info("========没有申请退押金，押金自动退还========weipay end=======" +
 * resultBase.isSuccess());
 * 
 * if(resultBase.isSuccess()) { int
 * count=pledgeInfoMapper.updateReturnStatus(pledgeUuid, pledgeSum); // 押金表更改
 * if(count>1) { LOGGER.info("====pledgeInfo表的押金状态修改成功====="); }
 * 
 * 
 * } }
 * 
 * 
 * 
 *//**
	 * 重单定时退款
	 *//*
		 * @Scheduled(cron = "0/30 * * * * ?") public void returnRepetitionOrder() { //
		 * 实时查询订单状态如果发现状态为8的、立即进行退款、退款后状态改为7、并且在描述字段添加退单原因 // 退单原因：重单必退 SaleInfo
		 * saleInfo = saleInfoMapper.selectBySaleStatus("8"); if (saleInfo != null) {
		 * LOGGER.
		 * info("returnRepetitionOrder================================returnRepetitionOrder start======="
		 * ); ResultBase resultBase = new ResultBase(); String sale_uuid =
		 * saleInfo.getSaleUuid(); int ispledgeForSale =
		 * pledgeInfoMapper.queryBySaleUUID(saleInfo.getSaleUuid()); Double
		 * SalePrefprice = 0.0D; if (saleInfo.getSalePrefprice() == null) {
		 * saleInfo.setSalePrefprice(0.0D); } if (ispledgeForSale > 0) { SalePrefprice
		 * += saleInfo.getSalePrefprice() - 99.0D; } else { SalePrefprice =
		 * saleInfo.getSalePrefprice(); }
		 * 
		 * // 首先根据订单号查到用户手机号信息==根据订单号查uir_uuid、根据uir_uuid查user——phone
		 * 
		 * SaleUserInfo saleUserInfo = saleUserInfoMapper.queryBySaleUUid(sale_uuid);
		 * String uirUuid = saleUserInfo.getUirUuid(); UserInfoReal userInfoReal =
		 * userInfoRealMapper.queryByUuid(uirUuid); LOGGER.info(
		 * "returnRepetitionOrder================================userInfoReal=======" +
		 * saleUserInfo); String user_phone = null; if (userInfoReal != null) {
		 * user_phone = userInfoReal.getUserPhone(); } // 退款、完成后改变状态、发送短信 LOGGER.
		 * info("returnRepetitionOrder================================weipay start======="
		 * ); if (SalePrefprice > 0) { WxRefund wxrefund = new WxRefund();
		 * wxrefund.setOut_refund_no(sale_uuid); wxrefund.setOut_trade_no(sale_uuid);
		 * wxrefund.setRefund_desc("巢落重单退款"); wxrefund.setTotal_fee(SalePrefprice);
		 * wxrefund.setRefund_fee(SalePrefprice); if (saleInfo.getSaleChannel() == null)
		 * { resultBase = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.appId); }else
		 * { resultBase = alipayUtil.wxRefundAccount(wxrefund,WxConfigUtil.xappId); }
		 * 
		 * LOGGER.
		 * info("returnRepetitionOrder================================weipay end======="
		 * + resultBase.isSuccess()); // 退款成功、更改订单状态、并且发送短息、退款失败、发送短信提示 if
		 * (resultBase.isSuccess()) { saleInfoMapper.updateReBack(sale_uuid); String[]
		 * strs = saleInfoMapper.selectByPhoneName(sale_uuid).getNest_name().split("/");
		 * String address = ""; if (strs != null) { address = strs[strs.length - 1];
		 * smsConfig.returnPledgeCode2(user_phone, Tools.timeStamp(), address,
		 * ResultEnum.SMS_1311.getMessage());// 需要短信模板 LOGGER.info(
		 * "returnRepetitionOrder================================退款成功短信发送成功======="); }
		 * else { LOGGER.info(
		 * "returnRepetitionOrder================================退款成功短信发送失败======="); }
		 * } } else {
		 * LOGGER.info("returnRepetitionOrder┇====┇======┇ 退款金额数据异常  ┇===============┇"
		 * ); LOGGER.info(
		 * "returnRepetitionOrder================================退款金额小于等于,0无法退款=======")
		 * ; saleInfoMapper.updateReBack(sale_uuid); } } LOGGER.
		 * info("com.nest_lot.config.TaskConfig.returnRepetitionOrder()============= end======="
		 * ); }
		 * 
		 * }
		 */