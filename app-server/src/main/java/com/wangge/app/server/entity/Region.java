package com.wangge.app.server.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(schema = "YEWU", name = "T_REGION")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING, length=20)
public abstract class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="REGION_ID")
	private String id;
	private String name;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Region parent;
	@ManyToMany(mappedBy="regions",cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
	private Collection<Task> task;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="parent")
	private Collection<Region> regions;

	public Collection<Task> getTask() {
		return task;
	}

	public void setTask(Collection<Task> task) {
		this.task = task;
	}

	public String getId() {
		return id;
	}

	public Region() {
		super();
	}


	/**
	 * 区域编号
	 * @param id
	 * @param name
	 */
	public Region(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * 区域编号
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getParent() {
		return parent;
	}

	public void setParent(Region parent) {
		this.parent = parent;
	}

	public Collection<Region> getRegions() {
		return regions;
	}

	public void setRegions(Collection<Region> regions) {
		this.regions = regions;
	}

	
	
}
