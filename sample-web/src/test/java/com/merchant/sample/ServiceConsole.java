package com.merchant.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.skplanet.openapi.external.framework.ManagerProducer;
import com.skplanet.openapi.external.oauth.OAuthAccessToken;
import com.skplanet.openapi.external.oauth.OAuthClientInfo;
import com.skplanet.openapi.external.oauth.OAuthManager;
import com.skplanet.openapi.external.payment.OpenApiManager;
import com.skplanet.openapi.vo.payment.FilePaymentHeader;
import com.skplanet.openapi.vo.payment.FilePaymentResult;
import com.skplanet.openapi.vo.payment.TransactionDetail;
import com.skplanet.openapi.vo.refund.CancelRequest;
import com.skplanet.openapi.vo.refund.CancelResponse;
import com.skplanet.openapi.vo.refund.RefundTransactionRequest;

public class ServiceConsole {
	
	public static void main(String ... v) {
		
		String accessToken = null;
		String path = null;
		String logPath = null;
		
		try {
			path = new ClassPathResource("properties/config.properties").getFile().getAbsolutePath();
			logPath = new ClassPathResource("properties/log4j.properties").getFile().getAbsolutePath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// Get New AccessToken
		OAuthClientInfo oauthClientInfo = new OAuthClientInfo(
				"84xK38rx9iCrFRJVOynsRA0MT0o3LTs83OqDLEJf5g0=", "GS1qrhoHMJWpmS6QwLNaG5NcFWFqzh5TrmY5476a2nA=", "client_credentials");
		OAuthManager oauthManager = ManagerProducer.getFactory(logPath).getOAuthManager(oauthClientInfo);
		
		try {
			oauthManager.setPropertyFile(path);
			OAuthAccessToken accessTokenObj = oauthManager.createAccessToken();
			accessToken = accessTokenObj.getAccessToken();
			System.out.println("AccessToken >>>" + accessToken);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Create File Payment
		OpenApiManager service = ManagerProducer.getFactory(logPath).getOpenApiManager();
		try {
			service.setPropertyFile(path);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		FilePaymentHeader filePaymentHeader = new FilePaymentHeader();
		filePaymentHeader.setVerBulkPay("1");
		filePaymentHeader.setBid("skplanet");
		filePaymentHeader.setNotiUrl("");
		filePaymentHeader.setCntTotalTrans(Integer.toString(1));
		filePaymentHeader.setPriority("Instant");
		
		File file = new File("res/sample.pay");
		
		System.out.println(">>>" + file.getAbsolutePath() + ">>>" + file.exists());
		String jobId = null;
		try {
			
			FilePaymentResult result = 
					service.createFilePayment(filePaymentHeader, file, accessToken);
			
			System.out.println(result.getJobId() + "|" + result.getResultCode() + 
					"|" + result.getResultMsg() + "|" + result.getWaitingJobs());
			jobId = result.getJobId();
			
			// Get File Payment Job Result
			Thread.sleep(2000);
			
			File resFile = new File("d:/samplefolder/sample-web/resFileinMerchant.txt");
			if (!resFile.createNewFile()) {
				System.exit(-1);
			}
			
			service.getFilePaymentJobStatus(jobId, resFile, accessToken);
			System.out.println("Path >>> " + resFile.getAbsolutePath());
			printFile(resFile);
			resFile.delete();
			
			// Get Payment Transaction Details
			TransactionDetail txDetail = service.getPaymentTransactionDetail("tx_my00001111", accessToken);
			System.out.println("Transaction detail result >>>" + txDetail.getResultCode() + "|" + txDetail.getResultMsg() + 
					"|" + txDetail.getPayer());
			
			// Cancel Payment Transaction
			CancelRequest cancelRequest = new CancelRequest();
			RefundTransactionRequest refundTxReq = new RefundTransactionRequest();
			refundTxReq.setAmount(1000);
			refundTxReq.setTid("tx_my00001111");
			cancelRequest.setRefundTransactionRequest(refundTxReq);
					
			CancelResponse cancelRes = service.cancelPaymentTransaction(cancelRequest, accessToken);
			System.out.println("Cancel response result >>> " + cancelRes.getResultCode() + "|" + cancelRes.getResultMsg());
			
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