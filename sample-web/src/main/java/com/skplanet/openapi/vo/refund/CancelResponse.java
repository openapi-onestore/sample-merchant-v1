package com.skplanet.openapi.vo.refund;

public class CancelResponse {

	private String resultCode;
	private String resultMsg;
	private RefundTransactionResponse refundTransactionResponse;
	
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public RefundTransactionResponse getRefundTransactionResponse() {
		return refundTransactionResponse;
	}

	public void setRefundTransactionResponse(RefundTransactionResponse refundTransactionResponse) {
		this.refundTransactionResponse = refundTransactionResponse;
	}
	
}
