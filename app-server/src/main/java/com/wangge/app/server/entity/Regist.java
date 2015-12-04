package com.wangge.app.server.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 注册
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "BIZ_REGIST")
public class Regist extends AbstractPersistable<Long> {
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

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "REGIST_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Regist parent;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private Salesman salesman;

	private String name;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "REGIST_STATUS")
	private RegistStatus status;
	private Integer minValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "CLOB")
	private String description;

	@Column(name = "REGIST_ORDER")
	private Integer order;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	@OrderBy("regist_order")
	private Collection<Regist> children;

	public Regist() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	public Regist getParent() {
		return parent;
	}

	public void setParent(Regist parent) {
		this.parent = parent;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public RegistStatus getStatus() {
		return status;
	}

	public void setStatus(RegistStatus status) {
		this.status = status;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Collection<Regist> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public void setChildren(Collection<Regist> children) {
		this.children = children;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
