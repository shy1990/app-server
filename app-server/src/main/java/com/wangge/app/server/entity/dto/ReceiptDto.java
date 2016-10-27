package com.wangge.app.server.entity.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



public class ReceiptDto {
	private String userName;
	
	private int receiptType;
	
	private Float amountCollected;
	@JsonFormat(pattern="yyyy.MM.dd HH:mm",timezone = "GMT+8")
	private Date createTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}

	public Float getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(Float amountCollected) {
		this.amountCollected = amountCollected;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		this.createTime = sdf.parse(createTime);
	}
	
	
	
	
}