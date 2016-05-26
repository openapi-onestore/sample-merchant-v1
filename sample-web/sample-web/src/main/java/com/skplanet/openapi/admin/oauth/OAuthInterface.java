package com.skplanet.openapi.admin.oauth;

public interface OAuthInterface {

	void setAccountInfo(OAuthAccountInfo accountInfo);
	void setClientInfo(OAuthClientInfo clientInfo);
	boolean createOAuthAccessToken();		// 1
	boolean verifyOAuthToken(String ipAddress);
	boolean createOAuthAccount();
	OAuthAccount getOAuthAccount() throws OAuthManagingException;	
	OAuthAccessToken getOAuthToken() throws OAuthManagingException;		// 1
	OAuthVerifyResult getOAuthVerifyResult() throws OAuthManagingException;
}
