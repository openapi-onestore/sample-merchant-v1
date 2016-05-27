package com.skplanet.openapi.vo.payment;

public class FilePaymentResult {
	
	private String resultCode;
	private String resultMsg;
	private int waitingJobs;
	private String jobId;
	
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
	public int getWaitingJobs() {
		return waitingJobs;
	}
	public void setWaitingJobs(int watingJobs) {
		this.waitingJobs = watingJobs;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
}
