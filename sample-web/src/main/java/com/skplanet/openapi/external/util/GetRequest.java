package com.skplanet.openapi.external.util;

import java.nio.charset.Charset;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetRequest extends HttpRequest<String, String> {
	
	@Override
	public String executeRequest() throws Exception {
		String result = null;
		
		// Url check
		checkCallurl();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(callUrl);
		
		if (validationHeader()) {
			addHeaders(httpGet);
		}
		
		CloseableHttpResponse response = httpClient.execute(httpGet);
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
	public void setParameter(String param) {
		if (callUrl != null) {
			callUrl.concat(param);
		}
	}

}
