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

import com.skplanet.openapi.dao.FilePaymentDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/web.xml",
		"file:src/main/resources/spring/mybatis-context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
		})
public class BulkJobDaoTest {
	
	@Autowired
	private FilePaymentDAO filePaymentDAO;
	
	private Map<String, String> hashMap;
	
	@Before
	public void setUp() {
		hashMap = new HashMap<String, String>();
	}
	
	@Test
	public void getBulkJobRequest() {
		List<Map<String,String>> map = filePaymentDAO.selectFilePaymentRequest();
		
		Assert.assertEquals(1, map.size());
	}
	
	@Test
	public void insertBulkJobRequest() {
		List<Map<String,String>> map = filePaymentDAO.selectFilePaymentRequest();
		
		Assert.assertEquals(map.size(), 0);
		
		hashMap.clear();
		hashMap.put("status", "SUCCESS");
		hashMap.put("reason", "0000");
		hashMap.put("waiting_jobs", "10");
		hashMap.put("job_id", "001234");
		hashMap.put("upload_file", "bulkjob.txt");
		hashMap.put("upload_date", "20150511");
		
		try {
			filePaymentDAO.insertFilePaymentRequest(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map = filePaymentDAO.selectFilePaymentRequest();
		
		Assert.assertEquals(1, map.size());
		
		System.out.println(map);
	}
	
}
