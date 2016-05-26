package com.skplanet.openapi.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FilePaymentDAOImpl extends SqlSessionDaoSupport implements FilePaymentDAO {

	private static final Logger logger = LoggerFactory.getLogger(FilePaymentDAOImpl.class);
	
	@Resource(name="sqlSessionFactory")
	public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Resource(name="sqlSessionTemplate")
	public void setSuperSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<Map<String, String>> selectFilePaymentRequest() {
		
		List<Map<String,String>> result = null;
		
		try {
			result = getSqlSession().selectList("filePaymentRequest.selectFilePaymentRequest");
			System.out.println(result);
		} catch(Exception e) {
			logger.error("fail to load file payment request, error message : " + e.getMessage());
		}
		
		return result;
	}

	@Override
	public void insertFilePaymentRequest(Map<String, String> param) throws Exception {
		System.out.println(param);
		try {
			getSqlSession().insert("filePaymentRequest.insertFilePaymentRequest", param);
		} catch(Exception e) {
			logger.error("fail to insert file payment request, error message : " + e.getMessage());
			throw new Exception();
		}
	}

	@Override
	public void updateNotifiVerified(Map<String, String> param) throws Exception {
		try {
			getSqlSession().update("filePaymentRequest.updateNotificationVerify", param);
		} catch(Exception e) {
			logger.error("fail to insert file payment request, error message : " + e.getMessage());
			throw new Exception();			
		}
	}

}
