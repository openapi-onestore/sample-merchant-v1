package com.skplanet.openapi.external.util;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class KvpPostRequest extends HttpRequest<Map<String, String>, String> {

	private final String delemeter = "&";
	private final String equal = "=";
	
	@Override
	public String executeRequest() throws Exception {
		String result = null;
		
		// Url check
		checkCallurl();
		
		if (!validationParameter()) {
			return null;
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(callUrl);
		
		if (validationHeader())
			addHeaders(httpPost);
		
		HttpEntity httpEntity = null;
		httpEntity = new StringEntity(parameter, ContentType.APPLICATION_FORM_URLENCODED);
		
		httpPost.setEntity(httpEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		
		try {
			checkHttpStatus(response);
			result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			response.close();
			httpClient.close();
		}
		
		return result;
	}
	
	@Override
	public void setParameter(Map<String,String> paramMap) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = paramMap.keySet().iterator();
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			sb.append(key).append(equal).append(paramMap.get(key));
			
			if (iterator.hasNext()) {
				sb.append(delemeter);
			}
		}
		this.parameter = sb.toString();
	}
	
}
