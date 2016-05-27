package com.skplanet.openapi.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skplanet.openapi.service.PaymentService;
import com.skplanet.openapi.vo.payment.FilePaymentResult;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory
			.getLogger(PaymentController.class);
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value = "/request", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> requestFilePaymentReqList() {
		
		logger.debug("Request FilePaymentRequestList ");		
		List<Map<String, String>> result = paymentService.requestFilePaymentRequestList();
		
		return result;
	}
	
	@RequestMapping(value = "/bulkjob", method = RequestMethod.POST)
	public String requestBulkJobUploadFromFile(
			@RequestParam("bulkjob") MultipartFile request) {
		
		FilePaymentResult filePaymentResult = paymentService.requestFilePayment(request);
		String result = null;
		
		if (filePaymentResult.getResultCode().equals("0000")) {
			// bulkJob file upload success
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm");
			result = "upload_file=" + request.getOriginalFilename() + "&upload_date=" + sdf.format(Calendar.getInstance().getTime());
			result = paymentService.requestFilePaymentRequest(filePaymentResult, result);
		} else {
			result = "File payment request failure. code : " + filePaymentResult.getResultCode() + " reason : " + filePaymentResult.getResultMsg();
		}
		
		return "redirect:../admin/main?uploadResult="+result;
	}
	
	@RequestMapping(value = "/result/{jobid}", method=RequestMethod.GET)
	@ResponseBody
	public String requestResultFile(@PathVariable String jobid) {
		
		String result = null;
		
		if (jobid == null) {
			return "Job ID is null, check your job id";
		}
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("jobId", jobid);
		
//		NotificationResult notificationResult = paymentService.requestNotificationResult(hashMap);
//		hashMap.put("verifySign", notificationResult.getVerifySign());
		
		System.out.println(hashMap);
		
		result = paymentService.requestFilePaymentResultFile(hashMap);
		
		String[] resultRow = result.split("\n");
		if (resultRow.length >= 100) {
			result = "Row number is higher than 100. Please file download interface!";
		}
		
		return result;
	}
	
	@RequestMapping(value = "/transaction/{tid}", method=RequestMethod.GET)
	@ResponseBody
	public String requestTxidInfo(@PathVariable String tid) {
		
		String result = null;
		
		if (tid == null) {
			return "Job ID is null, check your job id";
		}
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("tid", tid);		
		
		result = paymentService.requestTidInformation(hashMap);
		
		return result;
	}
	
	@RequestMapping(value = "/refund/{tid}", method=RequestMethod.GET)
	@ResponseBody
	public String requestCancelTransaction(@PathVariable String tid) {
		
		Map<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("tid", tid);
		
		String transactionInfo = paymentService.requestTidInformation(hashMap);
		String result = null;
		
		if (transactionInfo.startsWith("result=FAIL")) {
			return "Tid information request is failure";
		}
		
		hashMap.clear();
		hashMap.put("transactionInfo", transactionInfo);
		
		result = paymentService.requestRefund(hashMap);
		
		return result;
	}	
	
}