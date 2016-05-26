package com.skplanet.openapi.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.openapi.external.notification.NotiReceive;
import com.skplanet.openapi.service.NotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {

	final static Logger logger = LoggerFactory.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@RequestMapping(value = "/noti_listener", method = RequestMethod.POST)
	@ResponseBody
	public String notiListener(@RequestBody NotiReceive notificationResult) {
		
		String result = null;
		try {
			// TODO request param parse
			logger.debug("Notification received!");
			System.out.println("Notification original Data : " + objectMapper.writeValueAsString(notificationResult));
			result = notificationService.processNoti(notificationResult);
			
			return result;
		} catch (Exception e) {
			// TODO Fail Response
			return "Exception in notification listener!!";
		}
	}
	
	
	
}
