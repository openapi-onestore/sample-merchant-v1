package com.skplanet.openapi.test.notification;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.openapi.external.framework.Environment;
import com.skplanet.openapi.external.framework.ManagerProducer;
import com.skplanet.openapi.external.notification.NotiManager;
import com.skplanet.openapi.external.notification.NotiManagerImpl;
import com.skplanet.openapi.external.notification.NotiReceive;
import com.skplanet.openapi.external.notification.NotiVerifyResult;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.oauth.OAuthManagingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/web.xml",
		"file:src/main/resources/spring/mybatis-context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
		})
public class NotiManagerTest {
	
	private ObjectMapper objectMapper;
	private OAuthManager oauthManager;
	private NotiManager notiManager;
	private OAuthClientInfo oauthClientInfo;
	private String accessToken;
	
	@Before
	public void setUp() {
		objectMapper = new ObjectMapper();
		
		oauthClientInfo = new OAuthClientInfo("84xK38rx9iCrFRJVOynsRA0MT0o3LTs83OqDLEJf5g0=", "GS1qrhoHMJWpmS6QwLNaG5NcFWFqzh5TrmY5476a2nA=", "client_credentials");
		oauthManager = ManagerProducer.getFactory(Environment.SANDBOX, "").getOAuthManager(oauthClientInfo);
		
		try {
			accessToken = oauthManager.createAccessToken().getAccessToken();
		} catch (OAuthManagingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void requestNotificationVerificationTest() {
		
		notiManager = new NotiManagerImpl();
		
		NotiReceive notiReceive = new NotiReceive();
		notiReceive.setEvent("bulkJob");
		notiReceive.setStatus("completed");
		notiReceive.setJobId("100");
		notiReceive.setUpdateTime("20150910010101");
		notiReceive.setNotifyVersion("1.0");
		
		NotiVerifyResult notiVerifyResult = null;
		
		try {
			notiVerifyResult = notiManager.requestNotificationVerification(notiReceive, "bigcharging_notify_verification", accessToken);
			System.out.println(objectMapper.writeValueAsString(notiVerifyResult));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		objectMapper = new ObjectMapper();
		Assert.assertNotNull(notiVerifyResult);
	}
	
}
