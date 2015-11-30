package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "VISIT_ID")
	private Long id;


	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGIST_ID")
	private Regist regist;

	
	private String address;
	private String summary;
	private String imageurl1;
	private String imageurl2;
	private String imageurl3;
	
	
	public Visit() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	public Regist getRegist() {
		return regist;
	}

	public void setRegist(Regist regist) {
		this.regist = regist;
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

	

}
