package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;
import com.wangge.security.entity.User;

/**
 * 申请调价
 * 
 * @author STORE
 *
 */
@Entity
@Table(name = "BIZ_CHANGEPRICE")
public class Changerprice extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	public enum RegistStatus {
		PENDING("进行中"), COMMIT("提交审核"), AGREE("通过");

		private String name;

		private RegistStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	@Column(name = "PRICE_RANGE")
	private Integer pricerange;
	
	@Column(name = "PRODUCT_NAME")
	private String productname;
	
	@Column(name = "APPLY_REASON")
	private String applyReason;
	
	@Column(name = "APPROVE_REASON")
	private String approveReason;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALESMAN_ID")
	private Salesman salesman;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPROVERS_ID")
	private User user;
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "CHANGEPRICE_ID")
	private Long id;

	@Override
	public Long getId() {
		return id;
	}
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "STATUS")
	private RegistStatus status;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_TIME")
	private Date applyTime;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPROVE_TIME")
	private Date approveTime;

	public Integer getPricerange() {
		return pricerange;
	}

	public void setPricerange(Integer pricerange) {
		this.pricerange = pricerange;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
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

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RegistStatus getStatus() {
		return status;
	}

	public void setStatus(RegistStatus status) {
		this.status = status;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
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
	
	
	
}
