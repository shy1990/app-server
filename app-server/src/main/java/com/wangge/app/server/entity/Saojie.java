package com.wangge.app.server.entity;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.app.server.entity.Task.TaskStatus;
import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 扫街
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "BIZ_SAOJIE")
public class Saojie extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "SAOJIE_ID")
	private Long id;
	private String name;
	private String description;

	@Column(name = "SAOJIE_ORDER")
	private Integer order;
	private Integer minValue;
	@DateTimeFormat()
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@ManyToOne(fetch = FetchType.LAZY)
	private Saojie parent;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;

	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NEXT_ID")
	private Task next;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private Salesman salesman;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_BY")
	private SalesmanManager createBy;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "task")
	private Collection<DataSaojie> dataSaojie;

	public Saojie() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}
}
