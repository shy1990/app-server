package com.wangge.app.server.vo;

import java.util.Date;
import java.util.List;
//申请
public class Apply {
	private String applyName;//申请者
	private List area;//区域
	private String goodsName;//商品名称
	private Date applyDate;//申请时间
	private Double amount;//调动幅度(金额)
	
	private String reason;//理由
	private String reasonM;//未通过理由
	
	
	public String getReasonM() {
		return reasonM;
	}
	public void setReasonM(String reasonM) {
		this.reasonM = reasonM;
	}
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public List getArea() {
		return area;
	}
	public void setArea(List area) {
		this.area = area;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}	
