package com.skplanet.openapi.external.util;

import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HttpRequest<T, R> {

	protected static final Logger logger = LoggerFactory
			.getLogger(HttpRequest.class);
	protected String callUrl = null;
	protected String parameter = null;
	protected Map<String, String> headers = null;
	protected StatusLine statusLine;

	abstract public void setParameter(T parameter);

	abstract public R executeRequest() throws Exception;

	protected void checkCallurl() throws Exception {
		if (!validationUrl()) {
			throw new Exception("Call url is not valid...");
		}
	}

	protected void checkHttpStatus(CloseableHttpResponse response)
			throws Exception {
		statusLine = response.getStatusLine();
		logger.info("Http result code : " + statusLine.getStatusCode());
		if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
			throw new Exception("Http request fail - [Status code : "
					+ statusLine.getStatusCode() + ", Message from server : "
					+ EntityUtils.toString(response.getEntity()) + "]");
		}
	}

	protected void addHeaders(AbstractHttpMessage httpMessage) {
		for (String key : headers.keySet()) {
			logger.info("Add headers : " + key + " value : " + headers.get(key));
			httpMessage.setHeader(key, headers.get(key));
		}
	}

	private boolean validationUrl() {
		boolean result = true;
		if (callUrl == null)
			result = false;
		return result;
	}

	protected boolean validationParameter() {
		boolean result = true;
		if (parameter == null)
			result = false;
		return result;
	}

	protected boolean validationHeader() {
		boolean result = true;
		if (headers == null)
			result = false;
		return result;
	}

	protected String getParameter() {
		return this.parameter;
	}

	protected String getCallUrl() {
		return this.callUrl;
	}

	public void setHeader(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

}
