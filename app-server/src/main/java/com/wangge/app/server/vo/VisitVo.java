package com.wangge.app.server.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date expiredTime;
	private int timing = -1;//倒计时
	private String coordinate;
	private String visitAddress;
	
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
		return address == null ? "" : address;
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
		return imageurl == null ? "" : imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl == null ? "" : imageurl;
	}
	public String getSummary() {
		return summary == null ? "" : summary;
	}
	public void setSummary(String summary) {
		this.summary = summary == null ? "" : summary.trim();
	}
	public String getImageurl1() {
		return imageurl1 == null ? "" : imageurl1;
	}
	public void setImageurl1(String imageurl1) {
		this.imageurl1 = imageurl1 == null ? "" : imageurl1;
	}
	public String getImageurl2() {
		return imageurl2 == null ? "" : imageurl2;
	}
	public void setImageurl2(String imageurl2) {
		this.imageurl2 = imageurl2 == null ? "" : imageurl2;
	}
	public String getImageurl3() {
		return imageurl3 == null ? "" : imageurl3;
	}
	public void setImageurl3(String imageurl3) {
		this.imageurl3 = imageurl3 == null ? "" : imageurl3;
	}
  public Date getBeginTime() {
    return beginTime;
  }
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }
  public Date getExpiredTime() {
    return expiredTime;
  }
  public void setExpiredTime(Date expiredTime) {
    this.expiredTime = expiredTime;
  }
  public int getTiming() {
    return timing;
  }
  public void setTiming(int timing) {
    this.timing = timing;
  }
  public String getCoordinate() {
    return coordinate;
  }
  public void setCoordinate(String coordinate) {
    this.coordinate = coordinate;
  }
  public String getVisitAddress() {
    return visitAddress;
  }
  public void setVisitAddress(String visitAddress) {
    this.visitAddress = visitAddress;
  }
	
}
