package com.wangge.app.server.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "SYS_REGION")
@SecondaryTable(name = "SYS_COORDINATES")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REGION_ID")
	private String id;
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(table = "SYS_COORDINATES", columnDefinition = "CLOB", name = "content",updatable=true)
	private String coordinates;



	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Region parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Collection<Region> children;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_TYPE_ID")
	private RegionType type;

	private String namepath;

	private String centerPoint;

	private Integer starsLevel;


	public String getId() {
		return id;
	}

	public Region() {
		super();
	}

	/**
	 * 区域编号
	 *
	 * @param id
	 * @param name
	 */
	public Region(String id, String name, RegionType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}



	public String getNamepath() {
		return namepath;
	}

	public void setNamepath(String namepath) {
		this.namepath = namepath;
	}

	public RegionType getType() {
		return type;
	}

	public void setType(RegionType type) {
		this.type = type;
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

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}



	public Integer getStarsLevel() {
		return starsLevel;
	}

	public void setStarsLevel(Integer starsLevel) {
		this.starsLevel = starsLevel;
	}

	public Collection<Region> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public void setChildren(Collection<Region> children) {
		this.children = children;
	}

	public String getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(String centerPoint) {
		this.centerPoint = centerPoint;
	}


}
