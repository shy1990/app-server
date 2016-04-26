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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 注册
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "BIZ_VISIT")
public class Visit extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	public enum VisitStatus {
		PENDING("进行中"), FINISHED("完成");

		private String name;

		private VisitStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "VISIT_ID")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGISTDATA_ID")
	private RegistData registData;

	
	private String address;
	private String summary;
	@Column(name = "IMAGE_URL1")
	private String imageurl1;
	@Column(name = "IMAGE_URL2")
	private String imageurl2;
	@Column(name = "IMAGE_URL3")
	private String imageurl3;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "VISIT_STATUS")
	private VisitStatus status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date expiredTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date finishTime;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private Salesman salesman;
	
	public Visit() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	public RegistData getRegistData() {
		return registData;
	}

	public void setRegistData(RegistData registData) {
		this.registData = registData;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageurl1() {
		return imageurl1;
	}

	public void setImageurl1(String imageurl1) {
		this.imageurl1 = imageurl1;
	}

	public String getImageurl2() {
		return imageurl2;
	}

	public void setImageurl2(String imageurl2) {
		this.imageurl2 = imageurl2;
	}

	public String getImageurl3() {
		return imageurl3;
	}

	public void setImageurl3(String imageurl3) {
		this.imageurl3 = imageurl3;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VisitStatus getStatus() {
		return status;
	}

	public void setStatus(VisitStatus status) {
		this.status = status;
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

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }
	
	
}
