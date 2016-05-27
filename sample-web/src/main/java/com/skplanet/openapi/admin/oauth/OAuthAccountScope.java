package com.skplanet.openapi.admin.oauth;

public class OAuthAccountScope {

	private String apiId;
	private String relativeUrl;
	private int cdState;
	
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	public String getRelativeUrl() {
		return relativeUrl;
	}
	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}
	public int getCdState() {
		return cdState;
	}
	public void setCdState(int cdState) {
		this.cdState = cdState;
	}
	
}
