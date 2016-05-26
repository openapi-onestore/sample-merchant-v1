package com.skplanet.openapi.external.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FilePostRequest extends HttpRequest<File, String> {
	
	private File sendFile = null;
	
	@Override
	public String executeRequest() throws Exception {
		String result = null;

		// Url check
		checkCallurl();
		
		// TODO: fail interface
		if (sendFile == null || !sendFile.canRead()) {
			throw new Exception("Send file is null or can't be read...");
		}
		
		HttpPost httpPost = new HttpPost(callUrl);
		if (validationHeader()) {
			addHeaders(httpPost);
		}
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(sendFile), sendFile.length(), ContentType.TEXT_PLAIN);
		reqEntity.setChunked(true);
		
		httpPost.setEntity(reqEntity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		
		try {
			checkHttpStatus(response);
			result = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			response.close();
			httpclient.close();
		}		
		
		return result;
	}
	
	@Override
	public void setParameter(File sendFile) {
		this.sendFile = sendFile;
	}

}
