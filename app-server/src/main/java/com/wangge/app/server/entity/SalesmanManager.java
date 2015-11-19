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

import com.wangge.core.entity.AbstractPersistable;
import com.wangge.security.entity.User;

/**
 * 业务经理
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "SJ_YEWU", name = "BIZ_SALESMAN_MANAGER")
public class SalesmanManager extends AbstractPersistable<String> {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
			@Parameter(name = "property", value = "user") })
	@Column(name = "user_id")
	private String id;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public SalesmanManager() {
		super();
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
