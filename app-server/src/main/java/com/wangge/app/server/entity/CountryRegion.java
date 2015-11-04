package com.wangge.app.server.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 行政区
 * @author wujiming
 *
 */
@Entity
@DiscriminatorValue("COUNTRYREGION")
public class CountryRegion extends Region{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountryRegion() {
		super();
	}

	public CountryRegion(String id, String name) {
		super(id, name);
	}

}
