package com.skplanet.openapi.vo;

public class PaymentJobInfo {
	String filePath;
	int processingCount;

	public PaymentJobInfo(String filePath, int processingCount) {
		super();
		this.filePath = filePath;
		this.processingCount = processingCount;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getProcessingCount() {
		return processingCount;
	}
}
