package com.skplanet.openapi.external.oauth;


public class OAuthManagingException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public enum OAuthManaging {
		OAUTH_OBJECT_NULL,
		INVALID_PARAMETER,
		OAUTH_HTTP_REQUEST_FAIL,
		UNKNOWN_ERROR;
		
		private String msg;
		private OAuthManaging() {
			msg = "Undefined";
		}
		private OAuthManaging(String custMsg) {
			this.msg = custMsg;
		}
		public String getMsg() {
			return msg;
		}		
	}
	
	private OAuthManaging status = OAuthManaging.UNKNOWN_ERROR;
	
	public OAuthManagingException(String msg) {
		super(msg);
	}
	
	public OAuthManagingException(OAuthManaging control) {
		this.setStatus(control);
	}
	
	public OAuthManagingException(OAuthManaging control, String msg) {
		super(msg);
		this.setStatus(control);
	}
	
	public OAuthManaging getStatus() {
		return status;
	}

	public void setStatus(OAuthManaging status) {
		this.status = status;
	}
}
