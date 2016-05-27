package com.skplanet.openapi.admin.oauth;

public class OAuthVerifyResult {

	private String status;
	private String reason;
	private String expiredAt;
	private String message;
	private String appId;
	private String merchantId;
	private String[] scopes;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getExpiredAt() {
		return expiredAt;
	}
	
	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getAppId() {
		return appId;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String[] getScopes() {
		return scopes;
	}
	
	public void setScopes(String[] scopes) {
		this.scopes = scopes;
	}
	
}
