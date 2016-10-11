package com.wangge.app.server.vo;

import java.util.List;
import java.util.Set;

import com.wangge.app.server.entity.dto.ReceiptDto;

public class ReceiptVo {

	
	private Float arrears;
	
	private Float payAmount;
	
	private List<ReceiptDto> receipts;

	public Float getArrears() {
		return arrears;
	}

	public void setArrears(Float arrears) {
		this.arrears = arrears;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public List<ReceiptDto> getReceipts() {
		return receipts;
	}

	public void setReceipts(List<ReceiptDto> receipts) {
		this.receipts = receipts;
	}


}
