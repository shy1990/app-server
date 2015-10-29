package com.wangge.app.server.entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.wangge.common.entity.Region;
import com.wangge.common.entity.Task;

/**
 * 扫街任务
 * 
 * @author wujiming
 *
 */
@Entity
@Table(schema = "SANJI", name = "T_TASK_SAOJIE")
public class SaojieTask extends Task {
	private static final long serialVersionUID = 1L;
	@OneToOne
	@JoinColumn(name = "SAL_ID")
	private Salesman salesman;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(schema = "SANJI", name = "T_TASK_SAOJIE_REGION", joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID") , inverseJoinColumns = @JoinColumn(name = "REGION_ID", referencedColumnName = "ID") )
	private Collection<Region> regions;

	public SaojieTask() {
		super();
	}

	public SaojieTask(Salesman salesman) {
		super();
		this.salesman = salesman;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public Collection<Region> getRegions() {
		return regions;
	}

	public void setRegions(Collection<Region> regions) {
		this.regions = regions;
	}

}
