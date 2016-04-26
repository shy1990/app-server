package com.wangge.app.server.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 油补系数
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "SYS_OIL_PARAMETERS")
public class OilParameters implements Serializable {
	private static final long serialVersionUID = 1L;

	
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "KM_RATIO")
  private Float KmRatio;//公里系数
  @Column(name = "KM_OIL_SUBSIDY")
  private Float KmOilSubsidy; //每公里油补补助
  @Column(name = "REGION_ID")
  private String regionId;
  
  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public Float getKmRatio() {
    return KmRatio;
  }


  public void setKmRatio(Float kmRatio) {
    KmRatio = kmRatio;
  }


  public Float getKmOilSubsidy() {
    return KmOilSubsidy;
  }


  public void setKmOilSubsidy(Float kmOilSubsidy) {
    KmOilSubsidy = kmOilSubsidy;
  }


  public String getRegionId() {
    return regionId;
  }


  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }
	
	
}
