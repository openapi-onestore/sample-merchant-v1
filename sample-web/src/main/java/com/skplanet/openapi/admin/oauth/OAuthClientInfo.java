package com.skplanet.openapi.admin.oauth;


public final class OAuthClientInfo {
	
	public final String CLIENT_ID = "client_id";
	public final String CLIENT_SECRET = "client_secret";
	public final String GRANT_TYPE = "grant_type";
	
	private String clientId;
	private String clientSecret;	
	private String grantType;
	
	public String getAuthString() {
		return clientId + ":" + clientSecret;
	}
	
	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	public String getClientId() {
		return clientId;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}
	
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	public boolean validateClientInfo() {
		
		if (getClientId() == null)
			return false;
		
		if (getClientSecret() == null)
			return false;
		
		if (getGrantType() == null)
			return false;
		
		return true;
	}
	
}
