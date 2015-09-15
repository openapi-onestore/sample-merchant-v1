package com.skplanet.openapi.test.payment;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.oauth.OAuthManagerImpl;
import com.skplanet.openapi.external.oauth.OAuthManagingException;
import com.skplanet.openapi.external.payment.OpenApiException;
import com.skplanet.openapi.external.payment.OpenApiManager;
import com.skplanet.openapi.external.payment.OpenApiManagerImpl;
import com.skplanet.openapi.vo.payment.FilePaymentHeader;
import com.skplanet.openapi.vo.payment.FilePaymentResult;
import com.skplanet.openapi.vo.payment.Payer;
import com.skplanet.openapi.vo.payment.PaymentMethods;
import com.skplanet.openapi.vo.payment.TransactionDetail;
import com.skplanet.openapi.vo.refund.CancelRequest;
import com.skplanet.openapi.vo.refund.CancelResponse;
import com.skplanet.openapi.vo.refund.RefundTransactionRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/web.xml",
		"file:src/main/resources/spring/mybatis-context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
		})
public class OpenApiManagerTest {

	private ObjectMapper objectMapper;
	private OAuthManager oauthManager;
	private OAuthClientInfo oauthClientInfo;
	private String accessToken;
	
	@Before
	public void setUp() {
		objectMapper = new ObjectMapper();
		
		oauthClientInfo = new OAuthClientInfo();
		oauthClientInfo.setClientId("84xK38rx9iCrFRJVOynsRA0MT0o3LTs83OqDLEJf5g0=");
		oauthClientInfo.setClientSecret("GS1qrhoHMJWpmS6QwLNaG5NcFWFqzh5TrmY5476a2nA=");
		oauthClientInfo.setGrantType("client_credentials");
		
		oauthManager = new OAuthManagerImpl();
		oauthManager.setClientInfo(oauthClientInfo);
		try {
			accessToken = oauthManager.createAccessToken().getAccessToken();
		} catch (OAuthManagingException e) {
			e.printStackTrace();
		}
	}
	
	public void createFilePaymentTest() {
		// file payment test
		File requestFile = new File("D:/samplefolder/bulk_job.txt");
		Assert.assertEquals(true, requestFile.canRead());
		
		FilePaymentHeader filePaymentHeader = new FilePaymentHeader();
		filePaymentHeader.setVerBulkPay("1");
		filePaymentHeader.setBid("skplanet");
		filePaymentHeader.setNotiUrl("");
		filePaymentHeader.setCntTotalTrans("2");
		filePaymentHeader.setPrioity("Instant");
		
		OpenApiManager openApiManager = new OpenApiManagerImpl();
		
		FilePaymentResult filePaymentResult = null;
		try {
			filePaymentResult = openApiManager.createFilePayment(filePaymentHeader, requestFile, accessToken);
		} catch (OpenApiException e) {
			e.printStackTrace();
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		Assert.assertNotNull(filePaymentResult);
		try {
			System.out.println(objectMapper.writeValueAsString(filePaymentResult));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getResultFileFromPaymentTest() {
		
		OpenApiManager openApiManager = new OpenApiManagerImpl();
		
		File file = null;
		
		try {
			file = openApiManager.getFilePaymentJobStatus("38", accessToken);
		} catch (OpenApiException e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(file);
		
		
		
	}
	
	@Test
	public void propertyTest() {
		
//		Assert.assertNotNull(OAuthManagerImpl.class.getResource("dev_config.properties").getPath());
		
		try {
			String path = new ClassPathResource("properties/dev_config.properties").getFile().getAbsolutePath();
			
			Assert.assertNotNull(path);
			System.out.println(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getTransactionDetailTest() {
		
		OpenApiManager openApiManager = new OpenApiManagerImpl();
		TransactionDetail transactionDetail = null;
		
		try {
			transactionDetail = openApiManager.getPaymentTransactionDetail("TSTORE0004_20150731175443017804817193658", accessToken);
			System.out.println(objectMapper.writeValueAsString(transactionDetail));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(transactionDetail);
	}
	
	
	public void cancelPaymentTransactionTest() {
		OpenApiManager openApiManager = new OpenApiManagerImpl();
		
		Payer payer = new Payer();
		payer.setAuthkey("AUTHKEY");
		
		PaymentMethods paymentMethods = new PaymentMethods();
		paymentMethods.setAmount(1000);
		paymentMethods.setPaymentMethod("11");
		
		RefundTransactionRequest refundTransactionRequest = new RefundTransactionRequest();
		refundTransactionRequest.setAmount(1000);
		refundTransactionRequest.setFullRefund(true);
		refundTransactionRequest.setNote("member want to get a refund");
		refundTransactionRequest.setTid("TSTORE0004_20150731175443017804817193658");
		
		CancelRequest cancelRequest = new CancelRequest();
		cancelRequest.setPayer(payer);
		cancelRequest.setRefundTransactionRequest(refundTransactionRequest);
		
		CancelResponse cancelResponse = null;
		
		try {
			cancelResponse = openApiManager.cancelPaymentTransaction(cancelRequest, accessToken);
			System.out.println(objectMapper.writeValueAsString(cancelResponse));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(cancelResponse);
	}
	
	
}
