package com.skplanet.openapi.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.skplanet.openapi.dao.FilePaymentDAO;
import com.skplanet.openapi.request.outbound.PayplanetClient;
import com.skplanet.openapi.vo.NotificationResult;
import com.skplanet.openapi.vo.PaymentJobInfo;
import com.skplanet.openapi.vo.payment.FilePaymentResult;
import com.skplanet.openapi.vo.payment.TransactionDetail;

@Service("paymentService")
public class PaymentService {

	private static final Logger logger = LoggerFactory
			.getLogger(PaymentService.class);
	
	private String localSavingFolder = "/home/1000720/sample_folder";
	
	@Autowired
	private FilePaymentDAO filePaymentDAO;
	
	@Autowired
	private PayplanetClient payplanetClient;

//	private List<String> columns = Arrays.asList(new String[] { "mid",
//			"billingToken", "pid", "pName", "orderNo", "amtReqPurchase",
//			"amtCarrier", "amtCreditCard", "amtTms" });

	public FilePaymentResult requestFilePayment(MultipartFile multipartFile) {
		FilePaymentResult filePaymentResult = null;
		File tmpFile = new File(getFilePaymentFormat());
		
		try {
			multipartFile.transferTo(tmpFile);
			int processCount = countLines(tmpFile.getAbsolutePath());
			PaymentJobInfo paymentJobInfo = new PaymentJobInfo(tmpFile.getAbsolutePath(), processCount);
			filePaymentResult = payplanetClient.createFilePayment(paymentJobInfo.getProcessingCount(), tmpFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filePaymentResult;
	}

	public String requestFilePaymentRequest(FilePaymentResult filePaymentResult, String params) {
		String result = null;
		
		Map<String, String> param = new HashMap<String, String>();
		logger.debug("Debug");
		
		// Upload date, filename parsing and add
		String[] temp = params.split("&");
		for (String temps : temp) {
			String[] kvp = temps.split("=");
			param.put(kvp[0].toLowerCase(), kvp[1]);
			System.out.println(kvp[0] + " " + kvp[1]);
		}
		
		param.put("status", "SUCCESS");
		param.put("result_code", filePaymentResult.getResultCode());
		param.put("result_msg", filePaymentResult.getResultMsg());
		param.put("waiting_jobs", String.valueOf(filePaymentResult.getWaitingJobs()));
		param.put("job_id", filePaymentResult.getJobId());
		
		try {
			System.out.println(param);
			payplanetClient.insertFilePaymentRequest(param);
			result = "result=SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			result = "result=FAIL&reason=" + e.getMessage();
		}

		return result;
	}

	public List<Map<String, String>> requestFilePaymentRequestList() {
		List<Map<String, String>> result = null;
		try {
			result = payplanetClient.selectFilePaymentRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public NotificationResult requestNotificationResult(Map<String, String> param) {
		NotificationResult notificationResult = null;
		try {
			notificationResult = payplanetClient.selectNotificationResult(param);
		} catch (Exception e) {
			e.printStackTrace();
			return notificationResult;
		}
		return notificationResult;
	}
	
	/**
	 * requestBulkJobResultFile
	 * @param param map
	 * @return String
	 * TODO::get File method
	 */
	public String requestFilePaymentResultFile(Map<String, String> param) {
		String result = null;
		
		try {
			result = payplanetClient.getFilePaymentResultFile(param);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = "result=FAIL&reason="+e.getMessage();
		}
		return result;
	}
	
	public String requestTidInformation(Map<String, String> param) {
		String result = null;
		String tid = param.get("tid");
		
		try {
			result = payplanetClient.getTidInformation(tid);
		} catch (Exception e) {
			e.printStackTrace();
			result = "result=FAIL&reason="+e.getMessage();
		}
		return result;
	}
	
	public TransactionDetail requestTidInformationObject(Map<String, String> param) {
		TransactionDetail transactionDetail = null;
		String tid = param.get("tid");
		
		try {
			String result = payplanetClient.getTidInformation(tid);
			if (result.length() >= 20) {
				ObjectMapper objectMapper = new ObjectMapper();
				transactionDetail = objectMapper.readValue(result, TransactionDetail.class);
			} else {
				transactionDetail = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return transactionDetail;
		}
		return transactionDetail;
	}
	
	public String requestRefund(Map<String, String> param) {
		String result = null;
		
		try {
			result = payplanetClient.getRefundInformation(param);
		} catch (Exception e) {
			e.printStackTrace();
			result = "result=FAIL&reason="+e.getMessage();
		}
		return result;		
	}
	
//	private PaymentJobInfo makeBulkFile(Map<String, String> param)
//			throws FileNotFoundException, IOException {
//		File tmpFile = new File(getBulkfileFormat());
//		Writer writer = new PrintWriter(tmpFile);
//		
//		List<Map<String, String>> bulkJobs = bulkJobDAO.selectBulkJob(param);
//		logger.debug("bulkjob size : " + bulkJobs.size());
//
//		int processingCount = bulkJobs.size();
//
//		Iterator<String> iterator = columns.iterator();
//		while (iterator.hasNext()) {
//			writer.write(iterator.next());
//			if (iterator.hasNext()) {
//				writer.write(",");
//			}
//		}
//
//		writer.write('\n');
//
//		for (Map<String, String> bulkJob : bulkJobs) {
//			iterator = columns.iterator();
//			while (iterator.hasNext()) {
//				String wr = bulkJob.get(iterator.next());
//				logger.debug("Writer : " + wr + " Size : " + bulkJob.size());
//				writer.write(wr);
//				if (iterator.hasNext()) {
//					writer.write(",");
//				}
//			}
//			writer.write('\n');
//		}
//		writer.close();
//
//		return new PaymentJobInfo(tmpFile.getAbsolutePath(), processingCount);
//	}

	private String getFilePaymentFormat() {
		return String.format(Locale.getDefault(), "%s/%s.bulk",
				localSavingFolder, UUID.randomUUID().toString());
	}

	private int countLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} finally {
			is.close();
		}
	}
	
	

}
