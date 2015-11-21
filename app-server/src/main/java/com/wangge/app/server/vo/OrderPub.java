package com.wangge.app.server.vo;

import java.util.Date;
import java.util.List;
//订单相关
public class OrderPub {
	//============基本属性
	private String orderNum;//订单号
	private String username;//店铺名
	private Date createTime;//下单时间
	//==========附加属性
	private Double totalCost;	//总金额
	private String status;//订单状态
	//+
	private Integer totalPage;
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
