package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SYS_REGION_TYPE")
public class RegionType implements Serializable {
  private static final long serialVersionUID = 1L;



  @Id
  @Column(name = "REGION_TYPE_ID")
  private int id;

  @Column(name = "REGION_TYPE_NAME")
  private String name;


  @Column(name = "PARENT_ID")
  private  int parentId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getParentId() {
    return parentId;
  }

  public void setParentId(int parentId) {
    this.parentId = parentId;
  }
}
