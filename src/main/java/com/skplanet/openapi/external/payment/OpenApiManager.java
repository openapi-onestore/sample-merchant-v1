package com.skplanet.openapi.external.payment;

import java.io.File;
import java.io.InputStream;

import com.skplanet.openapi.vo.payment.FilePaymentHeader;
import com.skplanet.openapi.vo.payment.FilePaymentResult;
import com.skplanet.openapi.vo.payment.TransactionDetail;
import com.skplanet.openapi.vo.refund.CancelRequest;
import com.skplanet.openapi.vo.refund.CancelResponse;

public interface OpenApiManager {
	
	FilePaymentResult createFilePayment(FilePaymentHeader filePaymentHeader, File file, String accessToken) throws OpenApiException;
//	public FilePaymentResult createFilePayment(List<File> paymentFileList);
	public void getFilePaymentJobStatus(String jobId, File targetFile, String accessToken) throws OpenApiException;
	public InputStream getFilePaymentJobStatus(String jobId, String accessToken) throws OpenApiException;
	TransactionDetail getPaymentTransactionDetail(String tid, String accessToken) throws OpenApiException;
	CancelResponse cancelPaymentTransaction(CancelRequest cancelRequest, String accessToken) throws OpenApiException;
	
}
