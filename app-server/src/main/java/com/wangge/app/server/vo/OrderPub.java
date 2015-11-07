package com.wangge.app.server.vo;

import java.util.Date;
import java.util.List;
//订单相关
public class OrderPub {
	//============基本属性
	private String orderNum;//订单号
	private String username;//店铺名
	private String address; //店铺地址
	private Date createTime;//下单时间
	//==========附加属性
	private String payState;//支付状态 
	private List goodsName;//商品名
	private List goodsNum;//商品编号
	private Double totalCost;	//总金额
	private String status;//订单状态
	
	
	
	
	
	public List getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(List goodsName) {
		this.goodsName = goodsName;
	}
	public List getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(List goodsNum) {
		this.goodsNum = goodsNum;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	
	
	
}
