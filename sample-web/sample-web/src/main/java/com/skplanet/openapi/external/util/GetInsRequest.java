package com.skplanet.openapi.external.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GetInsRequest extends HttpRequest<Void, InputStream> {
	
	CloseableHttpClient httpClient = null;
	CloseableHttpResponse response = null;
	InputStream inputStream = null;
	
	@Override
	public InputStream executeRequest() throws Exception {
		
		// Url check
		checkCallurl();
		
		httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(callUrl);
		
		if (validationHeader()) {
			addHeaders(httpGet);
		}
		
		response = httpClient.execute(httpGet);
		
		try {
			checkHttpStatus(response);
			HttpEntity httpEntity = response.getEntity();
			inputStream = httpEntity.getContent();
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return inputStream;
	}
	
	@Override
	public void setParameter(Void param) {
		
	}
	
	public void closeInputStream() {
		try {
			inputStream.close();
			httpClient.close();
			response.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
