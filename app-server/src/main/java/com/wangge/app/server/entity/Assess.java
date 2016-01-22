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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.app.server.entity.Regist.RegistStatus;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 注册
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "BIZ_ASSESS")
public class Assess extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	public enum AssessStatus {
		PENDING("进行中"), COMMIT("提交审核"), AGREE("通过");
		private String name;

		private AssessStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "ASSESS_ID")
	private Long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private Salesman salesman;
	@Column(name = "ASSESS_DEFINE_AREA")
	private String assessDefineArea;
	@Column(name = "ASSESS_AREA")
	private String assessArea;
	@Column(name = "ASSESS_STAGE")
	private String assessStage;
	@Column(name = "ASSESS_ACTIVENUM")
	private String assessActivenum;
	@Column(name = "ASSESS_ORDERNUM")
	private String assessOrdernum;
	@Column(name = "ASSESS_CYCLE")
	private String assessCycle;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date assessTime;
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ASSESS_STATUS")
	private AssessStatus status;
	@Column(name = "ASSESS_AREA_ZH")
	private String assesszh;
	public Assess() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public String getAssessArea() {
		return assessArea;
	}

	public void setAssessArea(String assessArea) {
		this.assessArea = assessArea;
	}

	public String getAssessStage() {
		return assessStage;
	}

	public void setAssessStage(String assessStage) {
		this.assessStage = assessStage;
	}

	public String getAssessActivenum() {
		return assessActivenum;
	}

	public void setAssessActivenum(String assessActivenum) {
		this.assessActivenum = assessActivenum;
	}

	public String getAssessOrdernum() {
		return assessOrdernum;
	}

	public void setAssessOrdernum(String assessOrdernum) {
		this.assessOrdernum = assessOrdernum;
	}

	public String getAssessCycle() {
		return assessCycle;
	}

	public void setAssessCycle(String assessCycle) {
		this.assessCycle = assessCycle;
	}

	public Date getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}

	public AssessStatus getStatus() {
		return status;
	}

	public void setStatus(AssessStatus status) {
		this.status = status;
	}

	public String getAssesszh() {
		return assesszh;
	}

	public void setAssesszh(String assesszh) {
		this.assesszh = assesszh;
	}

	public String getAssessDefineArea() {
		return assessDefineArea;
	}

	public void setAssessDefineArea(String assessDefineArea) {
		this.assessDefineArea = assessDefineArea;
	}
	

}
