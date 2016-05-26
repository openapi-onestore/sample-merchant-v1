package com.skplanet.openapi.test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skplanet.openapi.dao.NotificationDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/web.xml",
		"file:src/main/resources/spring/mybatis-context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
		})
public class NotificationDaoTest {
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	private Map<String, String> hashMap;
	
	@Before
	public void setUp() {
		hashMap = new HashMap<String, String>();
	}
	
	@Test
	public void insertNotificationResult() {
		hashMap.put("jobId", "145");
		hashMap.put("event", "bulkJob");
		hashMap.put("status", "initialized");
		hashMap.put("updateDate", "20150729080258");
		hashMap.put("notiVersion", "1.0");
		
		try {
			notificationDAO.addNotificationResult(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		hashMap.clear();
		hashMap.put("jobId", "145");
		List<Map<String, String>> list = notificationDAO.selectNotificationResult(hashMap);
		
		Assert.assertEquals(1, list.size());
		hashMap = list.get(0);
		System.out.println(hashMap);
		
		Assert.assertEquals(hashMap.get("jobId"), "145");
		Assert.assertEquals(hashMap.get("event"), "bulkJob");
		Assert.assertEquals(hashMap.get("status"), "initialized");
		Assert.assertEquals(hashMap.get("updateDate"), "20150729080258");
		Assert.assertEquals(hashMap.get("notiVersion"), "1.0");
		
	}
	
	
	
}
