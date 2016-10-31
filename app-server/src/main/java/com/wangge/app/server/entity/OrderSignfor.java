package com.wangge.app.server.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;




@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "BIZ_ORDER_SIGNFOR")
public class OrderSignfor implements Serializable {

  /**
   * 
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;

  public static enum RelatedStatus {
    NOTRELATED("未关联"), ENDRELATED("已关联");
    private String name;

    RelatedStatus(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }

  @Id
  @Column(name = "SIGNID")
  @SequenceGenerator(name = "idgen")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  private Integer id;
  private String fastmailNo;
  private String orderNo;
  private String userId;
  private String userPhone;
  private String shopName;
  private Float orderPrice;
  private Float actualPayNum;//实际支付金额
  private Integer phoneCount;
  @JsonFormat(pattern = "MM.dd HH:mm",timezone="GMT+8")
  private Date creatTime;
  @JsonFormat(pattern = "MM.dd HH:mm",timezone="GMT+8")
  private Date yewuSignforTime; 
  @JsonFormat(pattern = "MM.dd HH:mm",timezone="GMT+8")
  private Date customSignforTime;
  private Integer orderStatus;
  private Integer orderPayType;
  private String yewuSignforGeopoint;
  private String customSignforGeopoint;
  private Integer customSignforException;
  @JsonInclude(Include.NON_DEFAULT)
  private int partsCount;
  @Transient
  @JsonInclude(Include.NON_DEFAULT)
  private int orderCount;//订单数
  @Transient
  private int status;
  @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
  private Date fastmailTime;
  
  private String customUnSignRemark;
  
  private int isPrimaryAccount;
  
  private Integer billStatus; //订单结款状态
  
  private  Float  arrears;//欠款金额
  
  private Float payAmount;// 实付金额
  
  private Date overTime ; // 订单结清日期
  
  private Date updateTime ; // 订单结清日期
  
  private String accountId;

  @Enumerated(EnumType.STRING)
  private RelatedStatus relatedStatus;

  private String memberPhone;

  public int getOrderCount() {
    return orderCount;
  }

  public void setOrderCount(int orderCount) {
    this.orderCount = orderCount;
  }

  public OrderSignfor (){
    super();
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFastmailNo() {
    return fastmailNo;
  }
  public void setFastmailNo(String fastmailNo) {
    this.fastmailNo = fastmailNo;
  }
  public String getOrderNo() {
    return orderNo;
  }
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getUserPhone() {
    return userPhone;
  }
  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }
  public String getShopName() {
    return shopName;
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
 
  public Float getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Float orderPrice) {
    this.orderPrice = orderPrice;
  }

  public Integer getPhoneCount() {
    return phoneCount;
  }

  public void setPhoneCount(Integer phoneCount) {
    this.phoneCount = phoneCount;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public Date getYewuSignforTime() {
    return yewuSignforTime;
  }
  public void setYewuSignforTime(Date yewuSignforTime) {
    this.yewuSignforTime = yewuSignforTime;
  }
  public Date getCustomSignforTime() {
    return customSignforTime;
  }
  public void setCustomSignforTime(Date customSignforTime) {
    this.customSignforTime = customSignforTime;
  }
  public Integer getOrderStatus() {
    return orderStatus;
  }
  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
  }
  public Integer getOrderPayType() {
    return orderPayType;
  }
  public void setOrderPayType(Integer orderPayType) {
    this.orderPayType = orderPayType;
  }
  public String getYewuSignforGeopoint() {
    return yewuSignforGeopoint;
  }
  public void setYewuSignforGeopoint(String yewuSignforGeopoint) {
    this.yewuSignforGeopoint = yewuSignforGeopoint;
  }
  public String getCustomSignforGeopoint() {
    return customSignforGeopoint;
  }
  public void setCustomSignforGeopoint(String customSignforGeopoint) {
    this.customSignforGeopoint = customSignforGeopoint;
  }
  public Integer getCustomSignforException() {
    return customSignforException;
  }
  public void setCustomSignforException(Integer customSignforException) {
    this.customSignforException = customSignforException;
  }

  public int getPartsCount() {
    return partsCount;
  }

  public void setPartsCount(int partsCount) {
    this.partsCount = partsCount;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Date getFastmailTime() {
    return fastmailTime;
  }

  public void setFastmailTime(Date fastmailTime) {
    this.fastmailTime = fastmailTime;
  }

  public String getCustomUnSignRemark() {
    return customUnSignRemark;
  }

  public void setCustomUnSignRemark(String customUnSignRemark) {
    this.customUnSignRemark = customUnSignRemark;
  }

  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }

  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

public Integer getBillStatus() {
	return billStatus;
}

public void setBillStatus(Integer billStatus) {
	this.billStatus = billStatus;
}

public Float getArrears() {
	return arrears;
}

public void setArrears(Float arrears) {
	this.arrears = arrears;
}

public Float getPayAmount() {
	return payAmount;
}

public void setPayAmount(Float payAmount) {
	this.payAmount = payAmount;
}

public Date getOverTime() {
	return overTime;
}

public void setOverTime(Date overTime) {
	this.overTime = overTime;
}

public Date getUpdateTime() {
	return updateTime;
}

public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
}


  
  public Float getActualPayNum() {
    return actualPayNum==null?orderPrice:actualPayNum;
  }

  public void setActualPayNum(Float actualPayNum) {
    this.actualPayNum = actualPayNum;
  }

  public RelatedStatus getRelatedStatus() {
    return relatedStatus;
  }

  public void setRelatedStatus(RelatedStatus relatedStatus) {
    this.relatedStatus = relatedStatus;
  }

  public String getMemberPhone() {
    return memberPhone;
  }

  public void setMemberPhone(String memberPhone) {
    this.memberPhone = memberPhone;
  }
}
