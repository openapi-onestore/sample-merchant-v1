package com.skplanet.openapi.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class HttpRequest implements Callable<String>{

	private String callUrl = null;
	private String param = null;
	private Map<String, String> paramMap = null;
	List<HttpHeader> header = null;
	
	private HttpClient httpClient;
	
	@Override
	public String call() throws Exception {
		String result = null;
		if (httpClient == null)
			httpClient = new HttpClient();
		
		if (!validation())
			return null;
		
		if (hasAdditionalHeader())
			httpClient.setHeaders(header);
		
		if (isParamEmpty()) {
			result = httpClient.get(callUrl);
		}
		
		result = (param == null) ? httpClient.post(callUrl, paramMap) : httpClient.post(callUrl, param);
		
		return result;
	}
	
	public void setHeader(List<HttpHeader> header) {
		this.header = header;
	}
	
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}
	
	private boolean validation() {
		if (callUrl == null)
			return false;
		return true;
	}
	
	private boolean hasAdditionalHeader() {
		if (header == null)
			return false;
		return true;
	}
	
	private boolean isParamEmpty() {
		if (param == null && paramMap == null)
			return true;
		return false;
	}
}
