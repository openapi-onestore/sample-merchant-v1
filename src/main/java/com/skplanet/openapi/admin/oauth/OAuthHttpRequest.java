package com.skplanet.openapi.admin.oauth;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;

public class OAuthHttpRequest implements Callable<String>{

	private String callUrl = null;
	private String param = null;	
	private Map<String, String> headers = null;

	private final String delemeter = "&";
	private final String equal = "=";
		
	private StatusLine statusLine;
	
	private boolean isJsonRequest = false;
	
	@Override
	public String call() throws Exception {
		String result = null;
				
		if (!validation())
			return null;
		
		if (isParamEmpty())
			return null;
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(callUrl);

		if (headers != null)
			addHeaders(httpPost);
		
		HttpEntity httpEntity = null;
		
		if (isJsonRequest) {
			httpEntity = new StringEntity(param, ContentType.APPLICATION_JSON);
		} else {
			httpEntity = new StringEntity(param, ContentType.APPLICATION_FORM_URLENCODED);			
		}
		
		httpPost.setEntity(httpEntity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		try {
			this.statusLine = response.getStatusLine();
			result = EntityUtils.toString(response.getEntity());
		} finally {
			response.close();
		}
		
		return result;
	}
	
	public void setHeader(Map<String, String> header) {
		this.headers = header;
	}
	
	public void setParamMap(Map<String, String> paramMap) {		
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = paramMap.keySet().iterator();
		
		while (iterator.hasNext()) {
			String key = iterator.next();
			sb.append(key).append(equal).append(paramMap.get(key));
			
			if (iterator.hasNext()) {
				sb.append(delemeter);
			}
		}
		this.param = sb.toString();
	}
	
	public void setParam(String param) {
		this.param = param;
		this.isJsonRequest = true;
	}
	
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}
	
	private boolean validation() {
		if (callUrl == null)
			return false;
		return true;
	}
	
	private boolean isParamEmpty() {
		if (param == null)
			return true;
		return false;
	}
	
	public String getStatus() {
		if (statusLine != null) {
			return statusLine.toString();
		} else {
			return null;
		}
	}
	
	public void setJsonRequest(boolean isJson) {
		this.isJsonRequest = isJson;
	}
	
	private void addHeaders(AbstractHttpMessage httpMessage) {
		for (String key : headers.keySet()) {
			System.out.println("add Headers : " + key + " value : " + headers.get(key));
			httpMessage.setHeader(key, headers.get(key));
		}
	}
}
