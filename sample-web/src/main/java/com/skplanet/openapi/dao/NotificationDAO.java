package com.skplanet.openapi.dao;

import java.util.List;
import java.util.Map;

public interface NotificationDAO {

	List<Map<String, String>> selectNotificationResult(Map<String, String> param);
	void addNotificationResult(Map<String, String> param) throws Exception;
	
}
