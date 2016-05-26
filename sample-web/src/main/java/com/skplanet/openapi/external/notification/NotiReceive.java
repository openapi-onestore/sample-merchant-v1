package com.skplanet.openapi.external.notification;

public class NotiReceive {

	private String notifyVersion;
	private String event;
	private String status;
	private String jobId;
	private String updateTime;
	
	public String getNotifyVersion() {
		return notifyVersion;
	}
	public void setNotifyVersion(String notifyVersion) {
		this.notifyVersion = notifyVersion;
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
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
