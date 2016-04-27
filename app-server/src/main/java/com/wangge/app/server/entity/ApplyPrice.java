package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
//@Table(name = "BIZ_CHANGEPRICE")
public class ApplyPrice implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "CHANGEPRICE_ID")
	private Long id;
	
	private String productName;//调价产品名称
	
	private Double priceRange;//调整额度
	
	private String applyReason;//申请理由
	private String approveReason;//审批理由
	private String status;//审核状态   1审核通过   2未通过   0 审核中
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyTime; //申请时间
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date approveTime; //审批时间
	
	@JoinColumn(name = "approvers_id")
	private String approversId; //审批人姓名
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;//区域

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "salesman_id")
	private Salesman salesman;//业务
	
	
	
	
	
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Salesman getSalesman() {
		return salesman;
	}
	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}
	public ApplyPrice() {
		super();
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(Double priceRange) {
		this.priceRange = priceRange;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}


	public String getApproveReason() {
		return approveReason;
	}
	public void setApproveReason(String approveReason) {
		this.approveReason = approveReason;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}



	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApproversId() {
		return approversId;
	}
	public void setApproversId(String approversId) {
		this.approversId = approversId;
	}
	

}
