package com.skplanet.openapi.vo;

public class FilePayment {

	private String mid;
	private String billingToken;
	private String pid;
	private String pName;
	private String orderNo;
	private String amtRequestPurchase;
	private String amtCarrier;
	private String amtCreditCard;
	private String amtTms;
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getBillingToken() {
		return billingToken;
	}
	public void setBillingToken(String billingToken) {
		this.billingToken = billingToken;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getAmtRequestPurchase() {
		return amtRequestPurchase;
	}
	public void setAmtRequestPurchase(String amtRequestPurchase) {
		this.amtRequestPurchase = amtRequestPurchase;
	}
	public String getAmtCarrier() {
		return amtCarrier;
	}
	public void setAmtCarrier(String amtCarrier) {
		this.amtCarrier = amtCarrier;
	}
	public String getAmtCreditCard() {
		return amtCreditCard;
	}
	public void setAmtCreditCard(String amtCreditCard) {
		this.amtCreditCard = amtCreditCard;
	}
	public String getAmtTms() {
		return amtTms;
	}
	public void setAmtTms(String amtTms) {
		this.amtTms = amtTms;
	}
	
}
