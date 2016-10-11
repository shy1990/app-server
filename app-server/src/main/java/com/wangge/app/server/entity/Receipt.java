package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "BIZ_RECEIPT")
public class Receipt {

	 @Id
	  @Column(name = "receipt_id")
	  @GenericGenerator(name = "idgen", strategy = "increment")
	  @GeneratedValue(generator = "idgen")
	private Integer id;
	private Float amountCollected;
	private Integer receiptType;
	private Date createTime;
	private String accountId;
	private int signid;
	private String orderNo;
	
	
	public Receipt(Float amountCollected, Integer receiptType, Date createTime,
			String accountId, String orderno) {
		super();
		this.amountCollected = amountCollected;
		this.receiptType = receiptType;
		this.createTime = createTime;
		this.accountId = accountId;
		this.orderNo = orderno;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getAmountCollected() {
		return amountCollected;
	}
	public void setAmountCollected(Float amountCollected) {
		this.amountCollected = amountCollected;
	}
	public Integer getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public int getSignid() {
		return signid;
	}
	public void setSignid(int signid) {
		this.signid = signid;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
