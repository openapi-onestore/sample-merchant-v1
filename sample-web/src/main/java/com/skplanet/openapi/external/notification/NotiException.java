package com.skplanet.openapi.external.notification;


public class NotiException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public enum Noti {
		NOTI_PROPERTY_SETTING_ERROR,
		NOTI_JOB_EXECUTE_FAIL_ERROR,
		PARAMETER_PARSING_ERROR,
		UNKNOWN_ERROR;
		
		private String msg;
		private Noti() {
			msg = "Undefined";
		}
		private Noti(String custMsg) {
			this.msg = custMsg;
		}
		public String getMsg() {
			return msg;
		}
	}
	
	private Noti status = Noti.UNKNOWN_ERROR;
	
	public NotiException(String msg) {
		super(msg);
	}
	
	public NotiException(Noti control) {
		this.setStatus(control);
	}
	
	public NotiException(Noti control, String msg) {
		super(msg);
		this.setStatus(control);
	}
	
	public Noti getStatus() {
		return status;
	}

	public void setStatus(Noti status) {
		this.status = status;
	}	
	
}
