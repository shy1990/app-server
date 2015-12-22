package com.wangge.app.server.vo;

import com.wangge.app.server.entity.Visit.VisitStatus;

public class VisitVo {
	private String id;
	private String shopName;
	private String address;
	private VisitStatus status;
	private String imageurl;//扫街图片
	private String summary;
	private String imageurl1;//业务拜访图片1
	private String imageurl2;//拜访图片2
	private String imageurl3;//拜访图片3
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public VisitStatus getStatus() {
		return status;
	}
	public void setStatus(VisitStatus status) {
		this.status = status;
	}
	
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
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
	
}
