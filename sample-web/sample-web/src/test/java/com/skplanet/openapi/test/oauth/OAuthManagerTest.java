package com.skplanet.openapi.test.oauth;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.openapi.external.framework.Environment;
import com.skplanet.openapi.external.framework.ManagerProducer;
import com.skplanet.openapi.external.oauth.OAuthAccessToken;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.util.KvpPostRequest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/web.xml",
		"file:src/main/resources/spring/mybatis-context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
		})
public class OAuthManagerTest {
	
	private OAuthClientInfo oauthClientInfo;
	
	@Before
	public void setUp() {
		oauthClientInfo = new OAuthClientInfo("rcWGYj7tu3PnsBAOsWCplsaFbadMlaWpNSwcZxEIPx1YhAK6xrFDF1HXOEe9ilCS", "T/Va54I5n/5ULmINeJELK7rUpYtWC38BgnTbmmtQvSk=", "client_credentials");
	}
	
	@Test
	public void getAccessToken() {
		
		OAuthManager oauthManager = ManagerProducer.getFactory(Environment.SANDBOX, "").getOAuthManager(oauthClientInfo);
		OAuthAccessToken oauth = null;
		
		try {
			oauth = oauthManager.createAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(oauth);
		Assert.assertEquals(32, oauth.getAccessToken().length());
		Assert.assertEquals("Bearer", oauth.getTokenType());
		
		String scope = oauth.getScope();
		Assert.assertEquals(true, scope.contains("GetTxInfo"));
		Assert.assertEquals(true, scope.contains("GetTxInfoV2"));
		
		// TODO: Http client test
		KvpPostRequest kvpPostRequest = new KvpPostRequest();
		kvpPostRequest.setCallUrl("http://172.21.60.143:8080/oauth/service/accessToken");
		Map<String, String> param = new HashMap<String, String>();
		param.put("grant_type", "client-secret");
		kvpPostRequest.setParameter(param);
		
		StringBuilder sb = new StringBuilder();		
		System.out.println(oauthClientInfo.getAuthString());
		
		byte[] basicStringBase64 = Base64.encodeBase64(oauthClientInfo.getAuthString().getBytes());
		sb.append("BASIC ").append(new String(basicStringBase64));
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", sb.toString());
		
		kvpPostRequest.setHeader(headerMap);
		
	}
	
//	@Test
//	public void getAccessTokenValidation() {
//		
//		OAuthManager oauthManager = new OAuthManager();
//		oauthManager.setClientInfo(oauthClientInfo);
//		
//		OAuthAccessToken oauth = null;
//		OAuthVerifyResult oauthVerifyResult = null;
//		
//		try {
//			Assert.assertEquals(true, oauthManager.createOAuthAccessToken());
//			oauth = oauthManager.getOAuthToken();
//			Assert.assertEquals(true, oauthManager.verifyOAuthToken("172.21.60.143"));
//			oauthVerifyResult = oauthManager.getOAuthVerifyResult();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Assert.assertEquals("SUCCESS", oauthVerifyResult.getStatus());
//		Assert.assertEquals("", oauthVerifyResult.getReason());
//		Assert.assertEquals("JaeWoo", oauthVerifyResult.getMerchantId());
//		Assert.assertEquals(true, oauth.getScope().contains(oauthVerifyResult.getScopes()[0]));
//		System.out.println("Expired At : " + oauthVerifyResult.getExpiredAt() + " Message : " + oauthVerifyResult.getMessage());
//		
//	}
//	
//	@Test
//	public void getOAuthAccountCreate() {
//		OAuthManager oauthManager = new OAuthManager();
//		OAuthAccountInfo oauthAccountInfo = new OAuthAccountInfo();
//		
//		String[] ipRange = {"127.0.0.1" , "10.202" };
//		OAuthAccountScope scope1, scope2;
//		scope1 = new OAuthAccountScope();
//		scope2 = new OAuthAccountScope();
//		
//		scope1.setApiId("GetTxInfo"); scope1.setRelativeUrl("/test"); scope1.setCdState(0);
//		scope2.setApiId("GetTxInfoV2"); scope2.setRelativeUrl("/v2/test"); scope2.setCdState(0);		
//		
//		OAuthAccountScope[] apiScope = { scope1, scope2 };
//		
//		oauthAccountInfo.setMerchantId("JaeWoo");
//		oauthAccountInfo.setMerchantPw("JW1234");
//		oauthAccountInfo.setAppId("OA00001234");
//		oauthAccountInfo.setIpRange(ipRange);
//		oauthAccountInfo.setApiScopes(apiScope);
//		
//		oauthManager.setAccountInfo(oauthAccountInfo);
//		Assert.assertEquals(false, oauthManager.createOAuthAccount());
//		
//		try {
//			OAuthAccount oauthAccount = oauthManager.getOAuthAccount();
//			Assert.assertEquals("FAILED", oauthAccount.getResult());
//			Assert.assertEquals(null, oauthAccount.getClientId());
//			Assert.assertEquals(null, oauthAccount.getClientSecret());
//			Assert.assertEquals(null, oauthAccount.getAccountStatus());
//		} catch (OAuthManagingException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test(expected=Exception.class)
//	public void getReadPropertyFail() throws Exception {
//		
//		OAuthManager oauthManager = new OAuthManager();
//		try {
//			oauthManager.setPropertyFile("D:\1114.properties");
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception();
//		}
//		
//	}
	
}
