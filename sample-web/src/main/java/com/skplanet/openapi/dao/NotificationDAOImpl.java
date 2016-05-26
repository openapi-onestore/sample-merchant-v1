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
public class NotificationDAOImpl extends SqlSessionDaoSupport implements NotificationDAO {

	private static final Logger logger = LoggerFactory.getLogger(NotificationDAOImpl.class);
	
	@Resource(name="sqlSessionFactory")
	public void setSuperSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	@Resource(name="sqlSessionTemplate")
	public void setSuperSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public List<Map<String, String>> selectNotificationResult(
			Map<String, String> param) {
		List<Map<String,String>> result = null;
		
		try {
			result = getSqlSession().selectList("notificationResult.selectNotificationResult", param);
		} catch(Exception e) {
			logger.error("fail to load notification result, error message : " + e.getMessage());
		}
		
		return result;		
	}

	@Override
	public void addNotificationResult(Map<String, String> param)
			throws Exception {
		try {
			getSqlSession().insert("notificationResult.insertNotificationResult",param);
		} catch(Exception e) {
			logger.error("fail to insert notification result, error message : " + e.getMessage());
			throw new Exception();
		}
	}

}
