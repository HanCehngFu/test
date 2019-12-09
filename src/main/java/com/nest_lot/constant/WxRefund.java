package com.nest_lot.constant;

public class WxRefund {

	private String out_trade_no;// 商户单号

	private String out_refund_no; // 退款单号

	private Double total_fee; // 订单金额 分

	private Double refund_fee; // 退款金额

	private String refund_desc; // 退款原因

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getOut_refund_no() {
		return out_refund_no;
	}

	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

	public Double getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(Double refund_fee) {
		this.refund_fee = refund_fee;
	}

	public String getRefund_desc() {
		return refund_desc;
	}

	public void setRefund_desc(String refund_desc) {
		this.refund_desc = refund_desc;
	}

}
