package com.wangge.app.server.vo;

import java.util.Date;
import java.util.List;
//申请
public class Apply {
	
	private Long id;
	private String salesman;//申请者
	private String areaName;//区域
	private String goodsName;//商品名称
	private Date applyDate;//申请时间
	private Double amount;//调动幅度(金额)
	private String status;//审核状态   1审核通过   2未通过   0 审核中
	private String reason;//理由
	private String replyReason;//审批详情
	
	private Integer totalPage;//总页数
	
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSalesman() {
		return salesman;
	}
	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReplyReason() {
		return replyReason;
	}
	public void setReplyReason(String replyReason) {
		this.replyReason = replyReason;
	}
	
}	
