package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
* @ClassName: ChildAccount
* @Description: TODO(*子账号类)
* @author SongBaoZhen
* @date 2016年3月25日 下午5:23:47
*
 */
@Entity
@Table(name = "BIZ_CHILD_ACCOUNT")
@JsonInclude(Include.NON_EMPTY)
public class ChildAccount {

  /*子账号主键id */
  @Id
  /*@GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")*/
  @Column(name = "CHILD_ID")
  private String id;
  /*父类id，关联主账号*/
  private String parentId;
  /*手机sim卡号*/
  private String simId;
  /*子账号使用者的姓名*/
  private String truename;
  
  public ChildAccount() {
    super();
  }
 
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  public String getSimId() {
    return simId;
  }
  public void setSimId(String simId) {
    this.simId = simId;
  }
  public String getTruename() {
    return truename;
  }
  public void setTruename(String truename) {
    this.truename = truename;
  }
 
  
}
