package com.wangge.app.server.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDetailPart implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String phoneName;
  private String partsName;
  private Integer phoneNum;
  private Integer partsNum;
  
  //以下用于流水订单详情
  private String num;//订单号
  private Float amount;//金额
  
  
  public String getPhoneName() {
    return phoneName;
  }
  public void setPhoneName(String phoneName) {
    this.phoneName = phoneName;
  }
  public String getPartsName() {
    return partsName;
  }
  public void setPartsName(String partsName) {
    this.partsName = partsName;
  }
  public Integer getPhoneNum() {
    return phoneNum;
  }
  public void setPhoneNum(Integer phoneNum) {
    this.phoneNum = phoneNum;
  }
  public Integer getPartsNum() {
    return partsNum;
  }
  public void setPartsNum(Integer partsNum) {
    this.partsNum = partsNum;
  }
  public String getNum() {
    return num;
  }
  public void setNum(String num) {
    this.num = num;
  }
  public Float getAmount() {
    return amount;
  }
  public void setAmount(Float amount) {
    this.amount = amount;
  }
  
  
  
  
}
