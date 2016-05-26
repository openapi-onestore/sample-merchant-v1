package com.skplanet.openapi.external.framework;

import com.skplanet.openapi.external.notification.NotiManager;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.payment.OpenApiManager;

public abstract class AbstractManagerFactory {
	
	public abstract OAuthManager getOAuthManager(OAuthClientInfo oauthClientInfo);
	public abstract OAuthManager getOAuthManager();
	public abstract NotiManager getNotiManager();
	public abstract OpenApiManager getOpenApiManager();
	
}
