package com.skplanet.openapi.vo.refund;

import com.skplanet.openapi.vo.payment.PaymentMethods;

public class RefundTransactionRequest {
	
	private String refundTid;
	private String tid;
	private boolean fullRefund;
	private int amount;
	private String note;
	
	private PaymentMethods[] paymentMethods;
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}
	
	public boolean getFullRefund() {
		return fullRefund;
	}
	
	public void setFullRefund(boolean fullRefund) {
		this.fullRefund = fullRefund;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public PaymentMethods[] getPaymentMethods() {
		return paymentMethods;
	}
	
	public void setPaymentMethods(PaymentMethods[] paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	public String getRefundTid() {
		return refundTid;
	}

	public void setRefundTid(String refundTid) {
		this.refundTid = refundTid;
	}
	
}
