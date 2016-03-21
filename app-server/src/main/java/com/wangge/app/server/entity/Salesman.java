package com.wangge.app.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wangge.common.entity.Region;
import com.wangge.core.entity.AbstractPersistable;
import com.wangge.security.entity.User;

/**
 * 业务员
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "BIZ_SALESMAN")
public class Salesman extends AbstractPersistable<String> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
			@Parameter(name = "property", value = "user") })
	@Column(name = "user_id")
	private String id;
	
	private String simId;
	
	private int isOldSalesman;
	
	private SalesmanStatus status = SalesmanStatus.saojie;
	

	public SalesmanStatus getStatus() {
		return status;
	}

	public void setStatus(SalesmanStatus status) {
		this.status = status;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "ASSESS_STAGE")
  private String assessStage;

	@Column(name = "OLD_ID")
  private String oldid;
	
	public Salesman() {
		super();
	}

	public Salesman(String salesmanId) {
    this.id = salesmanId;
  }

  public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

  public String getAssessStage() {
    return assessStage;
  }

  public void setAssessStage(String assessStage) {
    this.assessStage = assessStage;
  }

  public int getIsOldSalesman() {
    return isOldSalesman;
  }

  public void setIsOldSalesman(int isOldSalesman) {
    this.isOldSalesman = isOldSalesman;
  }

  public String getOldid() {
    return oldid;
  }

  public void setOldid(String oldid) {
    this.oldid = oldid;
  }
  
  
  
}
