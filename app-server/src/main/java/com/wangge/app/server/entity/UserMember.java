package com.wangge.app.server.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.User;

/**
 * 商城用户
 * @author peter
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_USER_MEMBER")
public class UserMember extends User{
	
	/**
	 * 已注册商城用户
	 */
	private static final long serialVersionUID = 1L;
	
	private String id; //用户id
//	@OneToOne(cascade=CascadeType.ALL)
//    @JoinColumn(name="id")
	private Region region;
	private String shopName; //店名
	private String phone; //手机号
	private String consignee; //收货人
	private String consigneePhone; //收货号码
	private int counterNumber; //柜台数
	private String codeYd; //移动
	private String codeLt; //联通
	private String codeDx; //电信
	private Date createTime; //创建时间
	private String coordinate; //坐标
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneePhone() {
		return consigneePhone;
	}
	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	public int getCounterNumber() {
		return counterNumber;
	}
	public void setCounterNumber(int counterNumber) {
		this.counterNumber = counterNumber;
	}
	public String getCodeYd() {
		return codeYd;
	}
	public void setCodeYd(String codeYd) {
		this.codeYd = codeYd;
	}
	public String getCodeLt() {
		return codeLt;
	}
	public void setCodeLt(String codeLt) {
		this.codeLt = codeLt;
	}
	public String getCodeDx() {
		return codeDx;
	}
	public void setCodeDx(String codeDx) {
		this.codeDx = codeDx;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	
}
