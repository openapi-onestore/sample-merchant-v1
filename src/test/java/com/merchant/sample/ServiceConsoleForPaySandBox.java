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

public class ServiceConsoleForPaySandBox {

	private static String getLogPath() throws IOException{
		return new ClassPathResource("properties/log4j.properties").getFile().getAbsolutePath();
	}

	private static String getAccessToken() throws Exception{

		// JW's token for dev server
//		String devJwToken = "84xK38rx9iCrFRJVOynsRA0MT0o3LTs83OqDLEJf5g0=";
//		String devJwToken2 = "GS1qrhoHMJWpmS6QwLNaG5NcFWFqzh5TrmY5476a2nA=";
		// Token for Release
//		String devJwToken = "VTFqvgcLbyyFdG2wZtEzBgbRB++RAp4WLWURY7g8Rvg=";
//		String devJwToken2 = "dxHw4bH8HU0f0+pmVsuvvfATdD8OXr85nL1ZTkmdVig=";
		
		// SandBox, 00686847
		final String clientId = "dCzM2NuHr1dOBQVqSpJO2VKzqguuuwgrddcMrXgFEyod0wDBNfm1YfCRKRsZ5CQ3";
		final String clientSecret = "6XV9AWDxsarQZpF8cF+425zQg96NYhziAB7pdsD7/5E=";
		
		// Get New AccessToken
		final OAuthClientInfo oauthClientInfo = new OAuthClientInfo(clientId, clientSecret, "client_credentials");

		final OAuthManager oauthManager = ManagerProducer.getFactory(Environment.SANDBOX, getLogPath()).getOAuthManager(oauthClientInfo);
		
		final OAuthAccessToken accessTokenObj = oauthManager.createAccessToken();
		final String accessToken = accessTokenObj.getAccessToken();

		System.out.println("AccessToken >>>" + accessToken);

		return accessToken;
	}

	public static void main(String ... v) {

		try {

			final String accessToken = getAccessToken();

			// Create File Payment
			final OpenApiManager service = ManagerProducer.getFactory(Environment.SANDBOX, getLogPath()).getOpenApiManager();
					
			
			final FilePaymentHeader filePaymentHeader = new FilePaymentHeader();
			filePaymentHeader.setVerBulkPay("1");
			filePaymentHeader.setBid("skplanet");
//			filePaymentHeader.setNotiUrl("");
			filePaymentHeader.setCntTotalTrans(1);
			filePaymentHeader.setPriority("Instant");

//			File file = new File("res/sample_release.pay");
			File file = new File("res/sample_sandbox.pay");

			System.out.println(">>>" + file.getAbsolutePath() + ">>>" + file.exists());
			String jobId = null;

			FilePaymentResult result = service.createFilePayment(filePaymentHeader, file, accessToken);

			System.out.println(result.getJobId() + "|" + result.getResultCode() +
					"|" + result.getResultMsg() + "|" + result.getWaitingJobs());
			jobId = result.getJobId();

			// Get File Payment Job Result
			Thread.sleep(3000);

			File resFile = new File("result/resFileinMerchant"+System.currentTimeMillis()+".txt");
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

		} catch (Exception e1) {
			e1.printStackTrace();
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
