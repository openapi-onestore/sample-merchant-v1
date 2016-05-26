package com.skplanet.openapi.external.notification;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.skplanet.openapi.external.notification.NotiException.Noti;
import com.skplanet.openapi.external.payment.OpenApiManagerImpl.OPEN_API_MODE;
import com.skplanet.openapi.external.util.JsonPostRequest;

public class NotiManagerImpl implements NotiManager {

	private static final Logger logger = LoggerFactory.getLogger(NotiManagerImpl.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private final String developmentVerifyUrl = "http://172.21.60.142/openapi/v1/payment/notification/verify";
	private final String sandboxVerifyUrl = "http://172.21.60.142/openapi/v1/payment/notification/verify";
	private final String releaseVerifyUrl = "http://172.21.60.142/openapi/v1/payment/notification/verify";
	private String verifyUrl = "http://172.21.60.142/openapi/v1/payment/notification/verify";
	
	public NotiManagerImpl() { }
	public NotiManagerImpl(String logPath) {
		if (logPath != null) {
			if (logPath.length() > 0) {
				PropertyConfigurator.configure(logPath);
			}
		}
	}

	public NotiManagerImpl(OPEN_API_MODE mode,String logPath) {
		if (mode == OPEN_API_MODE.DEVELOPMENT) {
			this.verifyUrl = developmentVerifyUrl;
		} else if (mode == OPEN_API_MODE.SANDBOX) {
			this.verifyUrl = sandboxVerifyUrl;
		} else {
			this.verifyUrl = releaseVerifyUrl;			
		}
		if (logPath != null) {
			if (logPath.length() > 0) {
				PropertyConfigurator.configure(logPath);
			}
		}
	}
	
	@Override
	public NotiReceive receiveNotificationFromServer(String notificationResult) throws NotiException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		NotiReceive notiReceive = null;
		
		try {
			notiReceive = objectMapper.readValue(objectMapper.writeValueAsString(notificationResult),NotiReceive.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotiException(Noti.PARAMETER_PARSING_ERROR, "Json Parsing Error");
		};
		
		return notiReceive;
	}
	
	@Override
	public NotiVerifyResult requestNotificationVerification(NotiReceive notiReceive, String listenerType, String accessToken) throws NotiException {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("listenerType", listenerType);
		paramMap.put("notifyVersion", notiReceive.getNotifyVersion());
		paramMap.put("event", notiReceive.getEvent());
		paramMap.put("status", notiReceive.getStatus());
		paramMap.put("jobId", notiReceive.getJobId());
		paramMap.put("updateTime", notiReceive.getUpdateTime());
		
		JsonPostRequest jsonPostRequest = new JsonPostRequest();
		String jsonParam = null;
		
		try {
			jsonParam = objectMapper.writeValueAsString(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paramMap.clear();
		paramMap.put("Authorization", "Bearer "+accessToken);
		jsonPostRequest.setHeader(paramMap);
		jsonPostRequest.setCallUrl(verifyUrl);
		jsonPostRequest.setParameter(jsonParam);		
		
		String result = null;
		NotiVerifyResult notiVerifyResult = null;
		
		try {
			result = jsonPostRequest.executeRequest();
			logger.info("Notification verify Result" + result);
			notiVerifyResult = objectMapper.readValue(result, NotiVerifyResult.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotiException(Noti.NOTI_JOB_EXECUTE_FAIL_ERROR, "NotificationVerification reuqest execute error");
		}
		
		return notiVerifyResult;
	}
	
}
