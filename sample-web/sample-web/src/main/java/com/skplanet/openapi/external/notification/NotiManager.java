package com.skplanet.openapi.external.notification;


public interface NotiManager {

	NotiReceive receiveNotificationFromServer(String notificationResult) throws NotiException;
	NotiVerifyResult requestNotificationVerification(NotiReceive notificationResult, String listenerType, String accessToken) throws NotiException;
	
}
