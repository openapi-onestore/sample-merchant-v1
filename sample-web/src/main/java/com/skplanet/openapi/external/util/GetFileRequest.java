package com.skplanet.openapi.external.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class GetFileRequest extends HttpRequest<File, Void> {

	private File targetFile = null;
	
	@Override
	public Void executeRequest() throws Exception {
		
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
			HttpEntity httpEntity = response.getEntity();
			
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), Charset.forName("UTF-8")));
			FileWriter fileWriter = new FileWriter(targetFile);
			
			String buffer = null;
			while( (buffer=bufferedReader.readLine()) != null) {
				fileWriter.append(buffer).append("\n");
				fileWriter.flush();
			}
			fileWriter.close();
			bufferedReader.close();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			response.close();
			httpClient.close();
		}
		
		return null;
	}
	
	@Override
	public void setParameter(File targetFile) {
		this.targetFile = targetFile;
	}
	
}
