package com.skplanet.openapi.external.framework;

import com.skplanet.openapi.external.notification.NotiManager;
import com.skplanet.openapi.external.notification.NotiManagerImpl;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.oauth.OAuthManagerImpl;
import com.skplanet.openapi.external.payment.OpenApiManager;
import com.skplanet.openapi.external.payment.OpenApiManagerImpl;
import com.skplanet.openapi.external.payment.OpenApiManagerImpl.OPEN_API_MODE;

public class ManagerFactory extends AbstractManagerFactory {

	private String logPropertyPath = null;
	private Environment env = null;
	
	public ManagerFactory(String logPropertyPath) {
		this.logPropertyPath = logPropertyPath;
	}
	
	public ManagerFactory(Environment env, String logPropetyFilePath) {
		this.logPropertyPath = logPropetyFilePath;
		this.env = env;
	}
	
	@Override
	public OAuthManager getOAuthManager(OAuthClientInfo oauthClientInfo) {
		if (env == Environment.DEVELOPMENT) {
			return new OAuthManagerImpl(OPEN_API_MODE.DEVELOPMENT, oauthClientInfo, logPropertyPath);
		} else if (env == Environment.SANDBOX) {
			return new OAuthManagerImpl(OPEN_API_MODE.SANDBOX, oauthClientInfo, logPropertyPath);
		} else {
			return new OAuthManagerImpl(OPEN_API_MODE.RELEASE, oauthClientInfo, logPropertyPath);			
		}
	}
	
	@Override
	public OAuthManager getOAuthManager() {
		if (env == Environment.SANDBOX) {
			return new OAuthManagerImpl(true, logPropertyPath);
		} else {
			return new OAuthManagerImpl(false, logPropertyPath);			
		}
	}
	
	@Override
	public NotiManager getNotiManager() {
		if (env == Environment.DEVELOPMENT) {
			return new NotiManagerImpl(OPEN_API_MODE.DEVELOPMENT, logPropertyPath);
		}else if (env == Environment.SANDBOX) {
			return new NotiManagerImpl(OPEN_API_MODE.SANDBOX, logPropertyPath);
		} else {
			return new NotiManagerImpl(OPEN_API_MODE.RELEASE, logPropertyPath);			
		}		
	}
	
	@Override
	public OpenApiManager getOpenApiManager() {
		if (env == Environment.DEVELOPMENT) {
			return new OpenApiManagerImpl(OPEN_API_MODE.DEVELOPMENT, logPropertyPath);
		} if (env == Environment.SANDBOX) {
			return new OpenApiManagerImpl(OPEN_API_MODE.SANDBOX, logPropertyPath);
		} else {
			return new OpenApiManagerImpl(OPEN_API_MODE.RELEASE, logPropertyPath);
		}
	}
	
}
