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
@Table(schema = "SJ_YEWU", name = "BIZ_SALESMAN")
public class Salesman extends AbstractPersistable<String> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
			@Parameter(name = "property", value = "user") })
	@Column(name="user_id")
	private String id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Salesman() {
		super();
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

}
