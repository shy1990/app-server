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
  private Float cash=0.0f;//现金
  private Float amerce=0.0f;//扣罚
  private Float unpaid=0.0f;//未付
  private Float debt=0.0f;//拖欠=未付+扣罚
  private Float payable;//应付=现金+拖欠+扣罚
  private Float paid=0.0f;//实付
  private Float nopay;//待付=应付-实付。
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
    this.cash = cash==null? 0.0f:cash;
  }

  public Float getDebt() {
    return debt;
  }

  public void setDebt(Float debt) {
    this.debt = debt==null?0.0f:debt;
  }

  public Float getAmerce() {
    return amerce;
  }

  public void setAmerce(Float amerce) {
    this.amerce = amerce==null?0.0f:amerce;
  }
  
  public Float getUnpaid() {
    return unpaid;
  }

  public void setUnpaid(Float unpaid) {
    this.unpaid = unpaid==null?0.0f:unpaid;
  }

  public Float getPayable() {
    payable=cash+unpaid+amerce;
    return payable;
  }

  public void setPayable(Float payable) {
    this.payable = payable==null?0.0f:payable;
  }

  public Float getPaid() {
    return paid;
  }

  public void setPaid(Float paid) {
    this.paid = paid==null?0.0f :paid;
  }

  public Float getNopay() {
    nopay=payable-paid;
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
