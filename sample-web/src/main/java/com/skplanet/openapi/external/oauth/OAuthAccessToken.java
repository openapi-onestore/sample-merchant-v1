package com.skplanet.openapi.external.oauth;

public class OAuthAccessToken {

	private String accessToken;
	private String expired; //TODO need to change to long type
	private String tokenType;
	private String scope;
	
	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getExpired() {
		return expired;
	}
	
	public void setExpired(String expired) {
		this.expired = expired;
	}
	
	public String getTokenType() {
		return tokenType;
	}
	
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getScope() {
		return scope;
	}
	
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
