package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.common.entity.Region;

@Entity
@Table(schema = "YEWU", name = "T_TASK")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuidgenerator")
	@GenericGenerator(name = "uuidgenerator", strategy = "uuid")
	@Column(name = "TASK_ID")
	private String id;
	private String name;
	private String description;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="task")
	private Collection<TaskTarget> targets;

	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "T_TASK_REGION", joinColumns = {
			@JoinColumn(name = "TASK_ID", referencedColumnName = "TASK_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "REGION_ID", referencedColumnName = "REGION_ID") })
	private Collection<Region> regions;

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
	@JoinColumn(name="CREATE_BY")
	private SalesmanManager createBy;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="task")
	private Collection<DataSaojie> dataSaojie;

	public Collection<DataSaojie> getDataSaojie() {
		return dataSaojie;
	}

	public void setDataSaojie(Collection<DataSaojie> dataSaojie) {
		this.dataSaojie = dataSaojie;
	}

	public String getId() {
		return id;
	}

	public Task() {
		super();
	}

	public Task getNext() {
		return next;
	}

	public void setNext(Task next) {
		this.next = next;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public SalesmanManager getCreateBy() {
		return createBy;
	}

	public void setCreateBy(SalesmanManager createBy) {
		this.createBy = createBy;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Collection<TaskTarget> getTargets() {
		return Collections.unmodifiableCollection(targets);
	}

	public void setTargets(Collection<TaskTarget> targets) {
		for (TaskTarget taskTarget : targets) {
			taskTarget.setTask(this);
		}
		this.targets = targets;
	}

	public Collection<Region> getRegions() {
		return Collections.unmodifiableCollection(regions);
	}

	public void setRegions(Collection<Region> regions) {
		this.regions = regions;
	}

	public enum TaskStatus {
		PENDING, AUDIT, AUTO_AGREE, MANUAL_AGREE
	}

}
