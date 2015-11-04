package com.wangge.app.server.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.SecondaryTable;

/**
 * 自定义区域
 * 
 * @author wujiming
 *
 */
@Entity
@SecondaryTable(schema = "YEWU", name = "T_COORDINATES")
@DiscriminatorValue("CUSTOMREGION")
public class CustomRegion extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(table="T_COORDINATES",columnDefinition = "CLOB",name="content")
	private String coordinates;

	public CustomRegion() {
		super();
	}

	public CustomRegion(String id, String name, String coordinates) {
		super(id, name);
		this.coordinates = coordinates;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	

}
