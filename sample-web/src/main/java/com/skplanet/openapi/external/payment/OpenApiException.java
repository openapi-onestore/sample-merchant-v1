package com.skplanet.openapi.external.payment;


public class OpenApiException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public enum OpenApi {
		OPENAPI_PROPERTY_SETTING_ERROR,
		OPENAPI_JOB_EXECUTE_ERROR,
		OPENAPI_FILE_NOT_FOUND_ERROR,
		UNKNOWN_ERROR;
		
		private String msg;
		private OpenApi() {
			msg = "Undefined";
		}
		private OpenApi(String custMsg) {
			this.msg = custMsg;
		}
		public String getMsg() {
			return msg;
		}
	}
	
	private OpenApi status = OpenApi.UNKNOWN_ERROR;
	
	public OpenApiException(String msg) {
		super(msg);
	}
	
	public OpenApiException(OpenApi control) {
		this.setStatus(control);
	}
	
	public OpenApiException(OpenApi control, String msg) {
		super(msg);
		this.setStatus(control);
	}
	
	public OpenApi getStatus() {
		return status;
	}

	public void setStatus(OpenApi status) {
		this.status = status;
	}
}
