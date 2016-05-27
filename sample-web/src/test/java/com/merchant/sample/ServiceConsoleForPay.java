package com.merchant.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.skplanet.openapi.external.framework.Environment;
import com.skplanet.openapi.external.framework.ManagerProducer;
import com.skplanet.openapi.external.oauth.OAuthAccessToken;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.payment.OpenApiManager;
import com.skplanet.openapi.vo.payment.FilePaymentHeader;
import com.skplanet.openapi.vo.payment.FilePaymentResult;

public class ServiceConsoleForPay {
	
	public static void main(String ... v) {
		
		// JW's token for dev server
//		String devJwToken = "84xK38rx9iCrFRJVOynsRA0MT0o3LTs83OqDLEJf5g0=";
//		String devJwToken2 = "GS1qrhoHMJWpmS6QwLNaG5NcFWFqzh5TrmY5476a2nA=";
		// Token for Release
		String devJwToken = "VTFqvgcLbyyFdG2wZtEzBgbRB++RAp4WLWURY7g8Rvg=";
		String devJwToken2 = "dxHw4bH8HU0f0+pmVsuvvfATdD8OXr85nL1ZTkmdVig=";
		
		// Token for Live and only for sample_release_with_iap.pay
		devJwToken = "G9AZgIWrLNTg/5sRlMzNU1nSh6PT7FMmNM2QuE1jdtwxUE2OgYLeZo2mcPcFMasO";
		devJwToken2 = "1wuASE1sopz28LO7xiTVyP0QWro5v0fhQ6eM8WtGYXE=";
		
		String accessToken = null;
		String logPath = null;
		
		try {
			logPath = new ClassPathResource("properties/log4j.properties").getFile().getAbsolutePath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Get New AccessToken
		OAuthClientInfo oauthClientInfo = new OAuthClientInfo(devJwToken, devJwToken2, "client_credentials");
		OAuthManager oauthManager = ManagerProducer.getFactory(Environment.LIVE, logPath).getOAuthManager(oauthClientInfo);
		
		try {
			OAuthAccessToken accessTokenObj = oauthManager.createAccessToken();
			accessToken = accessTokenObj.getAccessToken();
			System.out.println("AccessToken >>>" + accessToken);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Create File Payment
		OpenApiManager service = ManagerProducer.getFactory(Environment.LIVE, logPath).getOpenApiManager();
		
		FilePaymentHeader filePaymentHeader = new FilePaymentHeader();
		filePaymentHeader.setVerBulkPay("1");
		filePaymentHeader.setBid("skplanet");
		filePaymentHeader.setNotiUrl("");
		filePaymentHeader.setCntTotalTrans(1);
		filePaymentHeader.setPriority("Instant");
		
//		File file = new File("res/sample_release.pay");
		File file = new File("res/sample_release_with_iap.pay");
		
		System.out.println(">>>" + file.getAbsolutePath() + ">>>" + file.exists());
		String jobId = null;
		try {
			
			FilePaymentResult result = 
					service.createFilePayment(filePaymentHeader, file, accessToken);
			
			System.out.println(result.getJobId() + "|" + result.getResultCode() + 
					"|" + result.getResultMsg() + "|" + result.getWaitingJobs());
			jobId = result.getJobId();
			
			// Get File Payment Job Result
			Thread.sleep(3000);
			
			File resFile = new File("temp_iap_" + System.currentTimeMillis() + ".log");
			if (!resFile.createNewFile()) {
				System.exit(-1);
			}
			
			service.getFilePaymentJobStatus(jobId, resFile, accessToken);
			System.out.println("Path >>> " + resFile.getAbsolutePath());
			printFile(resFile);
			resFile.delete();
			
//			// Get Payment Transaction Details
//			TransactionDetail txDetail = service.getPaymentTransactionDetail("tx_my00001111", accessToken);
//			System.out.println("Transaction detail result >>>" + txDetail.getResultCode() + "|" + txDetail.getResultMsg() + 
//					"|" + txDetail.getPayer());
//			
//			// Cancel Payment Transaction
//			CancelRequest cancelRequest = new CancelRequest();
//			RefundTransactionRequest refundTxReq = new RefundTransactionRequest();
//			refundTxReq.setAmount(1000);
//			refundTxReq.setTid("tx_my00001111");
//			cancelRequest.setRefundTransactionRequest(refundTxReq);
//					
//			CancelResponse cancelRes = service.cancelPaymentTransaction(cancelRequest, accessToken);
//			System.out.println("Cancel response result >>> " + cancelRes.getResultCode() + "|" + cancelRes.getResultMsg());
			
			// Verify Transaction Notification
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void printFile(File file) {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = br.readLine()) != null) {
				System.out.println(">>> " + line);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
