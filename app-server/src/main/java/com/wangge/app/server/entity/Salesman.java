package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 业务员
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "YEWU", name = "T_SALESMAN")
public class Salesman extends User {
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="region_id")
	private Region region;

	public Salesman() {
		super();
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
