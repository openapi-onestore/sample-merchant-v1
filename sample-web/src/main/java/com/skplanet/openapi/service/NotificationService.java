package com.skplanet.openapi.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.openapi.external.notification.NotiReceive;
import com.skplanet.openapi.external.notification.NotiVerifyResult;
import com.skplanet.openapi.request.outbound.PayplanetClient;

@Service("NotificationService")
public class NotificationService {

	@Autowired
	private PayplanetClient payplanetClient;
	
	/**
	 * 
	 * @param param
	 * @return // TODO return 타입을 처리 방식에 맞게 변경
	 */
	public String processNoti(NotiReceive notificationResult) {
		// TODO Notification 처리
		// TODO 반드시 비동기로 처리되어야함.
		String result = null;
		
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("notifyVersion", notificationResult.getNotifyVersion());
			paramMap.put("event", notificationResult.getEvent());
			paramMap.put("status", notificationResult.getStatus());
			paramMap.put("jobId", notificationResult.getJobId());
			paramMap.put("updateTime", notificationResult.getUpdateTime());
			requestNotificationResult(paramMap);

			NotiVerifyResult notiResult = new NotiVerifyResult();
			notiResult.setResultCode("0000");
			notiResult.setResultMsg("SUCCESS");
			result = payplanetClient.getObjectJsonString(notiResult);
			payplanetClient.getNotificationVerifyResult(notificationResult, "bigcharging_notify_verification", notificationResult.getJobId());
			
		} catch (Exception e) {
			e.printStackTrace();
			NotiVerifyResult exceptionResult = new NotiVerifyResult();
			exceptionResult.setResultCode("9999");
			exceptionResult.setResultMsg("Exception occur");
			result = payplanetClient.getObjectJsonString(exceptionResult);
		}
		return result;
	}

	/**
	 * @param param
	 *            T Store Payment Transaction Notification Integration Guide
	 *            [Specification] Bulk Payment Notification Listener 참조
	 * 
	 * @return
	 * @throws Exception 
	 */
//	public String processBulkJob(NotificationResult notificationResult) throws Exception {
//		// TODO bulk job notification 처리를 한다.
//		
//		// TODO OpenAPI 서버에 Verify요청을 한다.
//		String result = null;
//		
//		
//		requestVerify(notificationResult, "bigcharging_notify_verification");
//		verifyParam.put("listenerType", listenerType);
//		
//		if (result == null)
//			result = "Fail";
//		
//		return result;
//	}
	
	public void requestNotificationVerify(Map<String, String> param) throws Exception {		
		payplanetClient.updateNotificationVerify(param);
	}
	
	public void requestNotificationResult(Map<String, String> param) throws Exception {
		payplanetClient.insertNotificationResult(param);
	}
	
}
