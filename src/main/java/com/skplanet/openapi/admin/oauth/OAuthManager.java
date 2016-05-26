package com.skplanet.openapi.admin.oauth;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;

import com.skplanet.openapi.admin.oauth.OAuthManagingException;
import com.skplanet.openapi.admin.oauth.OAuthManagingException.OAuthManaging;

public class OAuthManager implements OAuthInterface {

	private int threadPoolCount = 1;
	private ExecutorService jobExecutor = Executors.newFixedThreadPool(threadPoolCount, new ThreadFactory() {
		@Override
		public Thread newThread(Runnable runnable) {
			Thread oauthManagerThread = Executors.defaultThreadFactory().newThread(runnable);
			oauthManagerThread.setName("oauthManager");
			oauthManagerThread.setDaemon(true);
			return oauthManagerThread;
		}
	});
	
	private OAuthAccountInfo accountInfo = null;
	private OAuthClientInfo clientInfo = null;
	
	private OAuthAccount oauthAccount = null;
	private OAuthAccessToken oauth = null;
	private OAuthVerifyResult oauthVerifyResult = null;
	
	private String propertyPath = null;
	
	// url default setting
	private String oauthAccountCreateUrl = "http://172.21.60.143:8080/oauth/admin/management/account/creation";
	private String oauthAccessTokenUrl = "http://172.21.60.143:8080/oauth/service/accessToken";
	private String oauthVerifyUrl = "http://172.21.60.143:8080/oauth/internal/v1/validation";
	
//	// Jeff
//	public OAuthManager(String clientId, String secret) {
//		
//	}
	
//	public static OAuthManager createOauthManager(String clientId, String secret) {
//		return null;
//	}
	
	//~Jeff
	
	
	@Override
	public void setAccountInfo(OAuthAccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	
	@Override
	public void setClientInfo(OAuthClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	@Override
	public boolean createOAuthAccessToken() {
		
		if (clientInfo == null)
			return false;
		
		if (!clientInfo.validateClientInfo())
			return false;
		
		Map<String, String> data = new HashMap<String, String>();
		data.put(clientInfo.GRANT_TYPE, clientInfo.getGrantType());
		
		String response = null;
		
		OAuthHttpRequest httpRequest = new OAuthHttpRequest();
		
		try {
			httpRequest.setCallUrl(oauthAccessTokenUrl);
			httpRequest.setParamMap(data);
			httpRequest.setHeader(getOAuthHttpRequestHeader());
			
			Future<String> future = jobExecutor.submit(httpRequest);
			response = future.get();
			
			System.out.println("Post response : " + response);
			if (response != null) {
				ObjectMapper objectMapper = new ObjectMapper();
				oauth = objectMapper.readValue(response, OAuthAccessToken.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean createOAuthAccount() {
		
		if (accountInfo == null) {
			return false;
		}
		
		if (!accountInfo.validateAccountInfo()) {
			return false;
		}
		
		String response = null;
		OAuthHttpRequest httpRequest = new OAuthHttpRequest();
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			String accountJsonString = objectMapper.writeValueAsString(accountInfo);
			httpRequest.setCallUrl(oauthAccountCreateUrl);
			httpRequest.setParam(accountJsonString);
			
			Future<String> future = jobExecutor.submit(httpRequest);
			response = future.get();
			
			System.out.println("Post response : " + response);
			if (response != null) {
				objectMapper = new ObjectMapper();
				oauthAccount = objectMapper.readValue(response, OAuthAccount.class);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}

	@Override
	public boolean verifyOAuthToken(String ipAddress) {
		
		if (oauth == null)
			return false;
		
		Map<String, String> verifyData = new HashMap<String, String>();
		verifyData.put("ipAddress", "172.21.60.143");
		verifyData.put("apiId", oauth.getScope().split(" ")[0]);
		verifyData.put("accessToken", oauth.getAccessToken());
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthHttpRequest httpRequest = new OAuthHttpRequest();
		
		try {
			String requestJson = null;
			requestJson = objectMapper.writeValueAsString(verifyData);
			
			httpRequest.setCallUrl(oauthVerifyUrl);
			httpRequest.setParam(requestJson);
			
			Future<String> future = jobExecutor.submit(httpRequest);
			String response = future.get();			
			
			if (response != null) {
				oauthVerifyResult = objectMapper.readValue(response, OAuthVerifyResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
		
	@Override
	public OAuthAccessToken getOAuthToken() throws OAuthManagingException {
		if (oauth == null)
			throw new OAuthManagingException(OAuthManaging.OAUTH_OBJECT_NULL, "OAuth object is null");
		return this.oauth;
	}
	
	@Override
	public OAuthVerifyResult getOAuthVerifyResult() throws OAuthManagingException {
		if (oauthVerifyResult == null)
			throw new OAuthManagingException(OAuthManaging.OAUTH_OBJECT_NULL, "OAuth object is null");
		return oauthVerifyResult;
	}
	
	@Override
	public OAuthAccount getOAuthAccount() throws OAuthManagingException {
		if (oauthAccount == null)
			throw new OAuthManagingException(OAuthManaging.OAUTH_OBJECT_NULL, "OAuth object is null");
		return oauthAccount;
	}
	
	private Map<String, String> getOAuthHttpRequestHeader() {
		StringBuilder sb = new StringBuilder();		
		System.out.println(clientInfo.getAuthString());
		
		byte[] basicStringBase64 = Base64.encodeBase64(clientInfo.getAuthString().getBytes());
		sb.append("BASIC ").append(new String(basicStringBase64));
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", sb.toString());
		System.out.println("Authorization : " + sb.toString());
		
		return headerMap;
	}	
	
	public void setPropertyFile(String path) throws Exception {
		this.propertyPath = path;
		Properties props = new Properties();

		if (propertyPath == null) {
			throw new OAuthManagingException(OAuthManaging.OAUTH_OBJECT_NULL,"Property path is null");
		}

		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(propertyPath);
			props.load(new BufferedInputStream(fis));
			
			oauthAccountCreateUrl = props.getProperty("oauth.account_create_url");
			oauthAccessTokenUrl = props.getProperty("oauth.token_create_url");
			oauthVerifyUrl = props.getProperty("oauth.token_verify_url");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAuthManagingException(OAuthManaging.UNKNOWN_ERROR, "File creation is incorect!!");
		} finally {
			fis.close();
		}
	}
	
	public void setExecutorService(ExecutorService service) {
		if (jobExecutor != null) {
			this.jobExecutor.shutdown();			
		}
		this.jobExecutor = service;
	}
	
}
