package com.skplanet.openapi.external.oauth;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.openapi.external.oauth.OAuthManagingException.OAuthManaging;
import com.skplanet.openapi.external.payment.OpenApiManagerImpl.OPEN_API_MODE;
import com.skplanet.openapi.external.util.KvpPostRequest;

public class OAuthManagerImpl implements OAuthManager {
	
	private static final Logger logger = LoggerFactory.getLogger(OAuthManagerImpl.class);
	private OAuthClientInfo clientInfo = null;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// url default setting
	private final String developmentOauthTokenUrl = "http://172.21.60.143:8080/oauth/service/accessToken";
	private final String sandboxOauthTokenUrl = "https://sandbox.apiauth.payplanet.co.kr/oauth/service/accessToken";
	private final String releaseOauthTokenUrl = "https://apiauth.payplanet.co.kr/oauth/service/accessToken";
	private String oauthAccessTokenUrl;
	
	public OAuthManagerImpl(OPEN_API_MODE mode, OAuthClientInfo oauthClientInfo, String logPath) {
		if (mode == OPEN_API_MODE.DEVELOPMENT) {
			this.oauthAccessTokenUrl = developmentOauthTokenUrl;
		} else if (mode == OPEN_API_MODE.SANDBOX) {
			this.oauthAccessTokenUrl = sandboxOauthTokenUrl;
		} else {
			this.oauthAccessTokenUrl = releaseOauthTokenUrl;			
		}
		this.clientInfo = oauthClientInfo;
		
		if (logPath != null) {
			if (logPath.length() > 0) {
				PropertyConfigurator.configure(logPath);
			}
		}		
	}
	
	public OAuthManagerImpl(Boolean isSandBoxMode, String logPath) {
		if (logPath != null) {
			if (logPath.length() > 0) {
				PropertyConfigurator.configure(logPath);
			}
		}
	}
	
	@Override
	public OAuthAccessToken createAccessToken(OAuthClientInfo oauthClientInfo)
			throws OAuthManagingException {
		this.clientInfo = oauthClientInfo;
		return createAccessToken();
	}
	
	@Override
	public OAuthAccessToken createAccessToken() throws OAuthManagingException {
		
		if (clientInfo == null) {
			throw new OAuthManagingException(OAuthManaging.OAUTH_OBJECT_NULL, "Client info is null!!");
		}
		
		if (!clientInfo.validateClientInfo()) {
			throw new OAuthManagingException(OAuthManaging.INVALID_PARAMETER, "Client info is invalid!!");
		}
		
		Map<String, String> data = new HashMap<String, String>();
		data.put(clientInfo.GRANT_TYPE, clientInfo.getGrantType());
		
		KvpPostRequest kvpPostRequest = new KvpPostRequest();
		kvpPostRequest.setHeader(getOAuthHttpRequestHeader());
		kvpPostRequest.setCallUrl(oauthAccessTokenUrl);
		kvpPostRequest.setParameter(data);
		
		String response = null;
		OAuthAccessToken accessToken = null;
		
		try {
			logger.info("Set call uri : " + oauthAccessTokenUrl);
			response = kvpPostRequest.executeRequest();
			logger.info("Post response : " + response);
			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthManagingException(OAuthManaging.OAUTH_HTTP_REQUEST_FAIL, "OAUTH http request is failure!!");
		}
		
		try {
			accessToken = objectMapper.readValue(response, OAuthAccessToken.class);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new OAuthManagingException(OAuthManaging.OAUTH_HTTP_REQUEST_FAIL, "Json parsing error!!");
		}
		
		return accessToken;
	}
	
	private Map<String, String> getOAuthHttpRequestHeader() {
		StringBuilder sb = new StringBuilder();		
		logger.info(clientInfo.getAuthString());
		
		byte[] basicStringBase64 = Base64.encodeBase64(clientInfo.getAuthString().getBytes());
		sb.append("BASIC ").append(new String(basicStringBase64));
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", sb.toString());
		logger.info("Authorization : " + sb.toString());
		
		return headerMap;
	}
	
}
