package com.skplanet.openapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.skplanet.openapi.service.PaymentService;
import com.skplanet.openapi.vo.payment.TransactionDetail;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	final static Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private PaymentService paymentService;	
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public ModelAndView requestAdminMain(@RequestParam(value="uploadResult",required=false) String result) {
		System.out.println(result);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin_index");
		
		if (result == null) {
			result = "You don't transmit some file";
		}
		modelAndView.addObject("uploadResult", result);
		
		return modelAndView;
	}

	@RequestMapping(value="/result/{jobId}", method=RequestMethod.GET)
	public ModelAndView requestAdminPaymentResult(@PathVariable String jobId) {
		System.out.println(jobId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin_result");
		
		if (jobId == null) {
			jobId = "null";
			return modelAndView;
		}
		
		modelAndView.addObject("jobid", jobId);
		
		return modelAndView;
	}

	@RequestMapping(value="/result/file/{jobId}", method=RequestMethod.GET)
	public ModelAndView requestAdminPaymentResultFile(@PathVariable String jobId) {
		System.out.println(jobId);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin_result");
		
		if (jobId == null) {
			jobId = "You don't transmit some file";
			return modelAndView;
		}
		
		modelAndView.addObject("jobid", jobId);
		
		return modelAndView;
	}

	@RequestMapping(value="/transaction/{tid}", method=RequestMethod.GET)
	public ModelAndView requestAdminPaymentTransactionInfo(@PathVariable String tid) {
		System.out.println(tid);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("admin_tid_info");
		
		if (tid == null) {
			tid = "Please upload your file";
			return modelAndView;
		}
				
		Map<String, String> param = new HashMap<String, String>();
		param.put("tid", tid);
		
		TransactionDetail transactionDetail = paymentService.requestTidInformationObject(param);
		
		if (transactionDetail != null) {
			
			if (transactionDetail.getResultCode().equals("0000")) {
				// TransactionDetail
				modelAndView.addObject("resultCode", transactionDetail.getResultCode());
				modelAndView.addObject("resultMsg", transactionDetail.getResultMsg());
				
				// Payer
				modelAndView.addObject("authkey", transactionDetail.getPayer().getAuthKey());
				
				// TransactionInfo
				modelAndView.addObject("status", transactionDetail.getPaymentTransactionInfo().getStatus());			
				modelAndView.addObject("reason", transactionDetail.getPaymentTransactionInfo().getReason());			
				modelAndView.addObject("message", transactionDetail.getPaymentTransactionInfo().getMessage());			
				modelAndView.addObject("latestUpdate", transactionDetail.getPaymentTransactionInfo().getLastestUpdate());
				modelAndView.addObject("tid", transactionDetail.getPaymentTransactionInfo().getTid());
				modelAndView.addObject("amount", transactionDetail.getPaymentTransactionInfo().getAmount());
				modelAndView.addObject("description", transactionDetail.getPaymentTransactionInfo().getDescription());
				
				modelAndView.addObject("goods",transactionDetail.getPaymentTransactionInfo().getGoods());
				modelAndView.addObject("paymentMethods",transactionDetail.getPaymentTransactionInfo().getPaymentMethods());				
			} else {
				modelAndView.addObject("resultCode", transactionDetail.getResultCode());
				modelAndView.addObject("resultMsg", transactionDetail.getResultMsg());				
			}
			
		}
		
		return modelAndView;
	}	
	
}
