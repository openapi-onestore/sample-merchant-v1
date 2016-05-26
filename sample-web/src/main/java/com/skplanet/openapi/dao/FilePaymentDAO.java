package com.skplanet.openapi.dao;

import java.util.List;
import java.util.Map;

public interface FilePaymentDAO {

	List<Map<String, String>> selectFilePaymentRequest();
	void insertFilePaymentRequest(Map<String, String> param) throws Exception;
	void updateNotifiVerified(Map<String, String> param) throws Exception;
	
}
