package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 扫街数据
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "BIZ_SAOJIEDATA")
public class SaojieData extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "SAOJIEDATA_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SAOJIE_ID")
	private Saojie saojie;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;
	private String name;
	private String description;
	private String imageUrl;
	private String coordinate;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGIST_ID")
	private Regist regist;
	public SaojieData() {
		super();
	}

	public SaojieData(String name, String coordinate) {
		this.name = name;
		this.coordinate = coordinate;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Saojie getSaojie() {
		return saojie;
	}

	public void setSaojie(Saojie saojie) {
		this.saojie = saojie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Regist getRegist() {
		return regist;
	}

	public void setRegist(Regist regist) {
		this.regist = regist;
	}
	
}
