package com.wangge.app.server.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WaterOrderPart implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String seriaNo;//流水号
  private Float cash;//现金
  private Float debt=new Float(0);//拖欠
  private Float amerce=new Float(0);//扣罚
  private Float payable=cash+debt+amerce;//应付=现金+拖欠+扣罚
  private Float paid;//实付
  private Float nopay=payable-paid;//待付=应付-实付。
  private Integer status;//状态：0-未审核，1-已审核
  private String time;//创建日期
  
  private List<OrderDetailPart> order; //订单详情

  public String getSeriaNo() {
    return seriaNo;
  }

  public void setSeriaNo(String seriaNo) {
    this.seriaNo = seriaNo;
  }

  public Float getCash() {
    return cash;
  }

  public void setCash(Float cash) {
    this.cash = cash;
  }

  public Float getDebt() {
    return debt;
  }

  public void setDebt(Float debt) {
    this.debt = debt;
  }

  public Float getAmerce() {
    return amerce;
  }

  public void setAmerce(Float amerce) {
    this.amerce = amerce;
  }

  public Float getPayable() {
    return payable;
  }

  public void setPayable(Float payable) {
    this.payable = payable;
  }

  public Float getPaid() {
    return paid;
  }

  public void setPaid(Float paid) {
    this.paid = paid;
  }

  public Float getNopay() {
    return nopay;
  }

  public void setNopay(Float nopay) {
    this.nopay = nopay;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public List<OrderDetailPart> getOrder() {
    return order;
  }

  public void setOrder(List<OrderDetailPart> order) {
    this.order = order;
  }
  
  
  

}
