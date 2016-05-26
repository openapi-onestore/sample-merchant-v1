package com.skplanet.openapi.admin.oauth;

public class OAuthAccountInfo {
	
	private String merchantId;
	private String merchantPw;
	private String appId;
	private String[] ipRange;
	private OAuthAccountScope[] apiScopes;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantPw() {
		return merchantPw;
	}

	public void setMerchantPw(String merchantPw) {
		this.merchantPw = merchantPw;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String[] getIpRange() {
		return ipRange;
	}

	public void setIpRange(String[] ipRange) {
		this.ipRange = ipRange;
	}
	
	public OAuthAccountScope[] getApiScopes() {
		return apiScopes;
	}

	public void setApiScopes(OAuthAccountScope[] apiScopes) {
		this.apiScopes = apiScopes;
	}
	
	public boolean validateAccountInfo() {
		if (getMerchantId() == null || getMerchantPw() == null
				|| getAppId() == null || getIpRange() == null
				|| getApiScopes() == null) {
			return false;
		}
		return true;
	}
	
}
