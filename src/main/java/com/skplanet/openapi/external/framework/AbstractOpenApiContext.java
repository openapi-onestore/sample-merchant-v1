package com.skplanet.openapi.external.framework;

import com.skplanet.openapi.external.notification.NotiManager;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.payment.OpenApiManager;

public class AbstractOpenApiContext {

	protected OpenApiManager openApiManager;
	protected OAuthManager oauthManager;
	protected NotiManager notiManager;
	
	protected void initOpenApiContext(Environment env, String logPropertyFilePath) {
		openApiManager = ManagerProducer.getFactory(env, logPropertyFilePath).getOpenApiManager();
		oauthManager = ManagerProducer.getFactory(env, logPropertyFilePath).getOAuthManager();
		notiManager = ManagerProducer.getFactory(env, logPropertyFilePath).getNotiManager();
	}
	
	
	
	
}
