package com.wangge.app.server.entity.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BillHistoryDto {
	 @JsonFormat(pattern="dd",timezone = "GMT+8")
	private Date dateDay;
	
	private BigDecimal arrears;
	
	private BigDecimal shopNumber;
	
	private BigDecimal orderNumber;
	 @JsonFormat(pattern="yyyy.MM",timezone = "GMT+8")
	private Date dateYearMonth;
	
	public Date getDateDay() {
		return dateDay;
	}

	public void setDateDay(Date dateDay) {
		this.dateDay = dateDay;
	}

	public BigDecimal getArrears() {
		return arrears;
	}

	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}

	public BigDecimal getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(BigDecimal shopNumber) {
		this.shopNumber = shopNumber;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getDateYearMonth() {
		return dateYearMonth;
	}

	public void setDateYearMonth(Date dateYearMonth) {
		this.dateYearMonth = dateYearMonth;
	}
	

}
