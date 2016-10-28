package com.wangge.app.server.entity.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDto {
	
	private String orderNo;
	private int payTyp;
	private Float orderPrice;
	private Float arrear;
	private int billStatus;
	private int Payee;
	private int orderStatus;
	 @JsonFormat(pattern="HH:mm",timezone = "GMT+8")
	private Date createTime;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getPayTyp() {
		return payTyp;
	}
	public void setPayTyp(int payTyp) {
		this.payTyp = payTyp;
	}
	public Float getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Float orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Float getArrear() {
		return arrear;
	}
	public void setArrear(Float arrear) {
		this.arrear = arrear;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public int getPayee() {
		return Payee;
	}
	public void setPayee(int payee) {
		Payee = payee;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
