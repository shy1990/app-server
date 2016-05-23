package com.wangge.app.server.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 整理收现金购物车数据
 * @author thor
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashPart implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String num;
  private Float cash;
  private List<OrderDetailPart> details;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getNum() {
    return num;
  }
  public void setNum(String num) {
    this.num = num;
  }
  public Float getCash() {
    return cash;
  }
  public void setCash(Float cash) {
    this.cash = cash;
  }
  public List<OrderDetailPart> getDetails() {
    return details;
  }
  public void setDetails(List<OrderDetailPart> details) {
    this.details = details;
  }
  
  
}
