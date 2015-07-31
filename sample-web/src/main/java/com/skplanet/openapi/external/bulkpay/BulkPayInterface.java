package com.skplanet.openapi.external.bulkpay;

import java.util.Map;

public interface BulkPayInterface {

	String createFilePayment(Map<String, String> paramMap);
	String getFilePaymentInfo(Map<String, String> paramMap);
	String getPaymentTransactionDetail(Map<String, String> paramMap);
	String cancelPaymentTransaction(Map<String, String> paramMap);
	
}
