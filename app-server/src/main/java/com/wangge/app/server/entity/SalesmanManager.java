package com.wangge.app.server.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 业务经理
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "YEWU", name = "T_SALESMAN_MANAGER")
public class SalesmanManager extends User {
	private static final long serialVersionUID = 1L;

	public SalesmanManager() {
		super();
	}

}
