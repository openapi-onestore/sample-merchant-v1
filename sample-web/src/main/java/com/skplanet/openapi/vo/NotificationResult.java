package com.skplanet.openapi.vo;

public class NotificationResult {
	
	private String jobId;
	private String event;
	private String status;
	private String verifySign;
	private String updateDate;
	private String notifyVersion;
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVerifySign() {
		return verifySign;
	}
	
	public void setVerifySign(String verifySign) {
		this.verifySign = verifySign;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getNotifyVersion() {
		return notifyVersion;
	}
	
	public void setNotifyVersion(String notifyVersion) {
		this.notifyVersion = notifyVersion;
	}
	
}
