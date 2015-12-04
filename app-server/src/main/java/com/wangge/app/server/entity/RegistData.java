package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;

/**
 * 注册店铺信息
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "BIZ_REGISTDATA")
public class RegistData extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "REGISTDATA_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGIST_ID")
	private Regist regist;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;
	private String name;
	private String consignee;
	private String receivingAddress;
	private String counterNumber;
	private String loginAccount;
	
	public RegistData() {
		super();
	}

	public RegistData(String name, String consignee,String receivingAddress,String counterNumber,String loginAccount) {
		this.name = name;
		this.consignee = consignee;
		this.receivingAddress = receivingAddress;
		this.counterNumber = counterNumber;
		this.loginAccount = loginAccount;
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

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getReceivingAddress() {
		return receivingAddress;
	}

	public void setReceivingAddress(String receivingAddress) {
		this.receivingAddress = receivingAddress;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	
	
}
