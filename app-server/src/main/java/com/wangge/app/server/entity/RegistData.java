package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
public class RegistData extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "REGISTDATA_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ASSESS_ID")
	private Assess assess;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGION_ID")
	private Region region;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	private Salesman salesman;
	
	private String shopName;
	private String consignee;
	private String receivingAddress;
	private String counterNumber;
	private String loginAccount;
	private Date createtime;
	private String phoneNum;
	@Column(name="IMAGE_URL")
	private String imageUrl;
	@Column(name = "CLERK")
	private String clerk;
	@Column(name = "STORE_LENGTH")
	private String store_length;
	@Column(name = "STORE_WIDTH")
	private String store_width;
	@Column(name = "IMAGE_URL1")
	private String imageUrl1;
	@Column(name = "IMAGE_URL2")
	private String imageUrl2;
	@Column(name = "IMAGE_URL3")
	private String imageUrl3;
	private String description = "";
	
  public RegistData() {
		super();
	}

	public RegistData(String loginAccount,String imageUrl,String store_length,
			String store_width, String imageUrl1, String imageUrl2,
			String imageUrl3) {
		super();
		this.loginAccount = loginAccount;
		this.imageUrl = imageUrl;
		this.store_length = store_length;
		this.store_width = store_width;
		this.imageUrl1 = imageUrl1;
		this.imageUrl2 = imageUrl2;
		this.imageUrl3 = imageUrl3;
	}

	public RegistData(String name, String consignee,String receivingAddress,String phoneNum) {
		this.shopName = name;
		this.consignee = consignee;
		this.receivingAddress = receivingAddress;
		this.phoneNum = phoneNum;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public Assess getAssess() {
    return assess;
  }

  public void setAssess(Assess assess) {
    this.assess = assess;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getClerk() {
		return clerk;
	}

	public void setClerk(String clerk) {
		this.clerk = clerk;
	}

	public String getStore_length() {
		return store_length;
	}

	public void setStore_length(String store_length) {
		this.store_length = store_length;
	}

	public String getStore_width() {
		return store_width;
	}

	public void setStore_width(String store_width) {
		this.store_width = store_width;
	}

	public String getImageUrl1() {
		return imageUrl1;
	}

	public void setImageUrl1(String imageUrl1) {
		this.imageUrl1 = imageUrl1;
	}

	public String getImageUrl2() {
		return imageUrl2;
	}

	public void setImageUrl2(String imageUrl2) {
		this.imageUrl2 = imageUrl2;
	}

	public String getImageUrl3() {
		return imageUrl3;
	}

	public void setImageUrl3(String imageUrl3) {
		this.imageUrl3 = imageUrl3;
	}

	public String getDescription() {
		return description == null ? "" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
