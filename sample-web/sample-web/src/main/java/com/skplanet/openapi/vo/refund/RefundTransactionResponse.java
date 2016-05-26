package com.skplanet.openapi.vo.refund;

import com.skplanet.openapi.vo.payment.PaymentMethods;

public class RefundTransactionResponse {

	private String refundTid;
	private String tid;
	private int amount;
	private PaymentMethods[] paymentMethods;
	
	public String getRefundTid() {
		return refundTid;
	}
	
	public void setRefundTid(String refundTid) {
		this.refundTid = refundTid;
	}
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public PaymentMethods[] getPaymentMethods() {
		return paymentMethods;
	}

	public void setPaymentMethods(PaymentMethods[] paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
}
