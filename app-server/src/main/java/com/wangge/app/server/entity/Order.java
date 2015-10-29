package com.wangge.app.server.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * * <p>Title: </p>* <p>Description:订单信息 </p>* <p>Company: </p> * @author * @date
 */
@Table(schema = "SANJI", name = "T_TASK")
@Entity
public class Order extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String username;
	private String regionId;
	private String phone;
	

	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getRegionId() {
		return regionId;
	}


	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


//	private String memberId;//会员ID  管理 SJ_TM_MEMBER
//
//	private Date createtime = new Date();//下单时间
//
//
//	private BigDecimal totalCost;//总金额
//
//
//	private String orderNum;//订单编号
//
//	public String getMemberId() {
//		return memberId;
//	}
//
//
//	public void setMemberId(String memberId) {
//		this.memberId = memberId;
//	}
//
//
//	public Date getCreatetime() {
//		return createtime;
//	}
//
//
//	public void setCreatetime(Date createtime) {
//		this.createtime = createtime;
//	}
//
//
//	public BigDecimal getTotalCost() {
//		return totalCost;
//	}
//
//
//	public void setTotalCost(BigDecimal totalCost) {
//		this.totalCost = totalCost;
//	}
//
//
//	public String getOrderNum() {
//		return orderNum;
//	}
//
//
//	public void setOrderNum(String orderNum) {
//		this.orderNum = orderNum;
//	}


	
	
	
	
	
}
