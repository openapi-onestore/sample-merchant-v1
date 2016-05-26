package com.skplanet.openapi.vo.payment;

public class FilePaymentHeader {
	
	private String verBulkPay;
	private String bid;
	private String notiUrl;
	private long cntTotalTrans;
	private String priority;
	
	public FilePaymentHeader() {
		;
	}
	
	public FilePaymentHeader(String bid, String notiUrl, long cntTotalTrans, String priority) {
		this.bid = bid;
		this.notiUrl = notiUrl;
		this.cntTotalTrans = cntTotalTrans;
		this.priority = priority;
	}
	
	public String getVerBulkPay() {
		return verBulkPay;
	}
	public void setVerBulkPay(String verBulkPay) {
		this.verBulkPay = verBulkPay;
	}
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getNotiUrl() {
		return notiUrl;
	}
	public void setNotiUrl(String notiUrl) {
		this.notiUrl = notiUrl;
	}
	public long getCntTotalTrans() {
		return cntTotalTrans;
	}
	public void setCntTotalTrans(long cntTotalTrans) {
		this.cntTotalTrans = cntTotalTrans;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
}
