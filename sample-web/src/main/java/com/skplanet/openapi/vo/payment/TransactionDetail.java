package com.skplanet.openapi.vo.payment;

public class TransactionDetail {

	private String resultCode;
	private String resultMsg;
	
	private Payer payer;
	private PaymentTransactionInfo paymentTransactionInfo;
	
	public String getResultCode() {
		return resultCode;
	}
	
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	
	public Payer getPayer() {
		return payer;
	}
	
	public void setPayer(Payer payer) {
		this.payer = payer;
	}
	
	public PaymentTransactionInfo getPaymentTransactionInfo() {
		return paymentTransactionInfo;
	}
	
	public void setPaymentTransactionInfo(
			PaymentTransactionInfo paymentTransactionInfo) {
		this.paymentTransactionInfo = paymentTransactionInfo;
	}
	
}
