package com.nest_lot.constant;

/**
 * 支付宝/微信 转账
 * 
 * @author 吴榧
 *
 */
public class AlipayRefund {

	private String out_biz_no; // 订单号

	private String payee_type = "ALIPAY_LOGONID"; // 支付宝登录号，支持邮箱和手机号格式。

	private String payee_account; // 收款方账户

	private String amount; // 金额，最小0.1

	private String remark; // 备注

	private String payee_real_name; // 实名

	public String getOut_biz_no() {
		return out_biz_no;
	}

	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	public String getPayee_type() {
		return payee_type;
	}

	public void setPayee_type(String payee_type) {
		this.payee_type = payee_type;
	}

	public String getPayee_account() {
		return payee_account;
	}

	public void setPayee_account(String payee_account) {
		this.payee_account = payee_account;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayee_real_name() {
		return payee_real_name;
	}

	public void setPayee_real_name(String payee_real_name) {
		this.payee_real_name = payee_real_name;
	}

}
